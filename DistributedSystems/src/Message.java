import java.io.Serializable;

public class Message implements Serializable {

	private static final long serialVersionUID = -317582558808337605L;
	public MessageID msgID = null;

	public Message(){}

	public Message(MessageID id){
		msgID = id;
	}
}