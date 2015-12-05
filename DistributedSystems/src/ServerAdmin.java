import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerAdmin {

	private static ServerAdmin instance;
	private Map<String, NotificationSource> chats = new HashMap<String, NotificationSource>();
	private Registry registry;
	
	private ServerAdmin(String s, int port){
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		createConnection(s, port);
	}

	   public static ServerAdmin getInstance() {
		      if(instance == null) {
		         instance = new ServerAdmin("chats", 1100);
		      }
		      return instance;
		   }


	public static void main(String[] args){
		ServerAdmin.getInstance();
	}

	private boolean createConnection(String s, int port){
		try{
			NotificationSource src = new NotificationSource(s);
			getChats().put(s, src);
			getRegistry().rebind(s, src);
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
	
	public Registry getRegistry(){
		return registry;
	}
}

