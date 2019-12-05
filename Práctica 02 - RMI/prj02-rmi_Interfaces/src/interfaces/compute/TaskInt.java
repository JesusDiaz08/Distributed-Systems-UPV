package interfaces.compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TaskInt extends Remote {
		Object execute() throws RemoteException;
		Object execute(Object params) throws RemoteException;				
}
