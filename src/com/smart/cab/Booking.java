package com.smart.cab;

import java.util.UUID;

import com.smart.entity.Driver;
import com.smart.entity.Traveler;

public class Booking {
	
	private Traveler traveler = null;
	private UUID uuid = UUID.randomUUID();
	private Driver driver = null;
	private boolean isConfirmed = false;
	@Override
	public String toString() {
		return "Booking [traveler=" + traveler + ", uuid=" + uuid.toString() + ", driver=" + driver + ", isConfirmed="
				+ isConfirmed + ", fare=" + fare + "]";
	}
	private double fare = 0.00;
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public Traveler getTraveler() {
		return traveler;
	}
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	public Driver getDriver() {
		return driver;
	}
	public void setDriver(Driver driver) {
		this.driver = driver;
	}
	public boolean isConfirmed() {
		return isConfirmed;
	}
	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	
}
