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

				if (Bookings.getSingletonInstance().availableDrivers.size() > 0) {

					GPS gps = new GPS();
					Driver availableDriver = Bookings.getSingletonInstance().availableDrivers.parallelStream()
							.filter(driver -> gps.getdistanceBetweenTwoGPS(person, driver) <= 2).findAny().orElse(null);

					if (availableDriver != null) {

						Booking newBooking = new Booking();
						double distance = Double.parseDouble(request.getParameter("distance"));
						newBooking.setFare(14*distance);
						newBooking.setTraveler((Traveler) person);
						Bookings.getSingletonInstance().availablebookings.add(newBooking);
						System.out.println("Requested for booking :" + newBooking);
						request.setAttribute("message", "CAB requested for booking id :"
								+ newBooking.getUuid().toString() + " wait for driver to confirm the booking");
					} else {
						request.setAttribute("message", "No CAB available in range of 2 km !!");
					}
				} else {
					request.setAttribute("message", "No CAB available right now !!");
				}
			} else {
				if (request.getParameter("submit").contains("go online for bookings")) {
					Bookings.getSingletonInstance().availableDrivers.add((Driver) person);
					request.setAttribute("message",
							"Hi " + person.getName() + " travelers are waiting for you. find traveler");
				} else {

					if (Bookings.getSingletonInstance().availablebookings.isEmpty()) {
						request.setAttribute("message",
								"No booked trip available from travelers right now waiting for new booking");
					} else {
						GPS gps = new GPS();

						// java-8 code to find the traveler in range of 2 km and
						// with max trip fare
						Booking servingBooking = Bookings.getSingletonInstance().availablebookings.parallelStream()
								.filter(booking -> !booking.isConfirmed())
								.max(Comparator.comparingDouble(booking -> booking.getFare())).orElse(null);

						if (servingBooking != null) {
							System.out.println("confirmed booking :" + servingBooking);
							request.setAttribute("message", "Booking id : " + servingBooking.getUuid()
									+ " confirmed for traveler :" + servingBooking.getTraveler());
							Bookings.getSingletonInstance().setBookings(
									Bookings.getSingletonInstance().availablebookings.parallelStream().filter(booking -> !booking.isConfirmed())
									.map(booking -> {
										if (booking.getUuid().toString().equals(servingBooking.getUuid().toString())) {
											booking.setConfirmed(true);
											booking.setDriver((Driver) person);
										}
										return booking;
									}).collect(Collectors.toList()));
						} else {
							request.setAttribute("message",
									"No booked trip available from travelers right now waiting for new booking");
						}

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
