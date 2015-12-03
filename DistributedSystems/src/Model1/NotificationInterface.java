package Model1;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationInterface extends Remote {
	
	public boolean executeTask(ConnectionRequest t) throws RemoteException;

}
