package client;

import java.io.*;
import java.io.IOException;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
import rmi.EchoInt;

/**
 **@name            EchoObjectStub2
 **@description     Es el nuevo stub asociado con las nuevas politicas de
                    gestion de conexiones
 **/
 public class EchoObjectStub2 implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  //private int port=7;
  private String output = "Error";
  private boolean connected  = false;

  private final int time_to_disconnect = 5;
  private int port = 4000;
  private boolean timerActive;

  Timeout tout=null;

  public EchoObjectStub2 setHostAndPort(String host, int port) {
    this.host= host; this.port =port;
    tout = new Timeout(time_to_disconnect,this);
    return this;
  }

  public String echo(String input) throws java.rmi.RemoteException {
    connect();
    if (echoSocket != null && os != null && is != null) {
    try {
         os.println(input);
         os.flush();
         output= is.readLine();
      } catch (IOException e) {
        System.err.println("I/O failed in reading/writing socket");
      }
    }
    programDisconnection();
    return output;
  }

  /**
  **@name           connect
  **@description    Establecemos el socket que permitira conectar al cliente de Echo.java,
                    habilitando los flujos (de entrada y salida) necesarios.
  **@param          void
  **@return         void
  **@throws         java.rmi.RemoteException
  **/
  private synchronized void connect() throws java.rmi.RemoteException {
       //EJERCICIO: lo mismo que en EchoObjectStub
       /** Considerando la politica de gestion de conexiones, en el punto 1,
       establece que cuando termina una peticion el stub del cliente programa
       un temporizador que se desconecte con el servidor al cabo de 5 segundos
       **/
       if(connected){              // Si la conexion esta inactiva durante 5 seg
            tout.cancel();         // Vence el temporizador
            timerActive = false;
       }else{
            try{
               // Creacion del socket - Aqui se va a conectar la clase Echo.java
               echoSocket = new Socket(host, port);

               // Establecemos el flujo de entrada (streamIn) del socket para poder transportar la informacion de echo.java
               is = new BufferedReader( new InputStreamReader( echoSocket.getInputStream()));

               // Establecemos el flujo de salida (streamOut)
               os = new PrintWriter(echoSocket.getOutputStream());

               // Establecemos la conexion si no ha habido error alguno
               connected = true;
            }catch(IOException e){
                 System.err.println("Hubo un error al establecer la conexion");
                 e.printStackTrace();
            }
       }
  }

  /**
  **@name           disconnect
  **@description    Establece la desconexion del cliente de
                    Echo.java, cerrando los flujos (de entrada y
                    salida) necesarios.
  **@param          void
  **@return         void
  **@throws         java.rmi.RemoteException
  **/
  private synchronized void disconnect(){
	//EJERCICIO: lo mismo que en EchoObjectStub
     /**Segun la politica de gestion de conexiones, en el punto 3, establece que
     si durante los cinco segundos siguientes a una envocacion no llegan nuevas
     peticiones, vence el tiempo del temporizador y se cierra la conexion.**/
     if (timerActive && connected) {
          try{ // Puede haber errores si el socket no logra establecer conexion
               // Basta con cerrar el socket y los flujos de entrada y salida.
               echoSocket.close();
               is.close();
               os.close();
               connected = false;  // Cerramos la conexion
          }catch(IOException e){
               System.err.println("Hubo un error al establecer la conexion");
               e.printStackTrace();
          }
     }
  }

  private synchronized void programDisconnection(){
       timerActive = true;
       tout.start();
  }

  class Timeout {
     Timer timer;
     //EchoObjectStub4 stub;
     EchoObjectStub2 stub;
     int seconds;

     //public Timeout (int seconds, EchoObjectStub4 stub) {
     public Timeout (int seconds, EchoObjectStub2 stub) {
       this.seconds = seconds;
       this.stub = stub;
     }

     public void start() {
          //EJERCICIO
          timer = new Timer();
          timer.schedule(new TimeoutTask(), (seconds * 1000));      // temporizador de n segundos
     }

     public void cancel() {
          //EJERCICIO
          timer.cancel();
     }

     class TimeoutTask extends TimerTask {
          //EJERCICIO
          public void run(){
               System.out.println("Tiempo excedido");  // 5 segundos sin mensajes.
               stub.disconnect();
               cancel();
          }
     }
   }
}
