<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix ="fn" %>

<html>

<head>
<title>Student Tracker App</title>
<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<%-- <%
	// using jstl we don't require this step as it does the work behind the scenes
 	List<Student> theStudents = (List<Student>)request.getAttribute("STUDENTS");
	
%> --%>   

<body>
	<div id="wrapper">
		<div id="header">
			<h1>Symbiosis University</h1>
		</div>
	</div>
	
	
	<div id="container">
		<div id="content">
			<!-- Place the add button here -->
			<input type="button" value="Add student" onclick="window.location.href='add-student-form.jsp';return false;" 
			class="add-student-button"> 
			
			<!--  add a search box -->
            <form action="StudentControllerServlet" method="GET">
        
                <input type="hidden" name="command" value="SEARCH" />
            
                Search student: <input type="text" name="theSearchName" />
                
                <input type="submit" value="Search" class="add-student-button" />
            
            </form>
			
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				
			<%-- <% for (Student stud: theStudents){ %>
				<tr>
					<td><%=stud.getFirstName() %></td>  // with jsp
					<td><%=stud.getLastName() %></td>
					<td><%=stud.getEmail() %></td>
				</tr>
					
			 <% } %>    --%>
				
				<c:forEach var="stud" items="${STUDENTS}">
				
					<!-- set up a link for each student -->
					<c:url var ="tempLink" value="StudentControllerServlet">
						<c:param name="command" value="LOAD"/>
						<c:param name="studentId" value="${stud.id}" />
					</c:url>
					
					<c:url var ="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"/>
						<c:param name="studentId" value="${stud.id}" />
					</c:url>
					<tr>
						<td>${stud.firstName}</td>  
						<td>${stud.lastName}</td>
						<td>${stud.email}</td>
						<td>
							<a href="${tempLink}">Update</a>
							|
							<a href="${deleteLink}"
							onclick="if(!(confirm('You sure to delete?'))) return false">Delete</a>
						</td>
					</tr>
				</c:forEach>
			
			</table>
		</div>
	</div>

</body>

</html>