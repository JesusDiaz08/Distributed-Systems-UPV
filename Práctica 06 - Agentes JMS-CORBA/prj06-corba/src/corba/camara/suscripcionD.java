package corba.camara;


/**
* corba/camara/suscripcionD.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from robot.idl
* viernes 29 de noviembre de 2019 08:16:14 PM GMT
*/

public final class suscripcionD implements org.omg.CORBA.portable.IDLEntity
{
  public int id = (int)0;
  public corba.camara.IPYPortD iport = null;

  public suscripcionD ()
  {
  } // ctor

  public suscripcionD (int _id, corba.camara.IPYPortD _iport)
  {
    id = _id;
    iport = _iport;
  } // ctor

} // class suscripcionD
