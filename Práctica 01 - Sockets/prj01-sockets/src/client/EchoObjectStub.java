package client;

import java.net.*;
import java.io.*;
import rmi.*;

// Punto 6
public class EchoObjectStub implements EchoInt {

  private Socket echoSocket = null;
  private PrintWriter os = null;
  private BufferedReader is = null;
  private String host = "localhost";
  private int port=7;
  private String output = "Error";
  private boolean connected = false;

  public EchoInt setHostAndPort(String host, int port) {
    this.host= host; this.port =port;
    return this;
  }

  /**
  **@name           echo
  **@description    Establece la conexion, verifica si ocurre algun error e
                    imprime el mensaje que se le ha mandado el cliente
  **@param          String input: mensaje
  **@return         String output: mensaje repetido
  **@important      No tocar nada de esta funcion
  **/
  public String echo(String input)throws java.rmi.RemoteException {
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
    disconnect();
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
     //EJERCICIO: Implemente el metodo connect
     try{ // Puede haber errores si el socket no logra establecer conexion
          // Creacion del socket - Aqui se va a conectar la clase Echo.java
          echoSocket = new Socket(host, port);

          // Establecemos el flujo de entrada (streamIn) del socket para poder transportar la informacion de echo.java
          is = new BufferedReader( new InputStreamReader( echoSocket.getInputStream()));

          // Establecemos el flujo de salida (streamOut)
          os = new PrintWriter(echoSocket.getOutputStream());

          // Establecemos la conexion si no ha habido error alguno
          connected = true;

     }catch(IOException e){
          System.err.println("Hubo un error al intentar conectarse");
          e.printStackTrace();
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
	//EJERCICIO: Implemente el metodo disconnect
     try{ // Puede haber errores si el socket no logra establecer conexion
          // Basta con cerrar el socket y los flujos de entrada y salida.
          echoSocket.close();
          is.close();
          os.close();
          connected = false;
     }catch(IOException e){
          System.out.println("Hubo un error al intentar desconectarse");
          e.printStackTrace();
     }
  }
}
