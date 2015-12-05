import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationSink {

	private Map<String, ServerConnection> connections;

	public NotificationSink(){
		connections = new HashMap<String, ServerConnection>();
	}

	public boolean createNewConnection(String ip, int port, String chatName, String userID){
		ServerConnection con = null;
		try{
			Registry r = LocateRegistry.getRegistry(ip, port);
			RemoteInterface ro = (RemoteInterface) r.lookup(chatName);
			Message m = ro.executeTask(new ConnectionRequest(userID));
			System.out.println(m.msgID);
			con = new ServerConnection(ip, userID, port, chatName, r, ro);
			if(m.msgID.equals(MessageID.Approved))
				getConnections().put(chatName, con);
			else
				System.err.println(m.msgID + "");
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		Timer timer = new Timer();
		timer.schedule(new NotificationPoll(con), 500, 500);
		return true;
	}

	public String[] listChats(String host, int port){
		try {
			Registry r = LocateRegistry.getRegistry(host, port);
			return r.list();
		} catch (RemoteException e) {
			System.err.println("An error has occured, the host could not be contacted");
		}
		return null;
	}
	
	public boolean sendMessage(String chatName, String message) throws RemoteException, UnknownHostException{
		ServerConnection server = getConnections().get(chatName);
		if(server == null){
			System.out.println("There is not a chat with that name, please try again");
			return false;
		}
		server.getRemoteInterface().executeTask(new TaskMessage(MessageID.NotifyOnMessage, server.getUserID(), message));
		return true;
	}

	public Map<String, ServerConnection> getConnections(){
		return connections;
	}

	class NotificationPoll extends TimerTask {		

		ServerConnection c;

		public NotificationPoll(ServerConnection c){
			this.c = c;
		}

		public void run() {
			ServerConnection connection = c;
			Message n = null;
			try {
				n = (Message) connection.getRemoteInterface().executeTask(new TaskMessage(MessageID.PollServer, connection.getUserID(), "" + connection.getCurrentState()));
			} catch (RemoteException e) {
				System.out.println("There has been an error polling chat , the chat has been removed." + connection.getChatName() + " "  + e);
				getConnections().remove(connection.getChatName());
			}
			if(n instanceof Notification){
				Notification not = (Notification) n;
				c.setCurrentState(c.getCurrentState()+ not.getMessage().size());
				for(Entry<String, String> msg: not.getMessage()){
					ClientAdmin.notifyClient(msg.getValue(), msg.getKey(), not.getChatName());
				}
			}
		}

	}

}
