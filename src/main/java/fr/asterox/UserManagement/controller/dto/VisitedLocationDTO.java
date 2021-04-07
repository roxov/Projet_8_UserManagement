package fr.asterox.UserManagement.controller.dto;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationDTO {
	public LocationDTO location;
	public Date timeVisited;
	public UUID userId;

	public VisitedLocationDTO() {
		super();
	}

	public VisitedLocationDTO(LocationDTO location, Date timeVisited, UUID userId) {
		super();
		this.location = location;
		this.timeVisited = timeVisited;
		this.userId = userId;
	}

	public VisitedLocationDTO(UUID userId, LocationDTO location, Date timeVisited) {
		super();
		this.userId = userId;
		this.location = location;
		this.timeVisited = timeVisited;
	}

	public LocationDTO getLocation() {
		return location;
	}

	public Date getTimeVisited() {
		return timeVisited;
	}

	public UUID getUserId() {
		return userId;
	}

}
