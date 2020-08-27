/*

***********************************************************************************************
6)  Make use of for each loop with map to update repeatedly values in PreparedStatement object (Hint: The method CoffeesTable.updateCoffeeSales takes one argument, HashMap. Each element in the HashMap argument contains the name of one type of coffee and the number of pounds of that type of coffee sold during the current week. The for-each loop iterates through each element of the HashMap argument and sets the appropriate question mark placeholders in updateSales and updateTotal.)
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.io.*;
import java.util.*;
class coffee_details
{
	static Connection con;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Statement st = null;
	static ResultSet rs = null;
	
	public static void main(String args[]) throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_ass?serverTimezone=UTC","root","");
		
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		data.put("Robusta",25);
		data.put("Cappuccino",50);
		data.put("Black Coffee",12);
		data.put("Frappuccino",9);
		data.put("Americano",23);
		data.put("Dalgona",35);
		
		updateCoffeeSales(data);
		con.close();
	}	
	public static void updateCoffeeSales(HashMap<String, Integer> salesForWeek) throws SQLException
	{
		PreparedStatement updateSales = null;
		PreparedStatement updateTotal = null;		
		String updateString = "update coffees set sales = ? where coff_name = ?";
		String updateStatement = "update coffees set TOTAL = TOTAL + ? where coff_name = ?";		
		try
		{
			updateSales = con.prepareStatement(updateString);
			updateTotal = con.prepareStatement(updateStatement);			
			for (Map.Entry<String, Integer> e : salesForWeek.entrySet())
			{
				updateSales.setInt(1, e.getValue().intValue());
				updateSales.setString(2, e.getKey());
				updateSales.executeUpdate();
				updateTotal.setInt(1, e.getValue().intValue());
				updateTotal.setString(2, e.getKey());
				updateTotal.executeUpdate();
			}
			st = con.createStatement();
			rs = st.executeQuery("Select * from coffees");			
			System.out.println("\nUpdated Coffee Table Values: ");
			while(rs.next())
			{
				System.out.println("Coffee Name: " + rs.getString("coff_name") + "\tThis Week Sale: " + rs.getInt("sales") + "\tTotal sales: " + rs.getInt("Total"));
			}
		}
		catch (SQLException e )
		{
			System.out.println(e);
		}
		finally
		{
			if (updateSales != null)
				updateSales.close();
			
			if (updateTotal != null)
				updateTotal.close();
        }
    }
}

/*

************
Output
************
F:\jdbc-ass>javac coffee_details.java
F:\jdbc-ass>java coffee_details
Updated Coffee Table Values:
Coffee Name: Robusta  	This Week Sale: 25    	Total Sales: 389
Coffee Name: Cappuccino 	This Week Sale: 50    	Total Sales: 180
Coffee Name: Black Coffee   	This Week Sale: 12    	Total Sales: 44
Coffee Name: Frappuccino    	This Week Sale: 9       Total Sales: 78
Coffee Name: Americano  	This Week Sale: 23      Total Sales: 122
Coffee Name: Dalgona   	This Week Sale: 35      Total Sales: 179

F:\jdbc-ass>

*/