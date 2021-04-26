package fr.asterox.UserManagement.controller;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.UserManagement.proxy.LocationProxy;
import fr.asterox.UserManagement.proxy.RewardsCentralProxy;

@RestController
public class PerformancesTestController {

	@Autowired
	private LocationProxy locationProxy;

	@Autowired
	private RewardsCentralProxy rewardsCentralProxy;

	private Logger logger = LoggerFactory.getLogger(PerformancesTestController.class);

	@GetMapping("/trackLocation")
	public String trackLocation(@RequestParam @NotNull(message = "username is compulsory") String userName) {
		logger.debug("sending request to Location microservice to track user :" + userName);
		return locationProxy.trackLocation(userName);
	}

	@PutMapping("/calculateRewards")
	public void calculateRewards(@RequestParam @NotNull(message = "username is compulsory") String userName) {
		logger.debug("sending request to RewardsCentral microservice to track user :" + userName);
		rewardsCentralProxy.calculateRewards(userName);
	}

	@GetMapping("/testCalculateLocation")
	public void testCalculateLocation(@RequestParam @NotNull(message = "username is compulsory") String userName) {
		logger.debug("sending request to Location microservice for performance tests");
		locationProxy.testCalculateLocation(userName);
	}

	@PutMapping("/testCalculateRewards")
	public void testCalculateRewards(@RequestParam @NotNull(message = "username is compulsory") String userName) {
		logger.debug("sending request to RewardsCentral microservice for performance tests");
		rewardsCentralProxy.testCalculateRewards(userName);
	}
}
