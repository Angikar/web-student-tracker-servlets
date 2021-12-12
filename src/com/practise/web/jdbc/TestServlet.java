package com.practise.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.net.httpserver.Authenticator.Result;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// set up printwriter
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		
		// set up connection to database
		
		Connection myCon = null;
		Statement myStmt=null;
		ResultSet myRes = null;
		
		try {
			myCon=dataSource.getConnection();
			// create sql statement
			String sql ="Select * from student";
			myStmt = myCon.createStatement();
			
			// execute the sql statement
			myRes= myStmt.executeQuery(sql);
			
			//Process the result set
			while(myRes.next()) {
				String email1 = myRes.getString("email");
				out.println(email1);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		}
		
	}

