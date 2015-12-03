package Model1;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Notification extends UnicastRemoteObject implements NotificationInterface {

	private static final long serialVersionUID = 6812599201580107526L;

	protected Notification() throws RemoteException {
		super();
	}
	
}
