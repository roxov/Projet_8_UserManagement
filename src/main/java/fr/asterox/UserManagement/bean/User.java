package fr.asterox.UserManagement.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;

public class User {
	private UUID userId;

	public User() {
		super();
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setVisitedLocations(List<VisitedLocationDTO> visitedLocations) {
		this.visitedLocations = visitedLocations;
	}

	public void setUserRewards(List<UserReward> userRewards) {
		this.userRewards = userRewards;
	}

	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private List<VisitedLocationDTO> visitedLocations = new ArrayList<>();
	private List<UserReward> userRewards = new ArrayList<>();
	private UserPreferences userPreferences = new UserPreferences();
	private List<ProviderDTO> tripDeals = new ArrayList<>();

	public User(UUID userId, String userName, String phoneNumber, String emailAddress) {
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}

	public User(UUID userId, String userName, String phoneNumber, String emailAddress,
			List<VisitedLocationDTO> visitedLocations, List<UserReward> userRewards, UserPreferences userPreferences,
			List<ProviderDTO> tripDeals) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
		this.visitedLocations = visitedLocations;
		this.userRewards = userRewards;
		this.userPreferences = userPreferences;
		this.tripDeals = tripDeals;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void addToVisitedLocations(VisitedLocationDTO visitedLocationDTO) {
		visitedLocations.add(visitedLocationDTO);
	}

	public List<VisitedLocationDTO> getVisitedLocations() {
		return visitedLocations;
	}

	public void clearVisitedLocations() {
		visitedLocations.clear();
	}

	public void addUserReward(UserReward userReward) {
		if (userRewards.stream().filter(r -> !r.attraction.attractionName.equals(userReward.attraction.attractionName))
				.count() == 0) {
			userRewards.add(userReward);
		}
	}

	public List<UserReward> getUserRewards() {
		return userRewards;
	}

	public UserPreferences getUserPreferences() {
		return userPreferences;
	}

	public void setUserPreferences(UserPreferences userPreferences) {
		this.userPreferences = userPreferences;
	}

	public Optional<VisitedLocationDTO> getLastVisitedLocation() {
		if (visitedLocations.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(visitedLocations.get(visitedLocations.size() - 1));
	}

	public void setTripDeals(List<ProviderDTO> tripDeals) {
		this.tripDeals = tripDeals;
	}

	public List<ProviderDTO> getTripDeals() {
		return tripDeals;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((tripDeals == null) ? 0 : tripDeals.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		result = prime * result + ((userPreferences == null) ? 0 : userPreferences.hashCode());
		result = prime * result + ((userRewards == null) ? 0 : userRewards.hashCode());
		result = prime * result + ((visitedLocations == null) ? 0 : visitedLocations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (tripDeals == null) {
			if (other.tripDeals != null)
				return false;
		} else if (!tripDeals.equals(other.tripDeals))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userPreferences == null) {
			if (other.userPreferences != null)
				return false;
		} else if (!userPreferences.equals(other.userPreferences))
			return false;
		if (userRewards == null) {
			if (other.userRewards != null)
				return false;
		} else if (!userRewards.equals(other.userRewards))
			return false;
		if (visitedLocations == null) {
			if (other.visitedLocations != null)
				return false;
		} else if (!visitedLocations.equals(other.visitedLocations))
			return false;
		return true;
	}

}
