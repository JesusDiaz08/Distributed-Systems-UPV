package rmi;

public interface EchoInt extends java.rmi.Remote {

  public String echo(String input) throws java.rmi.RemoteException;

}
