import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ArithmaticOperations extends HttpServlet
{
	public void doPost(HttpServletRequest req,HttpServletResponse res)throws IOException , ServletException
	{
			PrintWriter out=res.getWriter();
			res.setContentType("text/html");
		
			int x=Integer.parseInt(req.getParameter("fst"));
			int y=Integer.parseInt(req.getParameter("snd"));
			int addition = (x+y);
			int multi = (x*y);
			int diff = (x-y);
			int div = (x/y);
			
			out.println(
				
				"<html>" +
					"<head><title>" + "Arithmatic Operations" + "</head></title>" +
					"<body>" +
						"<h3>" + "Result of all the Arithmatic operations :" + "</h3>" +"<br><br>"+
						"Addition of " + x +" and "+y+" is : " + addition + "<br>" +
						"Multiplication of " + x +" and "+y+" is : " + multi + "<br>" +
						"Difference of " + x +" and "+y+" is : " + diff + "<br>" +
						"Division of " + x +" and "+y+" is : " + div +  "<br>" +
					"</body>" +
				"</html>"
			);
	}
}	