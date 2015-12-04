import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerAdmin {

	private static ServerAdmin instance;
	private Map<String, NotificationSource> chats = new HashMap<String, NotificationSource>();
	private Map<NotificationSource, ArrayList<Connection>> users = new HashMap<NotificationSource, ArrayList<Connection>>();
	
	private ServerAdmin(String s, int port){
		createConnection(s, port);
	}

	public static ServerAdmin getInstance(){
		return instance;
	}


	public static void main(String[] args){
		instance = new ServerAdmin("chats", 1100);
	}

	private boolean createConnection(String s, int port){
		try{
			NotificationSource src = new NotificationSource("chat1");
			getChats().put(s, src);
			getUsers().put(src, new ArrayList<Connection>());
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(s, src);
			System.out.println("NotificationSource binded");
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Map<String, NotificationSource> getChats(){
		return chats;
	}
	
	public Map<NotificationSource, ArrayList<Connection>> getUsers(){
		return users;
	}
}

