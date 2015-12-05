
public class Connection {

	private String address;
	private String userID;
	private ClientRemoteInterface rInterface;
	
	public Connection(String add, String id, ClientRemoteInterface rInf){
		this.address = add;
		this.userID = id;
		this.rInterface = rInf;
	}
	
	public ClientRemoteInterface getClientRemoteInterface(){
		return rInterface;
	}
	
	public String getAddress() {
		return address;
	}
	public String getUserID() {
		return userID;
	}
	
}
