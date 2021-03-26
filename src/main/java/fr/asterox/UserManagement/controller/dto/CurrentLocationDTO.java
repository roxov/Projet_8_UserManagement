package fr.asterox.UserManagement.controller.dto;

import java.util.UUID;

import gpsUtil.location.Location;

public class CurrentLocationDTO {
	private UUID userId;
	private Location lastVisitedLocations;

	public CurrentLocationDTO(UUID userId, Location lastVisitedLocations) {
		super();
		this.userId = userId;
		this.lastVisitedLocations = lastVisitedLocations;
	}

	public UUID getUserId() {
		return userId;
	}

	public Location getLastVisitedLocations() {
		return lastVisitedLocations;
	}

}
