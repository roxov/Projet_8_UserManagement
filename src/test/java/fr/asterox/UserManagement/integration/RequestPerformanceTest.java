package fr.asterox.UserManagement.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.proxy.LocationProxy;
import fr.asterox.UserManagement.proxy.RewardsCentralProxy;
import fr.asterox.UserManagement.service.UserManagementService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RequestPerformanceTest {

	@Autowired
	UserManagementService userManagementService;

	@Autowired
	LocationProxy locationProxy;

	@Autowired
	RewardsCentralProxy rewardsCentralProxy;

	List<User> allUsers;

	@BeforeEach
	public void setUp() {
		allUsers = new ArrayList<>();
		allUsers = userManagementService.getAllUsers();
	}

	@Test
	public void givenAHighVolumeTrackLocation_whenTrackLocation_thenReturnAnswerWithin15Minutes() throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			locationProxy.trackLocation(user.getUserName());
		}
		stopWatch.stop();
		userManagementService.tracker.stopTracking();

		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	public void givenAHighVolumeCalculateRewards_whenCalculateRewards_thenReturnAnswerWithin20Minutes()
			throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			rewardsCentralProxy.calculateRewards(user.getUserName());
		}
		stopWatch.stop();
		userManagementService.tracker.stopTracking();

		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	public void givenAHighVolumeTrackLocation_whenCalculateLocation_thenReturnAnswerWithin15Minutes() throws Exception {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			locationProxy.testCalculateLocation(user.getUserName());
		}
		stopWatch.stop();
		userManagementService.tracker.stopTracking();

		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

	@Test
	public void givenAHighVolumeOfUsers_whenDoCalculationOfRewards_thenReturnAnswerWithin20Minutes() throws Exception {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			rewardsCentralProxy.testCalculateRewards(user.getUserName());
		}
		stopWatch.stop();
		userManagementService.tracker.stopTracking();

		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users):Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}
