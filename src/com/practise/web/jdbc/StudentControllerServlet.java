package com.practise.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	

	@Override
	public void init() throws ServletException {  // override init() method
		super.init();
		try {
			studentDbUtil= new StudentDbUtil(dataSource);
		}
		catch(Exception ex) {
			throw new ServletException(ex);
		}
	}
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			// read command parameter
			String theCommand = request.getParameter("command");
			// if the command is missing then default to listing students
			if (theCommand ==null) {
				theCommand ="LIST";
			}
			// route to appropriate method
			switch(theCommand) {
			
			case "LIST":
				listStudents(request, response);
				break;
			case "ADD":
				addStudents(request,response);
				break;
			case "LOAD":
				loadStudent(request,response);
				break;
			case "UPDATE":
				updateStudent(request,response);
				break;
			case "DELETE":
				deleteStudent(request,response);
			default:
				listStudents(request,response);			
			}
		
	}catch(Exception ex) {
		throw new ServletException(ex);}
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
				//read student info from form data
				String theStudentId=request.getParameter("studentId");
				
				//perform update on database
				studentDbUtil.deleteStudent(theStudentId);
				
				//send them back to the "list students" page
				listStudents(request,response);
		
	}



	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student info from form data
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		//create a new student object
		Student theStudent = new Student(id,firstName,lastName,email);
		//perform update on database
		studentDbUtil.updateStudent(theStudent);
		
		//send them back to the "list students" page
		listStudents(request,response);
		
	}



	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		//read student id from data
		String theStudentId = request.getParameter("studentId");
		//get student from database(db util)
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		//place student in the request attribute
		request.setAttribute("STUDENT", theStudent);
		//send to jsp page:update-student.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("update-student.jsp");
		dispatcher.forward(request, response);
		
	}



	private void addStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//read student info form data
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		//create a new student object
		
		Student theStudent = new Student(firstName,lastName,email);
		//add the data to the db
		studentDbUtil.addStudent(theStudent);
		//send back to the main page
		listStudents(request,response);
	}



	private void listStudents(HttpServletRequest request, HttpServletResponse response)
	throws Exception
	{
		
		// get the students from dbutils
		
		List<Student> students = StudentDbUtil.getStudents();
		
		// add students to the request
		
		request.setAttribute("STUDENTS", students);
		
		// send the dispatcher to jsp page
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("list_students.jsp");
		requestDispatcher.forward(request, response);
		
	}

}
