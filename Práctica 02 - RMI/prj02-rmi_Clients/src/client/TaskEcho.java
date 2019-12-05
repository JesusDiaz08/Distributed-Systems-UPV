package client;

import java.io.Serializable;
import java.rmi.RemoteException;
import interfaces.compute.TaskInt;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class TaskEcho implements TaskInt,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String myURL = "localhost";
		
	@Override
	public Object execute() throws RemoteException {
			//EJERCICIO
		Date date = new Date();
		
		String fecha = DateFormat.getTimeInstance(DateFormat.LONG, Locale.FRANCE).format(date);
		String ret = myURL + ":" + fecha + "> " ;
		
		try {
			Thread.sleep(3000);  
			ret = ret + " (retrasada 3 segundos)";
		}catch(InterruptedException e){}
		
		return ret;
	}

	@Override
	public Object execute(Object params) throws RemoteException {
			
			//EJERCICIO
Date date = new Date();
		
		String fecha = DateFormat.getTimeInstance(DateFormat.LONG, Locale.FRANCE).format(date);
		String ret = myURL + ":" + fecha + "> " + params;
		
		try {
			Thread.sleep(3000);  
			ret = ret + " (retrasada 3 segundos)";
		}catch(InterruptedException e){}
		
		return ret;
	}		
}
	
	

