package server;

import java.io.*;
import java.io.IOException;
import java.net.*;

import java.io.*;

/**
**@class            EchoMultiServer
**@description      Este servidor gestiona las conexiones con el servidor
                    con una politica distinta al EchoServer.java.
                    Vamos a tener que implementar una instancia de
                    EchoMultiServerThread para poder gestionar las multiples
                    conexiones.
**/
public class EchoMultiServer {
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {

      try {
           serverSocket = new ServerSocket(4000);      // Anclamos la conexion al puerto 4000
      } catch (IOException e) {    // Puede haber un error al querer conectarse
           System.out.println("EchoMultiServer: could not listen on port: 4000, " + e.toString());
           System.exit(1);
      }
      System.out.println("EchoMultiServer listening on port: 4000");

      boolean listening = true;
      while (listening) {               // Nos mantenemos escuchando las peticiones de los clientes
          Socket clientSocket = null;
          try{
               clientSocket = serverSocket.accept();   //EJERCICIO: aceptar una nueva conexion
          } catch(IOException e){
               System.err.println("Hubo un error al aceptar una nueva conexion en el puerto 4000.");
               e.getMessage();
               continue;           // Seguimos esperando las posibles peticiones que puedan llegar.
          }

          //EJERCICIO: y crear un Thread para que la gestione
          /**  Para poder crear un hilo que gestione las multiples conexiones
               es necesario crear una instancia de un EchoMultiServerThread, e
               inicializarlo con la conexion del cliente aceptado.
          **/
    	     new EchoMultiServerThread(clientSocket).start();  //
     }
     try {
          serverSocket.close();
     } catch (IOException e) {
          System.err.println("Could not close server socket." + e.getMessage());
     }
   }
}

//----------------------------------------------------------------------------
//  class EchoMultiServerThread
//----------------------------------------------------------------------------

class EchoMultiServerThread extends Thread {
    private EchoObject eo;
    private Socket clientSocket = null;
    private String myURL = "localhost";
    private BufferedReader is = null;
    private PrintWriter os = null;
    private String inputline = new String();

    EchoMultiServerThread(Socket socket) {
        super("EchoMultiServerThread");
        clientSocket = socket;
        eo = new EchoObject();
        try {
          // Ejercicio
          is = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));   // Obtenemos el flujo de entrada (datos) del cliente que establecio la conexion de manera exitosa
        	os = new PrintWriter(clientSocket.getOutputStream()); // Obtenemos el flujo de salida para posteriormente mostrarlo en pantalla.

        } catch (IOException e) {
            System.err.println("Error sending/receiving" + e.getMessage());
            e.printStackTrace();
        }
        try {
           myURL=InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
           System.out.println("Unknown Host :" + e.toString());
           System.exit(1);
       }
    }

    /**
    **@description       El objeto de EchoObject nos va a permitir hacer eco
                         de aquello que se vaya leyendo.
    **/
    public void run() {
       try {
            while ((inputline = is.readLine()) != null) {   // Leemos los mensajes
                 //EJERCICIO: Invocar el objeto
                 os.println(eo.echo(inputline));  // Mostramos el mensaje que vamos obteniendo, utilizando eo.echo()
                 os.flush();
                 //EJERCICIO: y devolver la respuesta por el socket
            }
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error sending/receiving: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
