package com.smart.cab;
import java.util.ArrayList;
import java.util.List;

import com.smart.entity.Driver;

public class Bookings {
	
	public List<Booking> availablebookings = null;
	public List<Driver> availableDrivers =null;

	private static Bookings singletonObject = null;

	private Bookings(){
		availablebookings = new ArrayList<Booking>();
		availableDrivers = new ArrayList<Driver>();
	}
	
	public List<Driver> getAvailableDrivers() {
		return availableDrivers;
	}

	public void setAvailableDrivers(List<Driver> availableDrivers) {
		this.availableDrivers = availableDrivers;
	}

	public static Bookings getSingletonInstance()
    {
        if (singletonObject == null){
        	singletonObject = new Bookings();
        }
        return singletonObject;
    }
	
	public List<Booking> getBookings() {
		return availablebookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.availablebookings = bookings;
	}

}
