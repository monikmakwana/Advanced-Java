// Basics Of JDBC...

import java.sql.*;
import java.util.Scanner;
public class JDBCDemo
{
	Connection conn;
	Scanner sc=new Scanner(System.in);
	public static void main(String[] args)
	{
		new JDBCDemo();
	}
	public JDBCDemo()
	{
		try
		{
			int ch;
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			String url="jdbc:mysql://localhost:3306/jdbc?serverTimezone=UTC";
			String uname="root";
			String pwd="";
			conn=DriverManager.getConnection(url,uname,pwd);
			do
			{
			System.out.println("\n0.Exit");
			System.out.println("1.Check Connection");
			System.out.println("2.Insert Records");
			System.out.println("3.Update Records");
			System.out.println("4.Delete Records");
			
			System.out.print("\n\nEnter Your Choice : ");
			ch=sc.nextInt();
			switch(ch)
			{
				case 0 : 
					 break;
				case 1 :
					check_conn();								
					 break;			
				case 2 : insert1();
					 break;
				case 3:
					update1();
					break;
				case 4:
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
		catch(IllegalAccessException ex){System.out.println(ex.getMessage());}
		catch(InstantiationException ex){System.out.println(ex.getMessage());}
		catch(SQLException ex){System.out.println(ex.getMessage());}
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
			System.out.print("\nEnter Student Name to Update : ");
			name=sc.next();
			System.out.print("\nWhich Student Id do you want to Update : ");
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
			
			System.out.print("\nWhich Student Id do you want to Delete : ");
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
 
		int id1;
		String name1;
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery("select * from student");
			
			// Display Table Data
			while(rs.next())
			{
			System.out.println(rs.getInt("stud_id") + "\t" + rs.getString("name"));
			}
			
			//Insert Static Value
			/*
			String sql="insert into student values(104,'Yash')";
			st.executeUpdate(sql);
			System.out.println("Inserted..\n");
			*/
			
			//Insert Dynamic Value
			System.out.print("\nEnter Student Id : ");
			id1=sc.nextInt();
			System.out.print("\nEnter Student Name : ");
			name1=sc.next();
			
			String sql="insert into student values(?,?)";
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1,id1);
			ps.setString(2,name1);
			ps.executeUpdate();
			System.out.println("Inserted..\n");
			
			
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
	private void check_conn()
	{
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

F:\jdbc-ass>javac JDBCDemo.java
F:\jdbc-ass>java JDBCDemo
0.Exit
1.Check Connection
2.Insert Records
3.Update Records
4.Delete Records

Enter Your Choice : 1
Connection is Created !

0.Exit
1.Check Connection
2.Insert Records
3.Update Records
4.Delete Records


Enter Your Choice : 2
101     Monik
102     Raj
103     Manan
104     Keyur
105     Ram

Enter Student Id : 106

Enter Student Name : Kevin
Inserted..

101     Monik
102     Raj
103     Manan
104     Keyur
105     Ram
106     Kevin

0.Exit
1.Check Connection
2.Insert Records
3.Update Records
4.Delete Records


Enter Your Choice : 3
101     Monik
102     Raj
103     Manan
104     Keyur
105     Ram
106     Kevin

Enter Student Name to Update : Shyam

Which Student Id do you want to Update : 106
Updated..

101     Monik
102     Raj
103     Manan
104     Keyur
105     Ram
106     Shyam

0.Exit
1.Check Connection
2.Insert Records
3.Update Records
4.Delete Records


Enter Your Choice : 4

Which Student Id do you want to Delete : 103
Deleted..

101     Monik
102     Raj
104     Keyur
105     Ram
106     Shyam

0.Exit
1.Check Connection
2.Insert Records
3.Update Records
4.Delete Records


Enter Your Choice : 0
F:\jdbc-ass>

*/