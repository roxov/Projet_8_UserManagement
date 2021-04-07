package fr.asterox.UserManagement.controller.dto;

import java.util.UUID;

public class ProviderDTO {
	private UUID tripId;
	private String name;
	private double price;

	public ProviderDTO(UUID tripId, String name, double price) {
		super();
		this.tripId = tripId;
		this.name = name;
		this.price = price;
	}

	public UUID getTripId() {
		return tripId;
	}

	public void setTripId(UUID tripId) {
		this.tripId = tripId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
