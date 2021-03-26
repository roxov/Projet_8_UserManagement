package fr.asterox.UserManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TestConfiguration.UserConfiguration;
import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.service.UserManagementService;
import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;

public class UserManagementServiceTest {
	UserManagementService userManagementService;

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
		GpsUtil gpsUtil = new GpsUtil();
		InternalTestHelper.setInternalUserNumber(0);
		userManagementService = new UserManagementService(gpsUtil);
	}

	@Test
	public void givenAUser_whenGetUser_thenReturnTheUser() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		userManagementService.addUser(user);

		// WHEN
		User result = userManagementService.getUser("jon");
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(result, user);
	}

	@Test
	public void givenTwoUsers_whenGetAllUsers_thenReturnListOfTwoUsers() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		userManagementService.addUser(user);
		userManagementService.addUser(user2);

		// WHEN
		List<User> allUsers = userManagementService.getAllUsers();
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(2, allUsers.size());
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void givenTwoUsers_whenAddUser_thenReturnRegisteredUsers() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

		userManagementService.addUser(user);
		userManagementService.addUser(user2);

		// WHEN
		User retrivedUser = userManagementService.getUser(user.getUserName());
		User retrivedUser2 = userManagementService.getUser(user2.getUserName());

		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}

//	TODO : à vérifier
	// @Test
//	public void userGetRewards() {
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//
//		InternalTestHelper.setInternalUserNumber(0);
//		UserManagementService tourGuideService = new UserManagementService(gpsUtil, rewardsService);
//
//		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
//		Attraction attraction = gpsUtil.getAttractions().get(0);
//		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction, new Date()));
//		tourGuideService.trackUserLocation(user);
//		List<UserReward> userRewards = user.getUserRewards();
//		tourGuideService.tracker.stopTracking();
//		assertTrue(userRewards.size() == 1);
//	}

	@Test
	public void givenAUser_whenGetUserLastLocation_thenReturnLastLocationOfTheUser() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		new UserConfiguration().generateUserLocationHistory(user);

		// WHEN
		VisitedLocation visitedLocation = userManagementService.getUserLastLocation(user);
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(visitedLocation, user.getLastVisitedLocation());
	}

	@Test
	public void givenAUser_whenTrackUserLocation_thenReturnUserIdOnVisitedLocation() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		// WHEN
		VisitedLocation visitedLocation = userManagementService.trackUserLocation(user);
		userManagementService.tracker.stopTracking();

		// THEN
		assertTrue(visitedLocation.userId.equals(user.getUserId()));
	}

	@Test
	public void givenAUser_whenGetUserRewards_thenReturnListOfOneUserReward() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

		// WHEN
		List<UserReward> userReward = userManagementService.getUserRewards(user);
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(userReward, Collections.emptyList());
	}

	@Test
	public void givenTwoUsers_whenGetAllCurrentLocations_thenReturnListOfIdAndLocations() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		new UserConfiguration().generateUserLocationHistory(user);
		new UserConfiguration().generateUserLocationHistory(user2);

		userManagementService.addUser(user);
		userManagementService.addUser(user2);

		// WHEN
		List<CurrentLocationDTO> currentLocations = userManagementService.getAllCurrentLocations();
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals(2, currentLocations.size());
		assertEquals(currentLocations.get(0).getUserId(), user.getUserId());
		assertEquals(currentLocations.get(1).getUserId(), user2.getUserId());
	}

}
