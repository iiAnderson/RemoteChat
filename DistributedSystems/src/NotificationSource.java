import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationSource extends UnicastRemoteObject implements RemoteInterface {

	private static final long serialVersionUID = -8627981551399289814L;
	private String chatName;
	private List<Connection> users;
	private Map<Connection, String> messages = new HashMap<Connection, String>();

	public NotificationSource(String name) throws RemoteException { 
		super();
		this.chatName = name;
		users = new ArrayList<Connection>();
	}

	@Override
	public Message executeTask(Task t) throws RemoteException {
		switch(t.taskID){
		case ConnectionRequest:
			ConnectionRequest req = (ConnectionRequest) t;
			getUsers().add(new Connection(req.getAddress(), req.getUserID()));
			System.out.println(req.getUserID() + " at " + req.getAddress() + "has been added to the chat " + getChatName());
			return new Message(MessageID.Approved);
		default:
			return new Message(MessageID.Denied);
		}
			
	}

	public Map<Connection, String> getMessages(){
		return messages;
	}
	
	public String getChatName(){
		return chatName;
	}

	public List<Connection> getUsers(){
		return users;
	}
}

