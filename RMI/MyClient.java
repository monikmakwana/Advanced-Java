import java.rmi.*;
import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
public class MyClient
{

public static void main(String args[])
{
	try
	{
		Adder stub=(Adder)Naming.lookup("rmi://localhost:8080/monik");
		System.out.println(stub.add(34,4));

	}
	catch(Exception e)
	{
		System.out.println(e);
	}
}
}
