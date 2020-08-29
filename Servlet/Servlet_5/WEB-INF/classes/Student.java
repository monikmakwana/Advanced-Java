import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Student extends HttpServlet
{
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		String rno=request.getParameter("rno");
		String fname=request.getParameter("fname");
		String mname=request.getParameter("mname");
		String lname=request.getParameter("lname");
		String courses=request.getParameter("Courses");
		String gen=request.getParameter("gen");
		String add=request.getParameter("add");
		int k=0;
		if((rno==null) || (rno.equals("")))
		{
			out.println("Please enter Student Roll Number..." + "<br>");
			k=1;
		}
		
		if((fname==null) || (fname.equals("")))
		{
			out.println("Please enter First Name..." + "<br>");
			k=1;
		}
		if((lname==null) || (lname.equals("")))
		{
			out.println("Please enter Last Name..." + "<br>");
		k=1;
		}
		if((courses==null) || (courses.equals("")))
		{
			k=1;
			out.println("Please enter Courses..." + "<br>");
		}
		if((gen==null) || (gen.equals("")))
		{
			k=1;
			out.println("Please enter Gender..." + "<br>");
		}
		if((add==null) || (add.equals("")))
		{
			k=1;
			out.println("Please enter Address..." + "<br>");
		}
		
		if(k==0)
		{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/registration?serverTimezone=UTC","root","");
			Statement st = con.createStatement();
			String str = "select Rollno from student";
			ResultSet rs = st.executeQuery(str);
			int cnt=0;
			while(rs.next())
			{				
				if(rno.equals(rs.getString("Rollno")))
				{			
					cnt=1;
					break;
				}			
			}
			if(cnt==1)
			{
				out.println("Student Roll Number already exist");
			}
			else
			{
				PreparedStatement ps=con.prepareStatement("insert into student values(?,?,?,?,?,?,?)");
			
				ps.setString(1,rno);
				ps.setString(2,fname);
				ps.setString(3,mname);
				ps.setString(4,lname);
				ps.setString(5,courses);
				ps.setString(6,gen);
				ps.setString(7,add);
							
				int i=ps.executeUpdate();
				if(i>0)
				{
					out.println("You are successfully Registered"); 
				}
			}
		
		}
		catch(Exception e)
		{
			out.println("<p>Inside exception : "+e.toString()+"</p>");
		}
		}
	}
}