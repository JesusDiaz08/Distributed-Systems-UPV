package interfaces.compute;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComputeServerInt extends Remote {
	int loadTask(TaskInt a) throws RemoteException;
	int removeTask(int idx) throws RemoteException;
	Object executeTask(TaskInt a) throws RemoteException;
	Object executeTask(TaskInt a,Object params) throws RemoteException;	
	Object executeTask(int idx, Object params) throws RemoteException;
}
 