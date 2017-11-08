package com.smart.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gps.GPS;
import com.smart.cab.Booking;
import com.smart.cab.Bookings;
import com.smart.entity.Driver;
import com.smart.entity.Person;
import com.smart.entity.Traveler;

public class SmartCabBooking extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (!request.getParameter("submit").equals("logout")) {
			Person person = (Person) session.getAttribute("person");
			person.setLatitude(Double.parseDouble(request.getParameter("latitude")));
			person.setLongitude(Double.parseDouble(request.getParameter("longitude")));
			if (person instanceof Traveler) {
				Booking newBooking = new Booking();
				newBooking.setFare(Math.random());
				newBooking.setTraveler((Traveler) person);
				Bookings.getSingletonInstance().bookings.add(newBooking);
				System.out.println("requested for booking :" + newBooking);
				request.setAttribute("message", "CAB requested for booking id :" + newBooking.getUuid().toString()
						+ " wait for driver to confirm the booking");
			} else {
				if (Bookings.getSingletonInstance().bookings.isEmpty()) {
					request.setAttribute("message",
							"No booked trip available from travelers right now waiting for new booking try later");
				} else {
					GPS gps = new GPS();
					
				// java-8 code to find the traveler in range of 2 km and with max trip fare	
					Booking servingBooking = Bookings.getSingletonInstance().bookings.parallelStream().filter(booking -> gps.getdistanceBetweenTwoGPS(person,booking.getTraveler()) <= 2
							&& !booking.isConfirmed()
							).max(Comparator.comparingDouble(booking -> booking.getFare())).orElse(null);
					
						
				  // java-7 code to find the traveler in range of 2 km and with max trip fare
				  /*	Booking servingBooking = null;
					    double maxFare = Double.MIN_VALUE;
					
				        for (Booking booking : Bookings.getSingletonInstance().bookings) {
						System.out.println("distance in km :" + gps.getdistanceBetweenTwoGPS(person, booking.getTraveler()));
						
						// code to get the all bookings from travelers in range of 2 km with max fare
						double distanceToTraveler = gps.getdistanceBetweenTwoGPS(person, booking.getTraveler());
						if (2 >= distanceToTraveler && maxFare < booking.getFare()) {
							maxFare = booking.getFare();
							servingBooking = booking;
						}
					}
				  */	
					if(servingBooking != null){
						System.out.println("confirmed booking :"+servingBooking);
						request.setAttribute("message", "Booking id : "+servingBooking.getUuid()+" confirmed for traveler :" + servingBooking.getTraveler());
						Bookings.getSingletonInstance().setBookings(Bookings.getSingletonInstance().bookings.parallelStream().map(booking -> {
							if(booking.getUuid().toString().equals(servingBooking.getUuid().toString())){
								booking.setConfirmed(true);
								booking.setDriver((Driver) person);
							}
							return booking;
						}).collect(Collectors.toList()));
					}else{
						request.setAttribute("message", "No booked trip available within 2 km range from travelers right now");
					}
					
					
				}
			}
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		} else {
			session.invalidate();
			request.setAttribute("message", "logout successfull redirecting to login page");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
