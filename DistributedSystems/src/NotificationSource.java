import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class NotificationSource extends UnicastRemoteObject implements ServerRemoteInterface {

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

	public synchronized void notifyClient(Notification n, Connection conn){
		conn.getClientRemoteInterface().notifyClient(n);
	}

	public synchronized void recieveMessage(TaskMessage m){
		addToChatLog(m.getMessage(), getUsers().get(m.getUserID()));
		getNotificationsToSend();
	}

	public synchronized Message handleConnectionRequest(ConnectionRequest req, ClientRemoteInterface inf){
		if(getUsers().containsKey(req.getUserID()))
			return new Message(MessageID.UsernameTakenException);
		getUsers().put(req.getUserID(), new Connection(req.getAddress(), req.getUserID(), inf));

		System.out.println(req.getUserID() + " at " + req.getAddress() + " has been added to the chat " + getChatName());
		return new Message(MessageID.Approved);
	}

	private void getNotificationsToSend(){
		for(Connection conn: getUsers().values()){

			List<Entry<Connection, String>> list = new ArrayList<Entry<Connection, String>>();

			for(int i = conn.getClientRemoteInterface().getConnections().get(chatName).getCurrentState(); i <= chatLog.size()-1; i++)
				list.add(getChatLog().get(i));

			List<Entry<String, String>> msg = new ArrayList<Entry<String, String>>();
			for(Entry<Connection, String> c: list)
				msg.add(new SimpleEntry<String, String>(c.getKey().getUserID(), c.getValue()));
			notifyClient(new Notification(msg, chatName), conn);
		}
	}

	public synchronized List<Entry<Connection, String>> getChatLog(){
		return chatLog;
	}

	public synchronized String getChatName(){
		return chatName;
	}

	public synchronized Map<String, Connection> getUsers(){
		return users;
	}

	private void addToChatLog(String s, Connection c){
		getChatLog().add(new SimpleEntry<Connection, String>(c, s));
	}
}

