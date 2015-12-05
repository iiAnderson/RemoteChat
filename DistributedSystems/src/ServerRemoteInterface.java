
import java.rmi.*;

public interface ServerRemoteInterface extends Remote {
	
	public void notifyClient(Notification n, Connection conn);

	public Message handleConnectionRequest(ConnectionRequest req, ClientRemoteInterface inf);
	
	public void recieveMessage(TaskMessage m);
}