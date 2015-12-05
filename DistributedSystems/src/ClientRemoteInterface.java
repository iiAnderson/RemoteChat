import java.rmi.Remote;
import java.util.Map;

public interface ClientRemoteInterface extends Remote{

	public Map<String, ServerConnection> connections = null;
	
	public boolean notifyClient(Notification n);

	public Map<String, ServerConnection> getConnections();
}
