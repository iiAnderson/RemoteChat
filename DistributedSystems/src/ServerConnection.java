
public class ServerConnection extends Connection {

	private int port;
	private String chatName;
	
	public ServerConnection(String add, String id, int port, String chatName) {
		super(add, id);
		this.port = port;
		this.chatName = chatName;
	}

	public int getPort(){
		return port;
	}
	
	public String getChatName(){
		return chatName;
	}
}
