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
	private List<Entry<Connection, String>> chatLog;

	public NotificationSource(String name) throws RemoteException { 
		super();
		this.chatName = name;
		users = new HashMap<String, Connection>();
		chatLog = new ArrayList<Entry<Connection, String>>();
	}

	@Override
	public synchronized Message executeTask(Task t) throws RemoteException {
		switch(t.taskID){
		case PollServer:
			return serverPolled((TaskMessage) t);
		case ConnectionRequest:
			return handleConnectionRequest((ConnectionRequest) t);
		case NotifyOnMessage:
			messageNotify((TaskMessage) t);
		default:
			return new Message(MessageID.Denied);
		}
	}
	
	private synchronized Message messageNotify(TaskMessage tm){
		addToChatLog(tm.getMessage(), getUsers().get(tm.getUserID())); 
		return new Message(MessageID.Approved);
	}

	private synchronized Message handleConnectionRequest(ConnectionRequest req){
		if(getUsers().containsKey(req.getUserID()))
			return new Message(MessageID.UsernameTakenException);
		getUsers().put(req.getUserID(), new Connection(req.getAddress(), req.getUserID()));
		
		System.out.println(req.getUserID() + " at " + req.getAddress() + " has been added to the chat " + getChatName());
		return new Message(MessageID.Approved);
	}
	
	private synchronized Message serverPolled(TaskMessage pos){
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
	}
	
	public synchronized List<Entry<Connection, String>> getChatLog(){
		return chatLog;
	}

	public String getChatName(){
		return chatName;
	}

	public Map<String, Connection> getUsers(){
		return users;
	}

	public synchronized void addToChatLog(String s, Connection c){
		getChatLog().add(new SimpleEntry<Connection, String>(c, s));
	}
}

