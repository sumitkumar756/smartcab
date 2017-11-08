<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/bg.css" />
<meta charset="ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<a href="index.jsp">Home</a>
	<div align="center">
	<h4>Welcome to Smart Cab</h4>
	<br>
		<form action="./registration" method="post">
			<table border="1px solid black">
				<tr>
					<td>Name:</td>
					<td><input type="text" name="name" required="required" /><br /></td>
				</tr>
				<tr>
					<td>Mobile:</td>
					<td><input type="text" name="number" required="required"
						width="13" placeholder="+91" /><br /></td>
				</tr>
				<tr>
					<td>Type:</td>

					<td><input type="radio" name="type" value="traveler" />traveler
						<input type="radio" name="type" value="driver" />  driver</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: center;"><input
						type="submit" value="Submit" /></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>