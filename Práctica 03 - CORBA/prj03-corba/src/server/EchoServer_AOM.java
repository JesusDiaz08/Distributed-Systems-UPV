/*
 *  EchoServer_AOM.java
 *  Template to build an Active Object Map CORBA server.
 *  Please replace the string Echo with the name of your object following
 *  the instructions detailed in the code comments.
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *
 *  Rev: 2017
 */

package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
// import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.ThreadPolicyValue;

///////////Template customization:
// Set the name of the class (Replace Echo with the name of your object).
public class EchoServer_AOM {

	public static void main(String[] args) {

		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		props.put("org.omg.CORBA.ORBInitialHost", "localhost");
		props.put("org.omg.CORBA.ORBInitialPort", "1050");

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
			///////////Template customization:
			// Set the name of you POA class (Replace Echo with the name of your object).
			POA poa = poaRoot.create_POA("EchoServerImpl_poa",	poaRoot.the_POAManager(), policies);

			// Create the servant
			///////////Template customization:
			// Instantiate the servant object (Replace Echo with the name of your object).
			// Check the servant class name in the implementation template
			EchoServerImpl servant = new EchoServerImpl();

			// Activate the servant with the ID on myPOA
			///////////Template customization:
			// Give a name to the servant object into the POA (replace xxxxx with whatever name).
			byte[] objectId = "EchoObject".getBytes();
			poa.activate_object_with_id(objectId, servant);

			// Activate the POA manager
			poaRoot.the_POAManager().activate();

			// Get a reference to the servant and write it down.
			obj = poa.servant_to_reference(servant);

			// ---- Uncomment below to enable Naming Service access. ----
			org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
			NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
			///////////Template customization:
			// Give a name to the servant object into the Name Service (replace yyyyy with whatever name).
			nc.rebind(nc.to_name("EchoObject"), obj);

			//PrintWriter ps = new PrintWriter(new FileOutputStream(new File("server.ior")));
			//ps.println(orb.object_to_string(obj));
			//ps.close();

			System.out.println("CORBA Server ready...");

			// Wait for incoming requests
			orb.run();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
