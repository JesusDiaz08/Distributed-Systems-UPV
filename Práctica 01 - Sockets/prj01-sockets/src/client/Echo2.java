package client;

import java.io.*;
import java.net.*;
import rmi.EchoInt;

public class Echo2 {
  private static EchoInt ss;

  public static void main(String[] args) {

	  if (args.length<2) {
		  System.out.println("Usage: Echo <host> <port#>");
		  System.exit(1);
	  }

	  ss = new EchoObjectStub2().setHostAndPort(args[0],Integer.parseInt(args[1]));
	  BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));
	  PrintWriter stdOut = new PrintWriter(System.out);
	  String input,output;

	  //System.out.println("-" + args[0].getClass().getName() + "/" + args[1].getClass().getName() + "--");
	  //System.out.println("-" + args[0] + "/" + args[1] + "--");

    try {
         while(true){                                            //EJERCICIO: el bucle infinito:
              System.out.print("Escribe el texto >\t");
              input = stdIn.readLine();                          //EJERCICIO: Leer de teclado
              output = ss.echo(input);                           //EJERCICIO: Invocar el stub
              stdOut.println(output);                            //EJERCICIO: Imprimir por pantalla
              stdOut.flush();
         }
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host: "+ args[0]);
    } catch (IOException e) {
      System.err.println("I/O failed for connection to host: "+args[0]);
    }
  }
}
