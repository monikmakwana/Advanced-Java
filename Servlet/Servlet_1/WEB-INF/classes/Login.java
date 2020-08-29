import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet
{
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String user=request.getParameter("uname");
		String pwd=request.getParameter("pwd");
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/login?serverTimezone=UTC","root","");
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from data");
			while(rs.next())
			{
				if(user.equals(rs.getString("Username")) && pwd.equals(rs.getString("Password")))
				{
					RequestDispatcher rd=request.getRequestDispatcher("Welcome");
					rd.forward(request, response);  
				}
			}
			out.println("<p style=\"color:red\"> *Sorry Username or Password Incorrect </p>");
			RequestDispatcher rd=request.getRequestDispatcher("/index.html");
			rd.include(request, response);  
		}
		catch(Exception e)
		{
			out.println("<p>Inside exception : "+e.toString()+"</p>");
		}
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		doGet(request,response);
	}
}