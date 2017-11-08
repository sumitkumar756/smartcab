package com.smart.cab;

import com.smart.entity.Driver;
import com.smart.entity.Person;

public class BookingUtility {

	public static String checkStatusOfBooking(Person person) {

		String message = null;

		if (Bookings.getSingletonInstance().availablebookings.parallelStream().filter(booking -> !booking.isConfirmed()
				&& booking.getTraveler().getMobileNumber().equals(person.getMobileNumber())).count() > 0) {
			message = "Your trip for booking is InProgress will be confirmed while Cab driver accepts";
		} else {
			if (person instanceof Driver) {

				Booking confirmedBooking = Bookings.getSingletonInstance().availablebookings.parallelStream()
				.filter(booking -> booking.isConfirmed() && booking.getDriver().equals(person)).findFirst().orElse(null);
				
				if(confirmedBooking != null){
					message = "You are on trip with tripid:"+confirmedBooking.getUuid().toString()+ " with below traveler details: \n"+ 
				  confirmedBooking.getTraveler();
				}
				
				
			} else {
				Booking confimredBooking = Bookings.getSingletonInstance().availablebookings.parallelStream()
						.filter(booking -> booking.isConfirmed()
								&& booking.getTraveler().equals(person))
						.findFirst().orElse(null);

				if (confimredBooking != null) {
					message = "Your booking is confirmed by cab driver trip id :" + confimredBooking.getUuid().toString()
							+ " is booked find the driver details below\n" + confimredBooking.getDriver().toString();
				}
			}
		}
		return message;
	}

	public static boolean isDriverOnTrip(Person person) {
		return Bookings.getSingletonInstance().getBookings().parallelStream()
				.filter(booking -> booking.isConfirmed() && booking.getDriver().equals(person)).count() > 0;
	}

}
