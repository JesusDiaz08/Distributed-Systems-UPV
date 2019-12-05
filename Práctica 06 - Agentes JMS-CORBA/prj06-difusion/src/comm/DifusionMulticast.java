package comm;

import java.io.*;
import java.net.*;
import corba.camara.IPYPortD;

public class DifusionMulticast implements Difusion{

  MulticastSocket socket;
  corba.camara.IPYPortD ipyport;
  public InetAddress group;

//------------------------------------------------------------------------------
  public  DifusionMulticast(IPYPortD ipyport){
    this.ipyport = ipyport;

    try {
    	//EJERCICIO:
    	  //Crear el socket multicast
    	    this.socket = new MulticastSocket(ipyport.port);
    	  //EJERCICIO:
    	  //Obtener la direccion del grupo
    	    this.group = InetAddress.getByName(ipyport.ip);
    	  //EJERCICIO:
    	  //Unirse al grupo
    	    this.socket.joinGroup(this.group);
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
  }

//------------------------------------------------------------------------------
  public Object receiveObject(){

    Object object = null;
    ObjectInputStream ois = null;
    byte[] buffer;
    DatagramPacket packet;
    ByteArrayInputStream bis;
    int MEMORY = 16384;		// Le damos 16 GB

    //EJERCICIO: recibir el paquete y deserializarlo
    // Inicializamos el buffer
    buffer = new byte[MEMORY];
    // Inicializamos el buffer para los datagramas que se recibiran por UDP - Multicast
    packet = new DatagramPacket(buffer, buffer.length);

    try {	// Puede haber un error al recibir el paquete de datagramas
    	this.socket.receive(packet);			// Aqui adentro tengo la IP y el PORT
		buffer = packet.getData();				// Empezamos a deserializar.
		bis = new ByteArrayInputStream(buffer);	// Obtenemos el flujo de bits y
		ois = new ObjectInputStream(bis);		// despues esos bits nos dar�n el objeto recibido
		object = (Object) ois.readObject();		// cuando lo adaptemos.

	} catch (ClassNotFoundException | IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	} finally {
		try {
			if (ois != null ) {
				ois.close();  				// Cierra el flujo del objeto
			}
		} catch (IOException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}

    return object;
  }

//------------------------------------------------------------------------------
  public void sendObject(Object object){

    ByteArrayOutputStream bos;
    ObjectOutputStream oos = null;
    byte[] buffer;
    DatagramPacket packet;

    //EJERCICIO: serializar el paquete y difundirlo
    bos = new ByteArrayOutputStream();

    try {
		oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		oos.flush();
		buffer = bos.toByteArray();
		// Asocio el buffer con el gpo y puerto, para el canal de difusion
		packet = new DatagramPacket(buffer, buffer.length, this.group, this.ipyport.port);
		// Difundimos el paquete
		this.socket.send(packet);
	} catch (IOException e) {
		// TODO: handle exception
		e.printStackTrace();
	} finally {
		try {
			oos.close();
		} catch (IOException e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}
	}

  }

}
