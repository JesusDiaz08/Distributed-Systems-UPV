package camara;

import comm.*;
import corba.instantanea.*;
import corba.camara.*;
import corba.camara.suscripcionD;
import corba.camara.IPYPortD;
import corba.robot.*;
import java.util.LinkedList;
import java.util.Iterator;

public class CamaraIntServerImpl extends corba.camara.CamaraIntPOA {

   private org.omg.PortableServer.POA poa_;
   private org.omg.CORBA.ORB orb_;

   private LinkedList<String> listaRobots = new LinkedList<String>();
   private LinkedList<EstadoRobotD> listaEstados = new LinkedList<EstadoRobotD>();
   InstantaneaD instantanea;
   private int nrobots;
   private IPYPortD ipyport;

    public CamaraIntServerImpl(org.omg.CORBA.ORB orb, org.omg.PortableServer.POA poa, IPYPortD iport)
    {
        orb_ = orb;
        poa_ = poa;
        ipyport = new IPYPortD(iport.ip, iport.port);

        nrobots = 0;
    }


    public org.omg.PortableServer.POA _default_POA()
    {
        if(poa_ != null)
            return poa_;
        else
            return super._default_POA();
    }

    //
    // IDL:corba/Camara/CamaraInt/SuscribirRobot:1.0
    //
    /* @description: Utilizamos un monitor bloqueante con el fin de vincular a un
     * objeto suscripcionD por lo que todos los bloques sincronizados del mismo
     * objeto van a tener solo un hilo ejecutandolos al mismo tiempo. De esta manera,
     * en cada suscripcion, aquel nuevo suscriptor debe de esperar.
     * */
    public synchronized suscripcionD SuscribirRobot(String IORrob)
    {
       suscripcionD suscripcion = null;
       synchronized(listaRobots){
            //EJERCICIO: Implementar la suscripcion al robots
            // Verificamos si es un nuevo suscriptor
            if (!listaRobots.contains(IORrob)) {
                 listaRobots.add(IORrob);    // Si es nuevo, lo agregamos
                 nrobots++;   // y actualizamos
            }
            // Utilizamos la clase de la interfaz generada previamente con corba
            suscripcion = new suscripcionD(nrobots,ipyport);
            System.out.print("Robot suscrito: " + IORrob);
       }
       return suscripcion;
    }

    public void start(){
        new CamaraDifusion(ipyport).start();
    }

    //------------------------------------------------------------------------------
    // La clase anidada CamaraDifusion
    //------------------------------------------------------------------------------
    class CamaraDifusion extends Thread{
     private Difusion difusion;

      //------------------------------------------------------------------------------
      public CamaraDifusion(IPYPortD iport){
         difusion = new DifusionJMS(iport,false); // Difusion con JMS
      }

      //------------------------------------------------------------------------------
      public void run(){
        corba.instantanea.EstadoRobotDHolder st = new EstadoRobotDHolder();
        String ior=null;
        LinkedList<String> listaFallos = new LinkedList<String>();

         while(true){
           listaEstados.clear();
           listaFallos.clear();

           synchronized (listaRobots) {
        	   for (Iterator<String> i = listaRobots.iterator(); i.hasNext(); ){
                   try {
                      //EJERCICIO: invocar via CORBA el metodo ObtenerEstado y anyadir
                     //el estado del robot correspondiente a la lista de estados
                	   ior = (String) i.next();
                	   org.omg.CORBA.Object obj = orb_.string_to_object(ior);
                	   RobotSeguidorInt rs = RobotSeguidorIntHelper.narrow(obj);
                	   rs.ObtenerEstado(st);
                	   listaEstados.add(st.value);
                   } catch (Exception  e){
                       System.out.println("Detectado fallo Robot: " + ior );
                       e.printStackTrace();
                     //EJERCICIO: anyadir el robot caido a la lista de fallos
                       listaFallos.add(ior);
                  } // cierra catch
                }// cierra for
        	   listaRobots.removeAll(listaFallos);
           }// cierra bloque sync

           //EJERCICIO: crear una instantanea a partir de la lista de estados de los robots.
           instantanea = new InstantaneaD((EstadoRobotD[]) listaEstados.toArray(new EstadoRobotD[0]));

           //EJERCICIO: difundir la instantanea
           difusion.sendObject(instantanea);

           try{
               Thread.sleep(400);
           }catch(InterruptedException e){
               e.printStackTrace();
           }
        }
      }
    }
}
