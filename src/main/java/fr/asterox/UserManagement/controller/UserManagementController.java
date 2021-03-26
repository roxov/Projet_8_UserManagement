package fr.asterox.UserManagement.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.service.UserManagementService;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tripPricer.Provider;

@RestController
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;

	@RequestMapping("/getUser")
	public User getUser(@RequestParam String userName) {
		return userManagementService.getUser(userName);
	}

	@RequestMapping("/getUserId")
	public UUID getUserId(@RequestParam String userName) {
		return userManagementService.getUserId(userName);
	}

	@RequestMapping("/getUserPreferences")
	public UserPreferences getUserPreferences(@RequestParam String userName) {
		return userManagementService.getUserPreferences(userName);
	}

	@RequestMapping("/getLastLocation")
	public Location getLastLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = userManagementService.getUserLastLocation(getUser(userName));
		return visitedLocation.location;
	}

	@RequestMapping("/getVisitedLocations")
	public List<VisitedLocation> getVisitedLocations(@RequestParam String userName) {
		User user = userManagementService.getUser(userName);
		return user.getVisitedLocations();
	}

	// TODO : A déplacer dans Location
	@RequestMapping("/trackLocation")
	public Location getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = userManagementService.getUserLocation(getUser(userName));
		return visitedLocation.location;
	}

	@RequestMapping("/getUserRewards")
	public List<UserReward> getUserRewards(@RequestParam String userName) {
		return userManagementService.getUserRewards(getUser(userName));
	}

	@RequestMapping("/addRewards")
	public void addUserReward(@RequestParam String userName, @RequestBody UserReward userReward) {
		userManagementService.addUserReward(userName, userReward);
	}

	@RequestMapping("/getAllCurrentLocations")
	public List<CurrentLocationDTO> getAllCurrentLocations() {
		List<CurrentLocationDTO> currentLocations = userManagementService.getAllCurrentLocations();
		return currentLocations;
	}

	@RequestMapping("/setTripDeals")
	public void setTripDeals(@RequestParam String userName, @RequestBody List<Provider> providers) {
		userManagementService.setTripDeals(userName, providers);
	}

}
