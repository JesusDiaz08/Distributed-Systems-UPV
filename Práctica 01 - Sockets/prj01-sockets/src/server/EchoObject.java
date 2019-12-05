package server;

import java.net.*;
import java.io.*;
import java.text.*;
import java.util.*;
import rmi.EchoInt;


public class EchoObject implements EchoInt {
  String myURL="localhost";

  public EchoObject(){
     try {
          myURL=InetAddress.getLocalHost().getHostName();
     } catch (UnknownHostException e) {
          myURL="localhost";
     }
  }

  public String echo(String input) throws java.rmi.RemoteException {
     Date h = new Date();
     String fecha = DateFormat.getTimeInstance(DateFormat.LONG ,Locale.FRANCE).format(h);
     String ret = myURL + ":" + fecha + "> " +  input;
     try {
           Thread.sleep(3000);  ret = ret + " (retrasada 3 segundos)";
     } catch (InterruptedException e) {}

     return ret;
  }
}
