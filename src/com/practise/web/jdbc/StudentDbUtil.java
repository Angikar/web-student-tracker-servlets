package com.practise.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class StudentDbUtil {
	
	private static DataSource dataSource;

	public StudentDbUtil(DataSource thedataSource) {
		dataSource = thedataSource;
	}
	
	public static List<Student> getStudents() throws Exception{
		
		List<Student> students = new ArrayList<Student>();
		
		Connection myCon=null;
		Statement myStmt=null;
		ResultSet myRes=null;
		
		try {
			myCon = dataSource.getConnection();
			
			String sql="select * from student order by last_name";
			
			myStmt = myCon.createStatement();
			
			myRes=myStmt.executeQuery(sql);
			
			while(myRes.next()) {
				int id=myRes.getInt("id");
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String email = myRes.getString("email");
				
				Student tempStud = new Student(id,firstName,lastName,email);
				
				students.add(tempStud);
			}
			
			return students;
		}
		finally {
			close(myCon,myStmt,myRes);
		}
		
		
	}

	private static void close(Connection myCon, Statement myStmt, ResultSet myRes) {
		
		try {
			
			if(myRes!=null) {myRes.close();}
			if(myStmt!=null) {myStmt.close();}
			if(myCon!=null) {myCon.close();}
				
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws Exception {
		
		Connection myCon = null;
		PreparedStatement myStmt = null;
		
		try {
		//get db connection
			myCon = dataSource.getConnection();
		// create sql for insert
			String sql = "insert into student "
					+ "(first_name, last_name, email) "
					+ "values (?, ? ,?)";
			
			myStmt = myCon.prepareStatement(sql);
		
		//set the param values for student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
		//execute sql statement
			myStmt.execute();
		}
		
		finally {
		//clean up jdbc objects
			close(myCon,myStmt,null);
		}
	}

	public Student getStudent(String theStudentId) throws Exception{

		Student theStudent = null;
		Connection myCon = null;
		PreparedStatement myStmt = null;
		ResultSet myRes=null;
		int studentId;
		
		try {
			//convert studnet id to int
			studentId=Integer.parseInt(theStudentId);
			// get connection to database
			myCon = dataSource.getConnection();
			//create sql to get selected student
			String sql ="select * from student where id=?";
			//create prepared statement
			myStmt = myCon.prepareStatement(sql);
			//set params
			myStmt.setInt(1,studentId);
			//execute query
			myRes =myStmt.executeQuery();
			//retrieve data from result set row
			if(myRes.next()) {
				String firstName = myRes.getString("first_name");
				String lastName = myRes.getString("last_name");
				String email = myRes.getString("email");
				//use the studentid during construction
				theStudent = new Student(studentId,firstName,lastName,email);
			}
			else {
				throw new Exception("Could not find student id: " + studentId);
			}
			
			return theStudent;
		}
		finally {
			close(myCon,myStmt,myRes);
		}
		
			
	}

	public void updateStudent(Student theStudent) throws Exception {
		
	
		Connection myCon = null;
		PreparedStatement myStmt = null;
	try {
		myCon = dataSource.getConnection();
		
		String sql = "update student "+ 
		"set first_name=?,last_name=?, email=? " +
	    "where id=?";
		
		//prepare statement
		myStmt=myCon.prepareStatement(sql);
		
		myStmt.setString(1, theStudent.getFirstName());
		myStmt.setString(2, theStudent.getLastName());
		myStmt.setString(3, theStudent.getEmail());
		myStmt.setInt(4, theStudent.getId());
		
		
		myStmt.execute();
	}
	finally {
		// clean up JDBC objects
		close(myCon,myStmt,null);
	}
	}

	public void deleteStudent(String theStudentId) throws Exception {
		Connection myCon = null;
		PreparedStatement myStmt = null;
		try {
			//convert studentid to int
			int studentId=Integer.parseInt(theStudentId);
			myCon= dataSource.getConnection();
			String sql ="delete from student where id=?";
			myStmt=myCon.prepareStatement(sql);
			myStmt.setInt(1, studentId);
			myStmt.execute();
		}
		finally {
			close(myCon,myStmt,null);
		}
	}
}
