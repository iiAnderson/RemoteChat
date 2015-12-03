package Testing;
import java.rmi.*;
import java.rmi.server.*;

public class RemoteThingServer {
	public static void main(String[] args){
		try{
			RemoteThing remoteThing=new RemoteThing();
			Naming.rebind("remoteThing",remoteThing);
			System.out.println("remoteThing ready");
		} catch(Exception e){}
	}
}
