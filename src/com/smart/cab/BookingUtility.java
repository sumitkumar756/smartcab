package com.smart.cab;

import com.smart.entity.Person;

public class BookingUtility {

	public static String checkExisitingConfirmedBooking(Person person) {

		String message = null;

		if (Bookings.getSingletonInstance().bookings.parallelStream().filter(booking -> !booking.isConfirmed()
				&& booking.getTraveler().getMobileNumber().equals(person.getMobileNumber())).count() > 0) {
			message = "Your trip booking is InProgress will be confirmed while Cab driver accepts";
		} else {

			Booking confimredBooking = Bookings.getSingletonInstance().bookings.parallelStream()
					.filter(booking -> booking.isConfirmed()
							&& booking.getTraveler().getMobileNumber().equals(person.getMobileNumber()))
					.findFirst().orElse(null);

			if (confimredBooking != null) {
				message = "Your booking with id :" + confimredBooking.getUuid().toString()
						+ " is booked find the driver details below\n" + confimredBooking.getDriver().toString();
			}
		}

		return message;
	}
}
