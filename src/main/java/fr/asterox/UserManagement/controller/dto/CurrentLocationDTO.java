package fr.asterox.UserManagement.controller.dto;

import java.util.UUID;

public class CurrentLocationDTO {
	private UUID userId;
	private LocationDTO lastVisitedLocations;

	public CurrentLocationDTO(UUID userId, LocationDTO lastVisitedLocations) {
		super();
		this.userId = userId;
		this.lastVisitedLocations = lastVisitedLocations;
	}

	public UUID getUserId() {
		return userId;
	}

	public LocationDTO getLastVisitedLocations() {
		return lastVisitedLocations;
	}

}
