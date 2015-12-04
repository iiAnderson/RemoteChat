import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class ClientAdmin {

	private static ClientAdmin instance;
	private String help;
	private List<ServerConnection> connections;

	private ClientAdmin(){
		buildClient();
	}

	public static void main(String[] args) {
		instance = new ClientAdmin();
	}

	private void buildClient(){
		Scanner scan = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("----Commands----\n");
		help.append("Connect Usage: connect <ip> <port> <chatname>");
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
	}

	private boolean executeCommand(String cmd){
		try{
			String[] split = cmd.split(" ");
			switch(split[0]){
			case "connect": 
				createNewConnection(split[1], Integer.parseInt(split[2]), split[3], "iiAnderson");
				return true;	
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

	private boolean createNewConnection(String ip, int port, String chatName, String userID){
		try{
			Registry r = LocateRegistry.getRegistry(ip, port);
			RemoteInterface ro = (RemoteInterface) r.lookup(chatName);
			System.out.println("chat found");
			ro.executeTask(new ConnectionRequest(userID));
			connections.add(new ServerConnection(userID, ip, port, chatName));
		} catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static ClientAdmin getInstance(){
		return instance;
	}

	static class NotificationPoll {
		
	}
}

