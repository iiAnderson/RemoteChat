import java.io.Serializable;

public abstract class Task implements Serializable{
	
	private static final long serialVersionUID = -2648768253316572694L;
	public MessageID taskID = null;
	
	public Task(MessageID id){
		this.taskID = id;
	}
	
	abstract boolean execute();
}
