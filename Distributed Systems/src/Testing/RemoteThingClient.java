package Testing;

import java.rmi.*;
import java.rmi.server.*;

public class RemoteThingClient {
	public static void main(String[] args){
		try{
			RemoteThingInterface remoteThing=
					(RemoteThingInterface)
					Naming.lookup("rmi://localhost/remoteThing");
			System.out.println("remoteThing found");
			System.out.println(remoteThing.accessThing());
		} catch(Exception e){System.out.println(e);}
	}
}

