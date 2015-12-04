
import java.rmi.*;

public interface RemoteInterface extends Remote {
	
	Message executeTask(Task t)
			throws RemoteException;
}