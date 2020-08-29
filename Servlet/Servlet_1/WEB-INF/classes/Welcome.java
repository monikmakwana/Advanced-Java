import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Welcome extends HttpServlet
{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String user=request.getParameter("uname");
		
		out.println(
		"<html>" +
		"<body>" +
		"<br>" +
		"<h3>" + "Login Successful" + "<h3>" +
		"<h3>" + "Welcome " + user + "</h3>" +
		"</body>" +
		"</html>"
		);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doGet(request,response);
	}
}