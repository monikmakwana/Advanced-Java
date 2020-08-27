/*

***********************************************************************************************
4)Create a Function for above created tables, which passes the destination (tocity) and fetches all the details of the flight for that particular city.
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Search_Flight
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
		System.out.println("Enter Destination City: ");
		String toCity = br.readLine();
		destination(toCity);
		con.close();
	}
	
	public static void destination(String destination) throws Exception
	{
		int no;
		Statement st = con.createStatement();
		String sql = "CREATE FUNCTION if not exists `flight`(`toCity1` VARCHAR(45)) RETURNS INT NOT DETERMINISTIC READS SQL DATA SQL SECURITY DEFINER BEGIN DECLARE no_flights int; select count(flightNum) INTO no_flights from routes where toCity=toCity1; RETURN no_flights; END";
		st.executeUpdate(sql);
		
		CallableStatement stmt = con.prepareCall("{?=call flight(?)}");
        stmt.registerOutParameter(1,Types.INTEGER);
		stmt.setString(2, destination);
		stmt.execute();		
		no = stmt.getInt(1);
		System.out.println("Total Flights for this destination is: "+no);		
		st = con.createStatement();
		sql="DROP FUNCTION IF EXISTS flight";
		st.executeUpdate(sql);
	}
}

/*

************
Output
************
F:\jdbc-ass>javac Search_Flight.java
F:\jdbc-ass>java Search_Flight
Enter Destination City: Ahmedabad
Total Flights for this destination is: 9
F:\jdbc-ass>

*/