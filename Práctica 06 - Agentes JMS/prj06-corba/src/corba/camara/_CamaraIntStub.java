package corba.camara;


/**
* corba/camara/_CamaraIntStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from robot.idl
* viernes 29 de noviembre de 2019 08:16:14 PM GMT
*/

public class _CamaraIntStub extends org.omg.CORBA.portable.ObjectImpl implements corba.camara.CamaraInt
{

  public corba.camara.suscripcionD SuscribirRobot (String IORrob)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("SuscribirRobot", true);
                $out.write_string (IORrob);
                $in = _invoke ($out);
                corba.camara.suscripcionD $result = corba.camara.suscripcionDHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return SuscribirRobot (IORrob        );
            } finally {
                _releaseReply ($in);
            }
  } // SuscribirRobot

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/camara/CamaraInt:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _CamaraIntStub
