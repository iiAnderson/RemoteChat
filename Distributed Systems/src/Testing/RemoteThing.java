package Testing;
import java.rmi.*;
import java.rmi.server.*;

public class RemoteThing extends UnicastRemoteObject

implements RemoteThingInterface {
	public RemoteThing() throws RemoteException { super(); }
	public String accessThing() throws RemoteException{
		return "Hello Remote World";
	}
}

