<%@ page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="./css/bg.css" />
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<script type="text/javascript">
 if('${message}'){
	  alert('${message}');
	}
 </script>
<body>
<a href="index.jsp">Home</a>
	<div align="center">
		<h4>Welcome to Smart Cab</h4>
		<br>
		<form action="./validator" method="post">
			<table border="1px solid black">
				<tr>
					<td>Mobile:</td>
					<td><input type="text" name="number" width="13"
						placeholder="+91 with 10 digit" /><br /></td>
				</tr>
				<tr>
					<td>Login As:</td>
					<td><input type="radio" name="type" value="traveler" />traveler<br/>
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