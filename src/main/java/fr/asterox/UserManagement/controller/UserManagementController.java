package fr.asterox.UserManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jsoniter.output.JsonStream;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.service.TourGuideService;
import gpsUtil.location.VisitedLocation;

@RestController
public class UserManagementController {

	@Autowired
	TourGuideService tourGuideService;

	@RequestMapping("/getLastLocation")
	public String getLastLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService.getUserLastLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
	}

	@RequestMapping("/getLocation")
	public String getLocation(@RequestParam String userName) {
		VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return JsonStream.serialize(visitedLocation.location);
	}

	@RequestMapping("/getRewards")
	public String getRewards(@RequestParam String userName) {
		return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
	}

	@RequestMapping("/getUser")
	private User getUser(@RequestParam String userName) {
		return tourGuideService.getUser(userName);
	}

	@RequestMapping("/getUserPreferences")
	private UserPreferences getUserPreferences(@RequestParam String userName) {
		return tourGuideService.getUserPreferences(userName);
	}
}
