package server;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import interfaces.echo.EchoInt;
/**************************+
 * VM parameters (examples)
 * -classpath c:\dya\ws\00_basico\prj-rmi\bin 
 * -Djava.rmi.server.codebase=file:///c:/sdi/ws/00_basico/prj-rmi_Interfaces/bin/
 * -Djava.security.policy=politicaRmi.policy
 *
 */
public class EchoObjectRMI implements EchoInt {
	/**
	 * 
	 */
	public EchoObjectRMI()  { //throws RemoteException
		super();
	}

	private static EchoObject eo = new EchoObject();

	@Override
	public String echo(String input) {
		return eo.echo(input);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int port = 4000;
		if (System.getSecurityManager() == null) {
		      System.setSecurityManager(new SecurityManager());
		}
		
		
		// System.setProperty("java.rmi.server.hostname",args[0]);
		// System.out.println(args[0]);		
        
		try {
			//EJERCICIO: get the local registry
        	Registry registry = LocateRegistry.getRegistry();			
        	//EJERCICIO: build the EchoObjectRMI stub
        	EchoInt stub = (EchoInt) UnicastRemoteObject.exportObject( new EchoObjectRMI(), port);		
        	//EJERCICIO: bind (or rebind) the stub into the local registry
        	registry.rebind("TestEcho01", stub);   // Lo inscribimos en el registro de nombres
        } catch (RemoteException e) {
            System.err.println("Something wrong happended on the remote end");
            e.printStackTrace();
            System.exit(-1); // can't just return, rmi threads may not exit
        }
        System.out.println("The echo server is ready");
	}
}
