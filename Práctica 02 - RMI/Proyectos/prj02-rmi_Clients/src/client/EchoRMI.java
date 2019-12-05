package client;

import java.io.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import interfaces.compute.ComputeServerInt;
import interfaces.echo.EchoInt;

public class EchoRMI {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EchoInt echo = null;
		if (args.length<1){
			System.out.println("Uso echo <host>");System.exit(1);
		}
		if(System.getSecurityManager()== null) {
			System.setSecurityManager(new SecurityManager());
		}
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter stdOut = new PrintWriter(System.out);

        String input,output;
        
        System.out.println("Args[0] = " +  args[0]);
        
        try{
        	//EJERCICIO: "lookup" the Echo RMI object
        	echo = (EchoInt) Naming.lookup("//"+args[0]+"/MyEcho");
        	stdOut.println("LookingUp TestEcho01");
        	stdOut.print("> "); 
        	stdOut.flush();
          while ( (input = stdIn.readLine())!=null){
        	  
        	  //EJERCICIO: call echo RMI object 
        	  output = echo.echo(input);        	  
              stdOut.println(output);
              stdOut.print("> "); 
              stdOut.flush();
          }
        }catch(Exception e){
              System.out.println("RMI Echo Client error: " + e.getMessage());
        }
	}

}
