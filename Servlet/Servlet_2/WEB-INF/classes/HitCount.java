import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HitCount extends HttpServlet 
{

   private int cnt; 

   public void init() 
   { 
      cnt = 0;
   } 

   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
		response.setContentType("text/html");

		cnt++; 
		PrintWriter out = response.getWriter();
		String title = "Application for Hit Count";
		out.println(
			"<html>" +
				"<head>"+
					"<title>" + title + "</title>"+
				"</head>" +
				"<body bgcolor = #b1cbbb >" + "<br>" +		
				   "<h2>" + "Total Number of Hits : " + cnt + "</h2>" +
				"</body>" +
			"</html>"
		);
   }
} 