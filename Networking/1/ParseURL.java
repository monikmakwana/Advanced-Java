import java.net.*;
import java.io.*;

public class ParseURL 
{
    public static void main(String[] args) throws Exception 
	{

        URL aURL = new URL("http://example.com:80/docs/books/tutorial" + 
							"/index.html?name=networking#DOWNLOADING");
		
		//URL aURL = new URL("http://volume1.coreservlets.com/archive/Chapter1.html"); 
		
        System.out.println("\nProtocol = " + aURL.getProtocol());
        System.out.println("Authority = " + aURL.getAuthority());
        System.out.println("Host = " + aURL.getHost());
        System.out.println("Port = " + aURL.getPort());
        System.out.println("Path = " + aURL.getPath());
        System.out.println("Query = " + aURL.getQuery());
        System.out.println("Filename = " + aURL.getFile());
        System.out.println("Reference = " + aURL.getRef());
    }
}