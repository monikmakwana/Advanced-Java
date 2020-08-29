package coreservlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShowRequestHeaders extends HttpServlet 
{
  public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
  {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String title = "Use of HttpServletRequest object";
    out.println(
                "<HTML>\n" +
                "<HEAD><TITLE>" + title + "</TITLE></HEAD>" +
                "<BODY BGCOLOR=FDF5E6>" +
                "<H2>" + title + "</H2>" + "<br>"+
				"<h3>HttpServletRequest Methods : </h3>" + "<br>" +
				"<B>Request Method: </B>" +
                request.getMethod() + "<BR>" +
				
				 "<B>Query String: </B>" +
                request.getQueryString() + "<BR>" +
				
                "<B>Request URI: </B>" +
                request.getRequestURI() + "<BR>" +
                "<B>Request Protocol: </B>" +
                request.getProtocol() + "<BR><BR>" +
				
				"<H3>Display Header Information : </H3>" + "<BR>" +
                "<TABLE BORDER=1>" + 
                "<TR BGCOLOR=\"#FFAD00\">\n" +
                "<TH>Header Name<TH>Header Value");
	Enumeration headerNames = request.getHeaderNames();
	while(headerNames.hasMoreElements()) 
	{
		  String headerName = (String)headerNames.nextElement();
		  out.println("<TR><TD>" + headerName);
		out.println("    <TD>" + request.getHeader(headerName));
	}
	out.println("</TABLE></BODY></HTML>");
	}
 public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
 {
    doGet(request, response);
 }
}