/*

***********************************************************************************************
3) Create a Stored procedure which passes Student id as the parameter and returns with the student grade. The id should be accepted from the user. 
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
class Proc_Grade
{
	static Connection con;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Statement st = null;
	static ResultSet rs = null;
	static String sql = null;	
	public static void main(String args[]) throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_ass?serverTimezone=UTC","root","");
		System.out.print("\nEnter Student Id : ");
		int id = Integer.parseInt(br.readLine());
		proc(id);
		con.close();
	}	
	public static void proc(int id) throws Exception
	{
		st = con.createStatement();
		sql = "CREATE PROCEDURE if not exists `stud_grade`(IN `id1` int(11)) NOT DETERMINISTIC NO SQL SQL SECURITY DEFINER BEGIN select * FROM grades where id=id1; END ";
		st.executeUpdate(sql);
		
		CallableStatement stmt = con.prepareCall("{call stud_grade(?)}");
        stmt.setInt(1, id);
		rs = stmt.executeQuery();
		if(rs.next()==false)
		{
			System.out.println("Not Found");
		}
		else
		{
			System.out.println("Grade Details : ");
			System.out.println("Assignment no: "+rs.getInt("assignment")+" Grade: "+rs.getString("grade"));
			while(rs.next())
			{
				System.out.println("Assignment no: "+rs.getInt("assignment")+" Grade: "+rs.getString("grade"));
			}
		}
		
		st = con.createStatement();
		sql="DROP PROCEDURE IF EXISTS stud_grade";
	st.executeUpdate(sql);
	}
}

/*

************
Output
************
F:\jdbc-ass>javac Proc_Grade.java
F:\jdbc-ass>java Proc_Grade

Enter Student Id : 101

Grade Details : 
Assignment no: 1  Grade: A

*/