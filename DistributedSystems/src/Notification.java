import java.util.List;
import java.util.Map.Entry;

public class Notification extends Message {

	private static final long serialVersionUID = 883957144791468161L;
	private List<Entry<String, String>> message;
	private String chatName;
	
	public Notification(MessageID id, List<Entry<String, String>> message, String chatName) {
		super(id);
		this.message = message;
		this.chatName = chatName;
	}

	public List<Entry<String, String>> getMessage() {
		return message;
	}

	public String getChatName(){
		return chatName;
	}
	
}
