import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionRequest extends Task {

	private static final long serialVersionUID = -8769967977820859075L;
	private String userID;
	private String address;
	
	public ConnectionRequest(String id) throws UnknownHostException{
		super(MessageID.ConnectionRequest);
		this.userID = id;
		address = InetAddress.getLocalHost().toString();
	}
	
	public boolean execute() {
		return true;
	}

	public String getUserID() {
		return userID;
	}

	public MessageID getTaskID() {
		return taskID;
	}
	
	public String getAddress(){
		return address;
	}

}
