package Model1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectionRequest extends UnicastRemoteObject implements NotificationInterface {

	private static final long serialVersionUID = -4666114492407410392L;

	protected ConnectionRequest() throws RemoteException {
		super();
	}

	@Override
	public boolean executeTask(ConnectionRequest t) throws RemoteException {
		
		return false;
	}

}
