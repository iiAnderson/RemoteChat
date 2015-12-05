
public class TaskMessage extends Task {

	private static final long serialVersionUID = 1496116495309047796L;
	private String userID;
	private String message;
	
	public TaskMessage(MessageID id, String userName, String message) {
		super(id);
		this.userID = userName;
		this.message = message;
	}
	
	public TaskMessage(MessageID id, String userName) {
		super(id);
		this.userID = userName;
	}


	@Override
	boolean execute() {
		return false;
	}

	public String getUserID() {
		return userID;
	}

	public String getMessage() {
		return message;
	}

	
	
}
