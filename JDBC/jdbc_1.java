/*

***********************************************************************************************
1) Create a database in MySQL, which  contains two very simple tables.  One holds student information and the other holds assignment grade information.  Our goal is to provide a very rudimentary interface to the database allowing for some simple manipulation of the data. You will need to allow the user to:
a.	Create a new student, with status showing whether that person has left the college or not.
b.	Add grades for an assignment.
c.	You will need to prompt the user for the assignment number, and then prompt the user for each student's grade.  When asking for the grade make sure you display the student's name and the assignment number (i.e. Please enter abc xyz's Assignment 5 Grade:).
d.	Change a grade for an assignment (i.e. this should work on only one student)
e.	View all the grades for an assignment
f.	Delete all assignment grade related info for the students who had left.
***********************************************************************************************

************
Program
************

*/

import java.sql.*;
import java.io.*;
class jdbc_1
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
		create_table();
		menu();
		con.close();
	}	
	public static void create_table() throws Exception
	{
		st = con.createStatement();
		sql = "Create table if not exists student(id int auto_increment primary key, name varchar(25), status boolean)";
		st.executeUpdate(sql);
		st = con.createStatement();
		sql = "Create table if not exists grades(id int, assignment int, grade char, foreign key(id) references student(id), primary key(id,assignment))";
		st.executeUpdate(sql);
	}	
	public static void menu() throws Exception
	{
		int ch;
		do
		{
			System.out.println("\n0. Exit");
			System.out.println("1. Insert Student");
			System.out.println("2. Add Grades");
			System.out.println("3. Change Grade");
			System.out.println("4. Delete Student's Grade Who had left");
			System.out.println("5. Display Student Details");			
			System.out.print("\nEnter Your Choice: ");
			ch = Integer.parseInt(br.readLine());			
			switch(ch)
			{
				case 0:
						System.out.println("Exit");
						break;				
				case 1:
						Insert_Student();
						break;
				case 2:
						Insert_Assignment();
						break;
				case 3:
						Update_Grade();
						break;
				case 4:
						Delete_Grade();
						break;
				case 5:
						Display_Grade();
						break;
				default:
						System.out.println("Please Enter Proper Choice");
						break;
			}
		}while(ch!=0);
	}
	
	public static void Insert_Student() throws Exception
	{	
		System.out.print("\nEnter Student Name: ");
		String par2 = br.readLine();
		
		System.out.print("Existing Student(y/n): ");
		String par3 = br.readLine();
		
		sql = "insert into student(name,status) values(?,?)";
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, par2);
		if(par3.equals("y"))
			pstmt.setBoolean(2, true);
		else
			pstmt.setBoolean(2, false);

		pstmt.executeUpdate();		
		System.out.println("Record Inserted..");
	}
	
	public static void Insert_Assignment() throws Exception
	{
		try
		{
			System.out.print("\n\nEnter Student Name: ");
			String nm = br.readLine();			
			System.out.print("\n\nEnter Assignment Number: ");
			int assno = Integer.parseInt(br.readLine());			
			System.out.print("\n\nEnter Grade: ");
			String grade = br.readLine();
			
			st = con.createStatement();
			sql = "select id from student where name='" + nm + "'";
			rs = st.executeQuery(sql);
			rs.next();
			
			st = con.createStatement();
			sql = "insert into grades values("+ rs.getInt("id")+"" + ", " + assno+"" + ", '" + grade + "')";
			st.executeUpdate(sql);
			System.out.println("Record Inserted..");
		}
		catch(Exception e)
		{
			System.out.println("\nGrades for this Student is already exist..");
		}
	}
	
	public static void Update_Grade() throws Exception
	{
		try
		{
			System.out.print("\n\nEnter Student Name: ");
			String nm = br.readLine();
			
			System.out.print("\n\nEnter Assignment Number: ");
			int assno = Integer.parseInt(br.readLine());
			
			System.out.print("\n\nEnter New Grade: ");
			String grade = br.readLine();
			
			st = con.createStatement();
			sql = "select id from student where name='" + nm + "'";
			rs = st.executeQuery(sql);
			rs.next();
			
			st = con.createStatement();
			sql = "update grades set grade = '"+ grade + "' where id = " + rs.getInt("id")+"";
			st.executeUpdate(sql);			
			System.out.println("Record Updated..");
		}
		catch(Exception e)
		{
			System.out.println("\nRecord Not Found");
		}
	}
	
	public static void Delete_Grade() throws Exception
	{
		try
		{
			while(true)
			{
				System.out.println("Remove Student?(y/n)");
				String option = br.readLine();
			
				if(option.equals("y"))
				{
					System.out.println("\nEnter Student name to remove: ");
					String str = br.readLine();
					st=con.createStatement();
					sql = "update student set status = 0 where name='"+ str +"'";
					st.executeUpdate(sql);
				}
				else
					break;
			}
			
			st=con.createStatement();
			sql = "delete from grades where id in (select id from student where status = 0)";
			st.executeUpdate(sql);
			
			System.out.println("Record Deleted..");
		}
		catch(Exception e)
		{
			System.out.println("Student already left..");
		}
	}
	
	public static void Display_Grade() throws Exception
	{
		try
		{
			System.out.print("\nEnter Student name to display details: ");
			String str = br.readLine();
			st=con.createStatement();
			sql = "select id from student where name='"+ str +"'";
			rs = st.executeQuery(sql);
			rs.next();
			
			st=con.createStatement();
			sql = "select * from grades where id="+ rs.getInt("id")+"";
			rs = st.executeQuery(sql);
			
			if(rs==null)
			{
				System.out.println("\nRecord Not Found");
			}
			else
			{
				System.out.println("\nDetails for " + str + "\n\n");
				while(rs.next())
				{
					System.out.println("Assignment-"+rs.getInt("assignment")+" : Grade-"+rs.getString("grade"));
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Student already left..");
		}
	}
}

/*

************
Output
************
F:\jdbc-ass>javac jdbc_1.java
F:\jdbc-ass>java jdbc_1

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 1

Enter Student Name: Monik
Existing Student(y/n): y
Record Inserted..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: Monik

Details for Monik



0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 2


Enter Student Name: Monik


Enter Assignment Number: 1


Enter Grade: A
Record Inserted..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: monik

Details for monik


Assignment-1 : Grade-A

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 3


Enter Student Name: monik


Enter Assignment Number: 1


Enter New Grade: B
Record Updated..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: monik

Details for monik


Assignment-1 : Grade-B

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 1

Enter Student Name: Manan
Existing Student(y/n): n
Record Inserted..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 2


Enter Student Name: manan


Enter Assignment Number: 1


Enter Grade: C
Record Inserted..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: manan

Details for manan


Assignment-1 : Grade-C

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 4
Remove Student?(y/n)
y

Enter Student name to remove:
manan
Remove Student?(y/n)
y

Enter Student name to remove:
manan
Remove Student?(y/n)
n
Record Deleted..

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: monik

Details for monik


Assignment-1 : Grade-B

0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 5

Enter Student name to display details: manan

Details for manan



0. Exit
1. Insert Student
2. Add Grades
3. Change Grade
4. Delete Student's Grade Who had left
5. Display Student Details

Enter Your Choice: 0
Exit
F:\jdbc-ass>

*/