package com.gps;

import com.smart.entity.Person;

public class GPS {

	private final double earthRadius = 6371; // in km
	
	public double getdistanceBetweenTwoGPS(Person cabDriver, Person traveler) {
		Double latDistance = toRad(traveler.getLatitude() - cabDriver.getLatitude());
		Double lonDistance = toRad(traveler.getLongitude() - cabDriver.getLongitude());
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(toRad(cabDriver.getLatitude()))
				* Math.cos(toRad(traveler.getLatitude())) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		Double distance = earthRadius * c;

		return distance;

	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}
}
