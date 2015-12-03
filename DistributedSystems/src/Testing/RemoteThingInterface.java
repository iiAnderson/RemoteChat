package Testing;
import java.rmi.*;

public interface RemoteThingInterface extends Remote {
	public String accessThing()
			throws RemoteException;
}