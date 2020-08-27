/*
When you run the program, you should see, scrolling by in your command window, 
the HTML commands and textual content from the HTML file located at http://volume1.coreservlets.com.
*/
import java.net.*;
import java.io.*;

public class URLReader 
{
    public static void main(String[] args) throws Exception 
	{

        //URL oracle = new URL("http://www.oracle.com/");
		URL url = new URL("http://volume1.coreservlets.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
		{
            System.out.println(inputLine);
        }
		in.close();
    }
}