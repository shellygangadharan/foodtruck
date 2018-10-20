package com.foodtruck.demo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="Locations")
public class Location {

	Float longitude;
	Float latitude;

	public Location(Float longitude , Float latitude) {
		this.longitude = longitude;
		this.latitude = latitude;
		
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	
}
