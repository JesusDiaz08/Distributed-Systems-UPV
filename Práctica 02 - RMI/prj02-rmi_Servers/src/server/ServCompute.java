package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import interfaces.compute.ComputeServerInt;
import interfaces.compute.TaskInt;

public class ServCompute implements ComputeServerInt {
	
	//EJERCICIO: define a Tasks container (p.e. an ArrayList)
	ArrayList<TaskInt> task = new ArrayList<>();
	
	protected ServCompute(){
		super();
	}
	
	@Override
	public int loadTask(TaskInt a) throws RemoteException {
        System.out.println("Loading TASK");
        //EJERCICIO: add task to the defined container
        task.add(a);
        int taskID = task.size() - 1;
		return taskID;//EJERCICIO: returns an appropriate taskid
	}

	@Override
	public int removeTask(int idx) throws RemoteException {
        System.out.println("Removing TASK");
        //EJERCICIO: remove the task from the container
        task.remove(idx);
		return 0;
	}

	@Override
	public Object executeTask(TaskInt a) throws RemoteException {
        System.out.println("Executing a Task");
        //EJERCICIO: execute the passed task
		return a.execute();//EJERCICIO:
	}

	@Override
	public Object executeTask(TaskInt a, Object params) throws RemoteException {
        System.out.println("Loading and executing a task with param("+params+")");
        //EJERCICIO: execute the passed task
		return a.execute(params); //EJERCICIO:
	}

	@Override
	public Object executeTask(int idx, Object params) throws RemoteException {
        System.out.println("Executing the task "+idx+" with param("+params+")");
        //EJERCICIO: execute the previously loaded task
		return task.get(idx).execute(params); //EJERCICIO:
	}

	public static void main(String[] args) {
		if(System.getSecurityManager()== null){
			System.setSecurityManager(new SecurityManager());
		}
		
	       try {
	        	//EJERCICIO: get the local registry
	    	   Registry registry = LocateRegistry.getRegistry();
	        	//EJERCICIO: build the ServCompute stub
	    	   ServCompute engine = new ServCompute();
	    	   ComputeServerInt stub = (ComputeServerInt) UnicastRemoteObject.exportObject(engine,0);
	        	//EJERCICIO: bind (or rebind) the stub into the local registry
	    	   registry.rebind("ComputeServer01", stub);
	    	   
	       } catch (RemoteException e) {
	            System.err.println("Something wrong happended on the remote end");
	            e.printStackTrace();
	            System.exit(-1); // can't just return, rmi threads may not exit
	        } catch (Exception e) {
	            System.err.println("Something wrong");
	        	e.printStackTrace();
	            System.exit(-1); // can't just return, rmi threads may not exit
			}
	        System.out.println("The server is ready for tasks:");
	}
}
