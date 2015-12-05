import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SuperClient {

	private static SuperClient instance;
	private Map<String, NotificationSource> chats = new HashMap<String, NotificationSource>();
	private String help;
	private NotificationSink sink;
	private String userID;
	private Registry registry;

	private SuperClient(){
		sink = new NotificationSink();
		try {
			registry = LocateRegistry.createRegistry(1099);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		buildClient();
	}

	public static void main(String[] args) {
		SuperClient.getInstance();
	}

	private void buildClient(){
		Scanner scan = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("            ----Commands----\n");
		help.append("Connect usage: connect <hostname> <port> <chatname>\n");
		help.append("Send usage: send <chatname> <message>\n");
		help.append("List usage: list <hostname> <port>\n");
		help.append("Create usage: create: <chatname> <port>\n");
		help.append("          --------------------");
		this.help = help.toString();
		
		setUserName(scan);
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
	}
	
	private void setUserName(Scanner scan){
		System.out.println("Please enter your username:");
		try{
			userID = scan.nextLine();
		} catch (Exception e){
			System.err.println("Username must be a string");
		}
	}

	private boolean executeCommand(String cmd){
		try{
			String[] split = cmd.split(" ");
			switch(split[0]){
			case "connect": 
				return getSink().createNewConnection(split[1], Integer.parseInt(split[2]), split[3], getUserID());
			case "send":
				String s = "";
				String chatName = split[1];
				split[0] = "";
				split[1] = "";
				for(String string: split)
					s += string;
				getSink().sendMessage(chatName, s);
				return true;
			case "list":
				String[] chats = getSink().listChats(split[1], Integer.parseInt(split[2]));
				System.out.println("Chats avaliable at " + split[1] + " on port " + split[2]);
				for(String c: chats)
					System.out.println(c);
				return true;
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
	
	public static void notifyClient(String message, String userID, String chatName){
		System.out.println("[" + chatName + "][" + userID + "] > " + message );
	}

	public static SuperClient getInstance() {
		if(instance == null) 
			instance = new SuperClient();
		return instance;
	}

	public NotificationSink getSink(){
		return sink;
	}
	
	public String getUserID(){
		return userID;
	}
	
}
