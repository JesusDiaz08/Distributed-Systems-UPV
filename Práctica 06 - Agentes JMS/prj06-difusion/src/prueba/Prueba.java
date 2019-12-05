package prueba;

import java.util.LinkedList;
import java.util.Properties;
import comm.*;
import corba.camara.IPYPortD;
import corba.instantanea.*;

//------------------------------------------------------------------------------
// La clase Prueba
//------------------------------------------------------------------------------
public class Prueba {
  IPYPortD ipyport;

//------------------------------------------------------------------------------
  public Prueba(){
    ipyport = new IPYPortD("228.1.1.1",1110);
    new CamaraDifusion();
    new RobotDifusion();
  }

//------------------------------------------------------------------------------
  public static void main(String args[]) {
	  //Crea el objeto para arrancar la aplicacion
	  Properties props = System.getProperties();
	  props.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
      new Prueba();
  }


//------------------------------------------------------------------------------
// La clase anidada CamaraDifusion (el servidor)
//------------------------------------------------------------------------------
  class CamaraDifusion extends Thread{
    Difusion difusion;
    InstantaneaD instantanea;
    LinkedList<EstadoRobotD> listaEstados = new LinkedList<EstadoRobotD>();
    EstadoRobotD st1 = new EstadoRobotD();
    EstadoRobotD st2 = new EstadoRobotD();

//------------------------------------------------------------------------------
    public CamaraDifusion(){
      //difusion = new DifusionMulticast(ipyport);     // Multicast
      difusion = new DifusionJMS(ipyport, true);       // JMS
      st1.nombre = "Hola 1!"; st2.nombre = "Hola 2!";
      listaEstados.add(st1); listaEstados.add(st2);
      this.start();
    }

//------------------------------------------------------------------------------
    public void run(){

      for(int i=1; i<6; i++) {
        instantanea = new InstantaneaD((EstadoRobotD[]) listaEstados.toArray(new EstadoRobotD[0]));
        difusion.sendObject(instantanea);
        try{
          Thread.sleep(400);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }


//------------------------------------------------------------------------------
// La clase anidada RobotDifusion (el cliente)
//------------------------------------------------------------------------------
  class RobotDifusion extends Thread{
    Difusion difusion;
    InstantaneaD instantanea;
    EstadoRobotD st = new EstadoRobotD();

//------------------------------------------------------------------------------
    public RobotDifusion(){
      //difusion = new DifusionMulticast(ipyport);     // Multicast
      difusion = new DifusionJMS(ipyport,true);        // JMS
      this.start();
    }

//------------------------------------------------------------------------------
    public void run(){

      while(true){
        instantanea = (InstantaneaD) difusion.receiveObject();
        System.out.println("-Escuchada difusion-");
        for(int i=0; i<instantanea.estadorobs.length; i++){
            st = instantanea.estadorobs[i];
            System.out.println("Contenido " + i + ": " + st.nombre);
        }
        try{
          Thread.sleep(400);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }
  }
}
