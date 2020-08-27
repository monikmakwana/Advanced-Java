/*

***********************************************************************************************
5) Develop JDBC application for SQLException which uses printSQLException to output the SQLState, error code, error description, and cause (if there is one) contained in the SQLException as well as any other exception chained to it: (Refer Oracle docs for SQL exception)
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.io.*;
import java.util.*;
class sql_exception
{
	static Connection con;
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static Statement st = null;
	static ResultSet rs = null;	
	public static void main(String args[]) throws Exception
	{
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_ass?serverTimezone=UTC","root","");
		
		try
		{
			st = con.createStatement();
			rs = st.executeQuery("Select * from rcc");
			if(rs.next())
				System.out.println("No error till now");		
			st = con.createStatement();
			rs = st.executeQuery("Select * from rcc");
		}
		catch(SQLException e)
		{
			printSQLException(e);
		}
		finally
		{
			con.close();
		}
	}	
	public static void printSQLException(SQLException ex)
	{
		for (Throwable e : ex)
		{
			if (e instanceof SQLException)
			{
				if (ignoreSQLException(((SQLException)e).getSQLState()) == false)
				{
					e.printStackTrace(System.err);
					System.err.println("SQLState: " + ((SQLException)e).getSQLState());
					System.err.println("Error Code: " + ((SQLException)e).getErrorCode());
					System.err.println("Message: " + e.getMessage());
					Throwable t = ex.getCause();
					while(t != null)
					{
						System.out.println("Cause: " + t);
						t = t.getCause();
					}
				}
			}
		}
	}	
	public static boolean ignoreSQLException(String sqlState)
	{
		if (sqlState == null)
		{
			System.out.println("The SQL state is not defined!");
			return false;
		}		
		if (sqlState.equalsIgnoreCase("X0Y32"))
			return true;		
		if (sqlState.equalsIgnoreCase("42Y55"))
			return true;
		return false;
	}
}

/*

************
Output
************
F:\jdbc-ass>javac sql_exception.java
F:\jdbc-ass>java sql_exception
java.sql.SQLSyntaxErrorException: Table 'jdbc_ass.rcc' doesn't exist
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:120)
        at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:97)
        at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:122)
        at com.mysql.cj.jdbc.StatementImpl.executeQuery(StatementImpl.java:1218)
        at sql_exception.main(sql_exception.java:20)
SQLState: 42S02
Error Code: 1146
Message: Table 'jdbc_ass.rcc' doesn't exist

F:\jdbc-ass>

*/