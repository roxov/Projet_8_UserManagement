package fr.asterox.UserManagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.proxy.LocationProxy;

@RestController
public class LocationController {

	@Autowired
	private LocationProxy locationProxy;

	private Logger logger = LoggerFactory.getLogger(LocationController.class);

	@RequestMapping("/trackLocation")
	public LocationDTO trackLocation(@RequestParam String userName) {
		logger.debug("sending request to Location microservice to track user location of user :" + userName);
		return locationProxy.trackLocation(userName);
	}
}
