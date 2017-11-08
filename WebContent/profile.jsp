<%@ page language="java" session="false" import="com.smart.entity.*"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.smart.cab.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script type="text/javascript">
	if ('${message}') {
		alert('${message}');
	}
	function initGeolocation() {
		if (navigator.geolocation) {
			// Call getCurrentPosition with success and failure callbacks
			navigator.geolocation.getCurrentPosition(success, fail);
		} else {
			alert("Sorry, your browser does not support geolocation services.");
		}
	}

	function success(position) {

		document.getElementById('longitude').value = position.coords.longitude;
		document.getElementById('latitude').value = position.coords.latitude
	}

	function fail() {
		alert("Geolocation access failed try again ");
	}
</script>



<head>
<link rel="stylesheet" type="text/css" href="./css/bg.css" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Profile</title>
</head>
<body onLoad="initGeolocation();">
	<div align="center">
		<h4>Welcome to Smart Cab</h4>
		<br>
	</div>

	<div align="center">

		<br>
		<form action="./operation" method="post">
			<input type="hidden" name="longitude" id="longitude" /> <input
				type="hidden" name="latitude" id="latitude" />
			<%
				Person person = (Person) request.getSession(false).getAttribute("person");
				if (person instanceof Driver) {
					boolean isDriverOnline = Bookings.getSingletonInstance().getAvailableDrivers()
							.contains((Driver) person);
					boolean isDriverOnTrip = BookingUtility.isDriverOnTrip(person);
					if (!isDriverOnline) {
			%>
			<input type="submit" name="submit"
				value="Let's go online for bookings" /><br> <br>
			<%
				}
					if (isDriverOnline && !isDriverOnTrip) {
			%>
			<input type="submit" name="submit" value="Search for Traveler" /><br>
			<br>
			<%
				}
					if (isDriverOnline && isDriverOnTrip) {
			%>
			<textarea rows="2" cols="500"><%=BookingUtility.checkStatusOfBooking(person)%></textarea>
			<%
				}

				} else {
					String confimationMessage = BookingUtility.checkStatusOfBooking(person);
					if (confimationMessage != null) {
			%>
			<textarea rows="2" cols="500"><%=confimationMessage%></textarea>
			<%
				} else {
			%>
			<input type="text" name="distance" width="3"
				placeholder="under 1000 km"> <input type="submit"
				name="submit" value="Request for CAB" />
			<%
				}
				}
			%>
			<br /> <br /> <input type="submit" name="submit" value="logout" />

		</form>
	</div>
</body>
</html>