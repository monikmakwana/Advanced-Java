package urlConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

public class URLConnectionTest
{
   public static void main(String[] args)
   {
      try
      {
         String urlName;
         if (args.length > 0) urlName = args[0];
         else urlName = "http://horstmann.com";

         var url = new URL(urlName);
         URLConnection connection = url.openConnection();

         // set username, password if specified on command line

         if (args.length > 2)
         {
            String username = args[1];
            String password = args[2];
            String input = username + ":" + password;
            Base64.Encoder encoder = Base64.getEncoder();
            String encoding = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encoding);
         }

         connection.connect();

         // print header fields

         Map<String, List<String>> headers = connection.getHeaderFields();
         for (Map.Entry<String, List<String>> entry : headers.entrySet())
         {
            String key = entry.getKey();
            for (String value : entry.getValue())
               System.out.println(key + ": " + value);
         }

         // print convenience functions

         System.out.println("----------");
         System.out.println("getContentType: " + connection.getContentType());
         System.out.println("getContentLength: " + connection.getContentLength());
         System.out.println("getContentEncoding: " + connection.getContentEncoding());
         System.out.println("getDate: " + connection.getDate());
         System.out.println("getExpiration: " + connection.getExpiration());
         System.out.println("getLastModifed: " + connection.getLastModified());
         System.out.println("----------");

         String encoding = connection.getContentEncoding();
         if (encoding == null) encoding = "UTF-8";
         try (var in = new Scanner(connection.getInputStream(), encoding))
         {   
            // print first ten lines of contents
   
            for (int n = 1; in.hasNextLine() && n <= 10; n++)
               System.out.println(in.nextLine());
            if (in.hasNextLine()) System.out.println(". . .");
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }   
}

/*

F:\Networking_Assignment\5>java urlConnection.URLConnectionTest
Keep-Alive: timeout=2, max=100
null: HTTP/1.1 302 Found
Server: Apache
Connection: Keep-Alive
Content-Length: 206
Date: Wed, 27 May 2020 09:30:03 GMT
Content-Type: text/html; charset=iso-8859-1
Location: https://horstmann.com/
----------
getContentType: text/html; charset=iso-8859-1
getContentLength: 206
getContentEncoding: null
getDate: 1590571803000
getExpiration: 0
getLastModifed: 0
----------
<!DOCTYPE HTML PUBLIC "-//IETF//DTD HTML 2.0//EN">
<html><head>
<title>302 Found</title>
</head><body>
<h1>Found</h1>
<p>The document has moved <a href="https://horstmann.com/">here</a>.</p>
</body></html>

F:\Networking_Assignment\5>java urlConnection.URLConnectionTest http://volume1.coreservlets.com
Keep-Alive: timeout=1, max=50
Accept-Ranges: bytes
null: HTTP/1.1 200 OK
Server: Apache
ETag: "da701a1-5e7a-51ecba4f0eb80"
X-Content-Type-Options: nosniff
Connection: Keep-Alive
Vary: Accept-Encoding,User-Agent
Last-Modified: Wed, 02 Sep 2015 23:01:18 GMT
Content-Length: 24186
Date: Wed, 27 May 2020 09:30:43 GMT
Content-Type: text/html
----------
getContentType: text/html
getContentLength: 24186
getContentEncoding: null
getDate: 1590571843000
getExpiration: 0
getLastModifed: 1441234878000
----------
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Core Servlets and JavaServer Pages (Second Edition) Volume 1</title>
<script src="scripts/sniffer.js" type="text/javascript"></script>
<script src="scripts/menus.js" type="text/javascript"></script>
<script src="scripts/toc.js" type="text/javascript"></script>
<link href="styles/screen.css" rel="stylesheet" type="text/css" media="screen" />
. . .

F:\Networking_Assignment\5>
*/