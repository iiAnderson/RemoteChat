import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class NotificationSource extends UnicastRemoteObject implements RemoteInterface {

	private static final long serialVersionUID = -8627981551399289814L;
	private String chatName;
	private Map<String, Connection> users;
	private List<Entry<Connection, String>>chatLog = new ArrayList<Entry<Connection, String>>();

	public NotificationSource(String name) throws RemoteException { 
		super();
		this.chatName = name;
		users = new HashMap<String, Connection>();
	}

	@Override
	public synchronized Message executeTask(Task t) throws RemoteException {
		switch(t.taskID){
		case PollServer:
			TaskMessage pos = (TaskMessage) t;
			List<Entry<Connection, String>> list = new ArrayList<Entry<Connection, String>>();
			if(chatLog.size()==Integer.parseInt(pos.getMessage()))
				return new Message(MessageID.NoMessagesToPoll);

			for(int i = Integer.parseInt(pos.getMessage()); i <= chatLog.size()-1; i++)
				list.add(getChatLog().get(i));

			if(list.equals(null))
				return new Message(MessageID.NoMessagesToPoll);

			List<Entry<String, String>> msg = new ArrayList<Entry<String, String>>();
			for(Entry<Connection, String> c: list)
				msg.add(new SimpleEntry<String, String>(c.getKey().getUserID(), c.getValue()));
			return new Notification(MessageID.Notification, msg, getChatName());

		case ConnectionRequest:
			ConnectionRequest req = (ConnectionRequest) t;
			if(getUsers().containsKey(req.getUserID()))
				return new Message(MessageID.UsernameTakenException);
			getUsers().put(req.getUserID(), new Connection(req.getAddress(), req.getUserID()));
			
			System.out.println(req.getUserID() + " at " + req.getAddress() + " has been added to the chat " + getChatName());
			return new Message(MessageID.Approved);
			
		case NotifyOnMessage:
			TaskMessage tm = (TaskMessage) t;
			System.out.println("recieved " + tm.getMessage());
			addToChatLog(tm.getMessage(), getUsers().get(tm.getUserID())); 
			return new Message(MessageID.Approved);
		default:
			return new Message(MessageID.Denied);
		}

	}

	public List<Entry<Connection, String>> getChatLog(){
		return chatLog;
	}

	public String getChatName(){
		return chatName;
	}

	public Map<String, Connection> getUsers(){
		return users;
	}

	public void addToChatLog(String s, Connection c){
		getChatLog().add(new SimpleEntry<Connection, String>(c, s));
	}
}

