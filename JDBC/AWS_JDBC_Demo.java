/*

***********************************************************************************************
2) Implement a JDBC application by connecting on Cloud instead of localhost by using AWS service.
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.util.Scanner;
public class AWS_JDBC_Demo
{
	Connection conn;
	Scanner sc=new Scanner(System.in);
	public static void main(String[] args)
	{
		new AWS_JDBC_Demo();
		
	}

public AWS_JDBC_Demo()
{
		try
		{
			int ch;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url="jdbc:mysql://awsdatabase.catg4apjfhxw.us-east-1.rds.amazonaws.com:3306/awsdatabase";
			String uname="monik";

			String pwd="123456789";
			conn=DriverManager.getConnection(url,uname,pwd);
			do
			{
			System.out.println("\n0.Exit");
			System.out.println("1.Check Connection");
			System.out.println("2.Create Table");
			System.out.println("3.Insert Records");
			System.out.println("4.Update Records");
			System.out.println("5.Delete Records");
			
			System.out.println("\nEnter Your Choice : ");
			ch=sc.nextInt();
			
			switch(ch)
			{
				case 0 : 
						 break;						 
				case 1 :
						check_conn();								
						 break;	
				case 2: create_table();
						 break;
				case 3 : insert1();
						 break;
				case 4:
						update1();
						break;
				case 5:
						delete1();
						break;
						 
				
			}
			}while(ch!=0);
			
			conn.close();
			
		}
		catch(ClassNotFoundException ex)
		{
			System.out.println(ex.getMessage());
		}
		//catch(IllegalAccessException ex){System.out.println(ex.getMessage());}
		//catch(InstantiationException ex){System.out.println(ex.getMessage());}
		catch(SQLException ex){System.out.println(ex.getMessage());}
}
private void create_table()
{
		try
		{

		Statement st=conn.createStatement();
		String sql = "CREATE TABLE employee " +
                   "(id INTEGER not NULL, " +
                   " name VARCHAR(25), " +  
                   " age INTEGER, " + 
                   " PRIMARY KEY ( id ))"; 

			st.executeUpdate(sql);
			System.out.println("Created table in given database...");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
}
	private void update1()
	{
		try
		{
			int id;
			String name;			
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("select * from student");
			
			// Display Table Data
			while(rs.next())
			{
			System.out.println(rs.getInt("stud_id") + "\t" + rs.getString("name"));
			}
			
			//Update Static Value
			/*
			String sql="update student set name='abc' where stud_id=104";
			st.executeUpdate(sql);
			System.out.println("Updated..\n");
			*/
			
			//Update Dynamic Value
			
			System.out.println("Enter Student Name to Update : ");
			name=sc.next();
			System.out.println("Which Student Id do you want to Update : ");
			id=sc.nextInt();
			
			String sql="update student set name = ? where stud_id = ?";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1,name);
			ps.setInt(2,id);
			ps.executeUpdate();
			System.out.println("Updated..\n");
			
			
			// Display Table Data
			ResultSet rs1=st.executeQuery("select * from student");
			while(rs1.next())
			{
			System.out.println(rs1.getInt("stud_id") + "\t" + rs1.getString("name"));
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private void delete1()
	{
		
		try
		{
			int id;
			String name;			
			Statement st=conn.createStatement();
			ResultSet rs=st.executeQuery("select * from student");
			
			// Display Table Data
			/*
			while(rs.next())
			{
			System.out.println(rs.getInt("stud_id") + "\t" + rs.getString("name"));
			}
			*/
			
			//Update Dynamic Value
			
			System.out.println("Which Student Id do you want to Delete : ");
			id=sc.nextInt();
			
			while(rs.next())
			{
				if(rs.getInt("stud_id") == id)
				{
					String sql="delete from student where stud_id = ?";
					PreparedStatement ps=conn.prepareStatement(sql);
					ps.setInt(1,id);
					ps.executeUpdate();
					System.out.println("Deleted..\n");	
				}
				//System.out.println(rs.getInt("stud_id") + "\t" + rs.getString("name"));
			}			
			// Display Table Data
			ResultSet rs1=st.executeQuery("select * from student");
			while(rs1.next())
			{
			System.out.println(rs1.getInt("stud_id") + "\t" + rs1.getString("name"));
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
private void insert1()
{
		try
		{
 
		int id1,age;
		String name1;
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("select * from student");
			
			// Display Table Data
			while(rs.next())
			{
			System.out.println(rs.getInt("id") + "\t" + rs.getString("name")+ "\t" + rs.getInt("age"));
			}
			
			//Insert Static Value
			/*
			String sql="insert into student values(104,'Yash')";
			st.executeUpdate(sql);
			System.out.println("Inserted..\n");
			*/
			
			//Insert Dynamic Value
			System.out.println("Enter Student Id : ");
			id1=sc.nextInt();
			System.out.println("Enter Student Name : ");
			name1=sc.next();
			System.out.println("Enter Student Age : ");
			age=sc.nextInt();
			
			String sql="insert into student values(?,?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1,id1);
			ps.setString(2,name1);
			ps.setInt(3,age);
			ps.executeUpdate();
			System.out.println("Inserted..\n");
			
			
			// Display Table Data
			ResultSet rs1=st.executeQuery("select * from student");
			while(rs1.next())
			{
			System.out.println(rs1.getInt("id") + "\t" + rs1.getString("name")+ "\t" + rs1.getInt("age"));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
}

private void check_conn()
{
	System.out.println("Connecting to AWS...");
	try
	{
			//Check if Connection is Created or Not
			if(conn.isClosed())
			{
				System.out.println("Connection is Closed !");
			}
			else
			{
				System.out.println("Connection is Created !");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
}		
}

/*

************
Output
************

F:\jdbc-ass>javac AWS_JDBC_Demo.java

F:\jdbc-ass>java AWS_JDBC_Demo

0.Exit
1.Check Connection
2.Create Table
3.Insert Records
4.Update Records
5.Delete Records

Enter Your Choice : 1
Connecting to AWS...
Connection is Created !

0.Exit
1.Check Connection
2.Create Table
3.Insert Records
4.Update Records
5.Delete Records

Enter Your Choice : 2
Created table in given database...

0.Exit
1.Check Connection
2.Create Table
3.Insert Records
4.Update Records
5.Delete Records

Enter Your Choice : 3

Enter Student Id : 101
Enter Student Name : Monik
Enter Student Age : 23
Inserted..

101 Monik 23

0.Exit
1.Check Connection
2.Create Table
3.Insert Records
4.Update Records
5.Delete Records

Enter Your Choice : 4

101 Monik 23

Enter Student Name to Update : Ram
Which Student Id do you want to Update : 101
Updated..

101 Ram 23

0.Exit
1.Check Connection
2.Create Table
3.Insert Records
4.Update Records
5.Delete Records

Enter Your Choice : 0

F:\jdbc-ass>

*/