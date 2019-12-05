/*
 *  Set of templates to build a CORBA remote object (Active Object Map)
 *  and the corresponding client.
 *
 *  @author Jose Simo. (c) ai2-DISCA-UPV Creative Commons.
 *  
 *  Rev: 2017
*/

Provided files:

./README
./MYCORBAOBJECT.idl
./server/MYCORBAOBJECTServer_AOM.java
./server/MYCORBAOBJECTServerImpl.java
./client/MYCORBAOBJECTClientImpl.java


Please change the object name MYCORBAOBJECT by your object name 

1.- Copy the files (take care of package folders) into the src folder of your Eclipse project.
2.- Rename files replacing MYCORBAOBJECT with the name of your object (p.e. YOUROBJECTNAME).
3.- Refresh Eclipse project and continue editing the files inside Eclipse.
4.- Edit YOUROBJECTNAME.idl and follow the instructions detailed in the code comments. 
    Define the interface of your object.
5.- Compile the idl file in a command line (> idlj –fallTIE YOUROBJECTNAME.idl)
6.- Edit server files and follow the instructions detailed in the code comments.
7.- Edit client file and follow the instructions detailed in the code comments.
8.- Implement method calls in the main method of the client object.
