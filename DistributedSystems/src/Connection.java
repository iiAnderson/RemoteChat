
public class Connection {

	private String address;
	private String userID;
	
	public Connection(String add, String id){
		this.address = add;
		this.userID = id;
	}
	
	public String getAddress() {
		return address;
	}
	public String getUserID() {
		return userID;
	}
	
}
