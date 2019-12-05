package robot;

import java.util.Properties;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
// import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.ThreadPolicyValue;
/*MODIFICADO*/
import corba.camara.*;
/*FIN MODIFICADO*/

public class RobotSeguidor_Server_AOM {
	  /*MODIFICADO*/
	  static CamaraInt camara;
	  static int ok=0;
	  /*FIN MODIFICADO*/
	public static void main(String[] args) {

		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
			
		// Solo si se cambia el host
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		// Solo si se cambia el port
		props.put("org.omg.CORBA.ORBInitialPort", "1050");
		
		// Declaramos que confiamos en cualquier paquete
		props.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
		  
		try {
			// Initialize the ORB.
			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);

			// get a reference to the root POA
			org.omg.CORBA.Object obj = orb.resolve_initial_references("RootPOA");
			POA poaRoot = POAHelper.narrow(obj);

			// Create policies for our persistent POA
			org.omg.CORBA.Policy[] policies = {
					// poaRoot.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
					poaRoot.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
					poaRoot.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL)
			};

			// Create myPOA with the right policies
			POA poa = poaRoot.create_POA("RobotSeguidorIntServerImpl_poa",	poaRoot.the_POAManager(), policies);

			// Create the servant
			RobotSeguidorIntServerImpl servant = new RobotSeguidorIntServerImpl();

			// Activate the servant with the ID on myPOA
			byte[] objectId = "Robot".getBytes();
			poa.activate_object_with_id(objectId, servant);

			// Activate the POA manager
			poaRoot.the_POAManager().activate();

			// Get a reference to the servant and write it down.
			obj = poa.servant_to_reference(servant);

			// ---- Uncomment below to enable Naming Service access. ----
			// org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
			// NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
			// nc.bind(nc.to_name("MyServerObject"), obj);

			//PrintWriter ps = new PrintWriter(new FileOutputStream(new File("server.ior")));
			//ps.println(orb.object_to_string(obj));
			//ps.close();

			/*MODIFICADO*/
		    do{
		        try{
		        	//EJERCICIO:Conectar con el servidor de nombre y obtener una referencia
		        	//a la **camara**
		        	org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
		        	NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
		        	org.omg.CORBA.Object referencia_a_camara = null;
				referencia_a_camara =  nc.resolve_str("Camara");
		        	camara = corba.camara.CamaraIntHelper.narrow(referencia_a_camara);
		        	System.out.println("Identificador: " + servant);

			         //EJERCICIO: convertir la referencia al robot en un IOR en formato String
		        	servant.miIOR = orb.object_to_string(obj);

		        	servant.orb = orb;
		        	servant.camara = camara;
		        	if (args.length>0) servant.minombre = args[0]; else servant.minombre="Robot";
		        	ok = 1;
		        } catch(Exception ex) {
		          System.out.println("El robot no se registro bien en la camara. Reintentando...");
		        }
		      } while(ok==0);

		      servant.start();
			  /*FIN MODIFICADO*/


			System.out.println("CORBA Server ready...");

			// Wait for incoming requests
			orb.run();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
