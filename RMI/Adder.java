//Add 2 Numbers Using RMI

import java.rmi.*;
public interface Adder extends Remote
{
	public int add(int x,int y)throws RemoteException;
}
