package fr.asterox.UserManagement.bean;

import fr.asterox.UserManagement.controller.dto.AttractionDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;

public class UserReward {

	public VisitedLocationDTO visitedLocation;
	public AttractionDTO attraction;
	private int rewardPoints;

	public UserReward() {
		super();
	}

	public UserReward(VisitedLocationDTO visitedLocation, AttractionDTO attraction, int rewardPoints) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}

	public UserReward(VisitedLocationDTO visitedLocation, AttractionDTO attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public int getRewardPoints() {
		return rewardPoints;
	}

	public VisitedLocationDTO getVisitedLocation() {
		return visitedLocation;
	}

	public void setVisitedLocation(VisitedLocationDTO visitedLocation) {
		this.visitedLocation = visitedLocation;
	}

	public void setAttraction(AttractionDTO attraction) {
		this.attraction = attraction;
	}

	public AttractionDTO getAttraction() {
		return attraction;
	}

}
