import java.rmi.registry.Registry;

public class ServerConnection {

	private int port;
	private String chatName;
	private Registry registry;
	private ServerRemoteInterface rInterface;
	private Integer currentState;
	private String address;
	private String userID;
	
	public ServerConnection(String add, String id, int port, String chatName, Registry r, ServerRemoteInterface ro) {
		this.address = add;
		this.userID = id;
		this.port = port;
		this.chatName = chatName;
		this.registry = r;
		this.rInterface = ro;
		currentState = 0;
	}

	public String getAddress() {
		return address;
	}
	public String getUserID() {
		return userID;
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
	
	public ServerRemoteInterface getRemoteInterface(){
		return rInterface;
	}
	
	public Integer getCurrentState(){
		return currentState;
	}
	
	public void setCurrentState(int i){
		this.currentState = i;
	}
}
