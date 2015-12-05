import java.rmi.registry.Registry;

public class ServerConnection extends Connection {

	private int port;
	private String chatName;
	private Registry registry;
	private RemoteInterface rInterface;
	private Integer currentState;
	
	public ServerConnection(String add, String id, int port, String chatName, Registry r, RemoteInterface ro) {
		super(add, id);
		this.port = port;
		this.chatName = chatName;
		this.registry = r;
		this.rInterface = ro;
		currentState = 0;
	}

	public int getPort(){
		return port;
	}
	
	public String getChatName(){
		return chatName;
	}
	
	public Registry getRegistry(){
		return registry;
	}
	
	public RemoteInterface getRemoteInterface(){
		return rInterface;
	}
	
	public Integer getCurrentState(){
		return currentState;
	}
	
	public void setCurrentState(int i){
		this.currentState = i;
	}
}
