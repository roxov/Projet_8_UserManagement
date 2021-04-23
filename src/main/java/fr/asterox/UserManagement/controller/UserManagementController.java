package fr.asterox.UserManagement.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;
import fr.asterox.UserManagement.service.UserManagementService;

@RestController
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;

	private Logger logger = LoggerFactory.getLogger(UserManagementController.class);

	@PutMapping("/addUser")
	public void addUser(@RequestBody User user) {
		logger.debug("adding user :" + user);
		userManagementService.addUser(user);
	}

	@GetMapping("/getUser")
	public User getUser(@RequestParam String userName) {
		logger.debug("getting user with username :" + userName);
		return userManagementService.getUser(userName);
	}

	@GetMapping("/getUserId")
	public UUID getUserId(@RequestParam String userName) {
		logger.debug("getting userId of user :" + userName);
		return userManagementService.getUserId(userName);
	}

	@GetMapping("/getUserPreferences")
	public UserPreferences getUserPreferences(@RequestParam String userName) {
		logger.debug("getting user preferences of user :" + userName);
		return userManagementService.getUserPreferences(userName);
	}

	@GetMapping("/getLastLocation")
	public LocationDTO getLastLocation(@RequestParam String userName) {
		logger.debug("getting last location of user :" + userName);
		return userManagementService.getUserLastLocation(getUser(userName));
	}

	@GetMapping("/getVisitedLocations")
	public List<VisitedLocationDTO> getVisitedLocations(@RequestParam String userName) {
		logger.debug("getting the list of locations visited by user :" + userName);
		User user = userManagementService.getUser(userName);
		return user.getVisitedLocations();
	}

	@PutMapping("/addVisitedLocation")
	public void addVisitedLocation(@RequestParam String userName, @RequestBody VisitedLocationDTO visitedLocationDTO) {
		logger.debug("adding visited location to user :" + userName);
		getUser(userName).addToVisitedLocations(visitedLocationDTO);
	}

	@GetMapping("/getUserRewards")
	public List<UserReward> getUserRewards(@RequestParam String userName) {
		logger.debug("getting user rewards of user :" + userName);
		return userManagementService.getUserRewards(getUser(userName));
	}

	@PutMapping("/addReward")
	public void addReward(@RequestParam String userName, @RequestBody UserReward userReward) {
		logger.debug("adding rewards to user :" + userName);
		userManagementService.addUserReward(userName, userReward);
	}

	@GetMapping("/getAllCurrentLocations")
	public List<CurrentLocationDTO> getAllCurrentLocations() {
		logger.debug("getting last location of history of all users");
		List<CurrentLocationDTO> currentLocations = userManagementService.getAllCurrentLocations();
		return currentLocations;
	}

	@PostMapping("/setTripDeals")
	public void setTripDeals(@RequestParam String userName, @RequestBody List<ProviderDTO> providers) {
		logger.debug("setting trip deals of user :" + userName);
		userManagementService.setTripDeals(userName, providers);
	}

	@GetMapping("/getTripDeals")
	public void getTripDeals(@RequestParam String userName) {
		logger.debug("getting trip deals of user :" + userName);
		userManagementService.getTripDeals(userName);
	}

	@PostMapping("/setUserPreferences")
	public void setUserPreferences(@RequestParam String userName, @RequestBody UserPreferences userPreferences) {
		logger.debug("setting user preferences of user :" + userName);
		userManagementService.setPreferences(userName, userPreferences);
	}

}
