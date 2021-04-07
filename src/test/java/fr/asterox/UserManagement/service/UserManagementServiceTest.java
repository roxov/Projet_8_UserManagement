package fr.asterox.UserManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import TestConfiguration.UserConfiguration;
import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.LocationController;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {
	@Mock
	private LocationController locationController;

	UserManagementService userManagementService;
	User user;

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
		InternalTestHelper.setInternalUserNumber(0);
		userManagementService = new UserManagementService();

		// GIVEN
		user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		userManagementService.addUser(user);
	}

	@AfterEach
	public void closeTracker() {
		userManagementService.tracker.stopTracking();
	}

	@Test
	public void givenTwoUsers_whenAddUser_thenReturnRegisteredUsers() {
		// GIVEN
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		userManagementService.addUser(user2);

		// WHEN
		User retrivedUser = userManagementService.getUser(user.getUserName());
		User retrivedUser2 = userManagementService.getUser(user2.getUserName());

		// THEN
		assertEquals(user, retrivedUser);
		assertEquals(user2, retrivedUser2);
	}

	@Test
	public void givenAUser_whenGetUser_thenReturnTheUser() {
		// WHEN
		User result = userManagementService.getUser("jon");

		// THEN
		assertEquals(result, user);
	}

	@Test
	public void givenAUser_whenGetUserId_thenReturnTheUserId() {
		// WHEN
		UUID result = userManagementService.getUserId("jon");

		// THEN
		assertEquals(result, user.getUserId());
	}

	@Test
	public void givenAUser_whenGetUserPreferences_thenReturnTheUserPreferencesByDefault() {
		// WHEN
		UserPreferences result = userManagementService.getUserPreferences("jon");

		// THEN
		assertEquals(result.getAttractionProximity(), Integer.MAX_VALUE);
		assertEquals(result.getCurrency(), "USD");
		assertEquals(result.getNumberOfAdults(), 1);
	}

	@Test
	public void givenTwoUsers_whenGetAllUsers_thenReturnListOfTwoUsers() {
		// GIVEN
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		userManagementService.addUser(user2);

		// WHEN
		List<User> allUsers = userManagementService.getAllUsers();

		// THEN
		assertEquals(2, allUsers.size());
		assertTrue(allUsers.contains(user));
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void givenAVisitedLocationInTheListOfAUser_whenGetUserRewards_thenReturnAEmptyList() {
		// GIVEN
		GpsUtil gpsUtil = new GpsUtil();
		Attraction attraction = gpsUtil.getAttractions().get(0);
		userManagementService.addUserReward("jon",
				new UserReward(new VisitedLocation(user.getUserId(), attraction, new Date()), attraction));

		// WHEN
		List<UserReward> userRewards = userManagementService.getUserRewards(user);

		// THEN
		assertTrue(userRewards.size() == 1);
	}

	@Test
	public void givenAVisitedLocationInTheListOfAUser_whenAddReward_thenReturnAListOfOneReward() {
		// GIVEN
		GpsUtil gpsUtil = new GpsUtil();
		Attraction attraction = gpsUtil.getAttractions().get(0);

		// WHEN
		userManagementService.addUserReward("jon",
				new UserReward(new VisitedLocation(user.getUserId(), attraction, new Date()), attraction));

		// THEN
		assertTrue(user.getUserRewards().size() == 1);
	}

	@Test
	public void givenAUserWithLocationHistory_whenGetUserLastLocation_thenReturnLastLocationOfTheUser() {
		// GIVEN
		new UserConfiguration().generateUserLocationHistory(user);

		// WHEN
		LocationDTO location = userManagementService.getUserLastLocation(user);

		// THEN
		assertEquals(location, user.getLastVisitedLocation().location);
		verify(locationController, Mockito.times(0)).trackLocation(anyString());
	}

	@Test
	public void givenTwoUsers_whenGetAllCurrentLocations_thenReturnListOfIdAndLocations() {
		// GIVEN
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		new UserConfiguration().generateUserLocationHistory(user);
		new UserConfiguration().generateUserLocationHistory(user2);
		userManagementService.addUser(user2);

		// WHEN
		List<CurrentLocationDTO> currentLocations = userManagementService.getAllCurrentLocations();

		// THEN
		assertEquals(2, currentLocations.size());
		assertEquals(currentLocations.get(0).getUserId(), user.getUserId());
		assertEquals(currentLocations.get(1).getUserId(), user2.getUserId());
	}

	@Test
	public void givenAUser_whenSetTripDeals_thenReturnListOfIdAndLocations() {
		// GIVEN
		new UserConfiguration().generateUserLocationHistory(user);

		List<ProviderDTO> providers = List.of(new ProviderDTO(UUID.randomUUID(), "tripName", 15.5));

		// WHEN
		userManagementService.setTripDeals("jon", providers);

		// THEN
		assertEquals("tripName", user.getTripDeals().get(0).getName());
		assertEquals(15.5, user.getTripDeals().get(0).getPrice());
	}

	@Test
	public void givenAUser_whenSetUserPreferences_thenReturnListOfIdAndLocations() {
		// GIVEN
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		new UserConfiguration().generateUserLocationHistory(user);
		userManagementService.addUser(user);
		UserPreferences userPreferences = new UserPreferences(150, "EURO", 0, 250, 3, 4, 2, 2);

		// WHEN
		userManagementService.setPreferences("jon", userPreferences);
		userManagementService.tracker.stopTracking();

		// THEN
		assertEquals("EURO", user.getUserPreferences().getCurrency());
		assertEquals(250, user.getUserPreferences().getHighPricePoint());
		assertEquals(150, user.getUserPreferences().getAttractionProximity());
	}

}
