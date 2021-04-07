package fr.asterox.UserManagement.controller.dto;

public class LocationDTO {
	public double latitude;
	public double longitude;

	public LocationDTO(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

}
