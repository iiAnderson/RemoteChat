import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

public class Notification implements Serializable{

	private static final long serialVersionUID = 883957144791468161L;
	private List<Entry<String, String>> message;
	private String chatName;
	
	public Notification(List<Entry<String, String>> message, String chatName) {
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
