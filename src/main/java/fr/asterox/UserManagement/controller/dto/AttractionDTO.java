package fr.asterox.UserManagement.controller.dto;

import java.util.UUID;

public class AttractionDTO {
	public String attractionName;
	public String city;
	public String state;
	public UUID attractionId;

	public AttractionDTO() {
		super();
	}

	public AttractionDTO(String attractionName, String city, String state, UUID attractionId) {
		super();
		this.attractionName = attractionName;
		this.city = city;
		this.state = state;
		this.attractionId = attractionId;
	}

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public UUID getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(UUID attractionId) {
		this.attractionId = attractionId;
	}

}
