package fr.asterox.UserManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.asterox.UserManagement.proxy.RewardsCentralProxy;

@RestController
public class RewardsCentralController {
	@Autowired
	RewardsCentralProxy rewardsCentralProxy;

	@RequestMapping("/calculateRewards")
	public void calculateRewards(@RequestParam String userName) {
		rewardsCentralProxy.calculateRewards(userName);
	}

}
