import java.io.Serializable;

public class TaskMessage implements Serializable {

	private static final long serialVersionUID = 1496116495309047796L;
	private String userID;
	private String message;
	private String chatName;
	
	public TaskMessage(String userName, String message, String chatName) {
		this.userID = userName;
		this.chatName = chatName;
		this.message = message;
	}
	
	public String getChatName(){
		return chatName;
	}
	
	public TaskMessage(String userName) {
		this.userID = userName;
	}

	public String getUserID() {
		return userID;
	}

	public String getMessage() {
		return message;
	}

	
	
}
