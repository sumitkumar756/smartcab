package com.smart.cab;
import java.util.ArrayList;
import java.util.List;

public class Bookings {
	
	public List<Booking> bookings = null;

	private static Bookings singletonObject = null;

	private Bookings(){
		bookings = new ArrayList<Booking>();
	}
	
	public static Bookings getSingletonInstance()
    {
        if (singletonObject == null){
        	singletonObject = new Bookings();
        }
        return singletonObject;
    }
	
	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

}
