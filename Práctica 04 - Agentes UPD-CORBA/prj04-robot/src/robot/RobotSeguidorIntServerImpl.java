package robot;

import corba.instantanea.EstadoRobotDHolder;

import comm.*;
import corba.instantanea.*;
import corba.camara.*;

/**
 * This class is the implementation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class RobotSeguidorIntServerImpl extends corba.robot.RobotSeguidorIntPOA {

    org.omg.CORBA.ORB orb;
    CamaraInt camara;

    String minombre;
    int miid;
    String miIOR;

    private InstantaneaD instantanea;

	/**
	 * Constructor for RobotSeguidorIntServerImpl
	 */
	public RobotSeguidorIntServerImpl() {
	}

	public void ObtenerEstado(EstadoRobotDHolder est) {
		// TODO Auto-generated method stub

		//EJERCICIO: componer la instantanea a partir de EstadoRobotD y retornarla
	        //return _r;
	        est.value = new corba.instantanea.EstadoRobotD(minombre, miid, miIOR);

	}

    public void start(){
        new RobotDifusion().start();
    }

    //------------------------------------------------------------------------------
    // La clase anidada RobotDifusion
    //------------------------------------------------------------------------------

    class RobotDifusion extends Thread{

      private Difusion difusion;
      private EstadoRobotD sr;
      private suscripcionD sus;

      public void run(){
      //EJERCICIO: suscribir el robot en la camara
    	  sus = camara.SuscribirRobot(miIOR);
      //EJERCICIO: crear la difusion
    	  difusion = new Difusion(sus.iport);
          miid = sus.id;

        while(true){
           //EJERCICIO: recibir instantanea
        	instantanea = (InstantaneaD) difusion.receiveObject();

           //EJERCICIO: iterar sobre la lista de estados, imprimiendo el nombre de
           //todos los robots cuyo estado figura en la instantanea.

        	for (int j = 0; j < instantanea.estadorobs.length; j++) {
        		this.sr = instantanea.estadorobs[j];
        		System.out.println("Robot " + j + " : " + sr.nombre);
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
