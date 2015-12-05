import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ServerAdmin {

	private static ServerAdmin instance;
	private Map<String, NotificationSource> chats = new HashMap<String, NotificationSource>();
	private Registry registry;
	private String help;
	
	private ServerAdmin(String s, int port){
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		buildServer();
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

	private void buildServer(){
			Scanner scan = new Scanner(System.in);
			StringBuilder help = new StringBuilder();
			help.append("----Commands----\n");
			help.append("Create usage: create: <chatname> <port>\n");
			this.help = help.toString();
			System.out.println(help.toString());
			while(true){
				try{
					String cmd = scan.nextLine();
					if(cmd.equalsIgnoreCase("exit"))
						break;
					executeCommand(cmd);
				} catch (Exception e){
					System.out.println("Invalid Input");
				}
			}
			scan.close();
			System.exit(0);
		}

		private boolean executeCommand(String cmd){
			try{
				String[] split = cmd.split(" ");
				switch(split[0]){
				case "create":
					createConnection(split[1], Integer.parseInt(split[2]));
					break;
				default:
					System.out.println("There was an error");
					System.out.println(help);
				}
			} catch (Exception e){
				System.out.println("There was an error");
				System.out.println(help);
				return false;
			}
			return false;
		}
	
	private boolean createConnection(String s, int port){
		try{
			if(getChats().containsKey(s)){
				System.out.println("There is already a chat with that name");
				return false;
			}
				
			NotificationSource src = new NotificationSource(s);
			getChats().put(s, src);
			getRegistry().rebind(s, src);
			System.out.println("A connection on port " + port + " named " + s + " has been made");
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

