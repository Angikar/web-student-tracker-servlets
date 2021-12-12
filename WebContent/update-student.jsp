<html>

<head>
	<title>Update Student Form</title>
	<link type="css/text" rel="stylesheet" href="css/add-student-style.css">
	<link type="text/css" rel="stylesheet" href="css/style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h1>Symbiosis University</h1>
		</div>
	</div>
	
	<div id="container">
		<h3>Add Student</h3>
		
		<form action="StudentControllerServlet" method="GET">
			<input type="hidden" name="command" value="UPDATE"/>
			<input type="hidden" name="studentId" value="${STUDENT.id}"/>
			
			<table>
				<tbody>
					<tr>
						<td><label>First name:</label></td>
						<td><input type="text" name="firstName" value="${STUDENT.firstName}"/></td>
						<!-- "STUDENT" prepopulates the form data with the attribute from loadStudent method -->
					</tr>
					
					<tr>
						<td><label>Last name:</label></td>
						<td><input type="text" name="lastName" value="${STUDENT.lastName}"/></td>
					</tr>
					
					<tr>
						<td><label>Email:</label></td>
						<td><input type="text" name="email" value="${STUDENT.email}"/></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="save" class="save"/></td>
					</tr>
				</tbody>
			</table>
		
		</form>
		
		<div>
		<p> <a href="StudentControllerServlet">Back to List</a></p>
		</div>
	
	</div>
	
</body>

</html>