package interfaces.echo;

import java.rmi.Remote;

public interface EchoInt extends Remote {
	String echo(String a) throws java.rmi.RemoteException;

}
