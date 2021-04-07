package fr.asterox.UserManagement.service;

import java.util.List;
import java.util.UUID;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;

/**
 * 
 * Microservice containing data relatives to users.
 *
 */
public interface IUserManagementService {
	public User getUser(String userName);

	public UUID getUserId(String userName);

	public UserPreferences getUserPreferences(String userName);

	public List<User> getAllUsers();

	public void addUser(User user);

	public List<UserReward> getUserRewards(User user);

	public void addUserReward(String userName, UserReward userReward);

	public LocationDTO getUserLastLocation(User user);

	public List<CurrentLocationDTO> getAllCurrentLocations();

	public void setTripDeals(String userName, List<ProviderDTO> providers);

	public void setPreferences(String userName, UserPreferences userPreferences);
}
