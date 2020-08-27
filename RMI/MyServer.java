import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class MyServer
{

public static void main(String args[]) throws Exception,NotBoundException
{
	String host = "localhost";
	try
	{
	
			LocateRegistry.createRegistry(5000);
			Registry registry = LocateRegistry.getRegistry(host,5000);
			Adder stub=new AdderRemote();
			Naming.rebind("rmi://localhost:5000/monik",stub);
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}

}
