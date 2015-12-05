import java.util.Scanner;

public class ClientAdmin {

	private static ClientAdmin instance;
	private String help;
	private NotificationSink sink;

	private ClientAdmin(){
		sink = new NotificationSink();
		buildClient();
	}

	public static void main(String[] args) {
		ClientAdmin.getInstance();
	}

	private void buildClient(){
		Scanner scan = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("----Commands----\n");
		help.append("Connect usage: connect <ip> <port> <chatname>\n");
		help.append("Send usage: send <chatname> <message>");
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
				getSink().createNewConnection(split[1], Integer.parseInt(split[2]), split[3], "matt");
				return true;
			case "send":
				String s = "";
				String chatName = split[1];
				split[0] = "";
				split[1] = "";
				for(String string: split)
					s += string;
				getSink().sendMessage(chatName, s);
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

	public static void notifyClient(String message, String userID, String chatName){
		System.out.println("[" + chatName + "][" + userID + "] > " + message );
	}

	public static ClientAdmin getInstance() {
		if(instance == null) 
			instance = new ClientAdmin();
		return instance;
	}

	public NotificationSink getSink(){
		return sink;
	}

}

