package fr.asterox.UserManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.AttractionDTO;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.proxy.LocationProxy;

/**
 * 
 * Tests are realized with a testUser, added in the internal map.
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {
	@Mock
	private LocationProxy locationProxy;

	@InjectMocks
	UserManagementService userManagementService;

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
		InternalTestHelper.setInternalUserNumber(0);
		userManagementService = new UserManagementService();
	}

	@AfterEach
	public void closeTracker() {
		userManagementService.tracker.stopTracking();
	}

	@Test
	public void givenAUser_whenAddUser_thenReturnRegisteredUsers() {
		// GIVEN
		User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");
		userManagementService.addUser(user2);

		// WHEN
		User retrivedUser2 = userManagementService.getUser(user2.getUserName());

		// THEN
		assertEquals(user2, retrivedUser2);
	}

	@Test
	public void givenAUser_whenGetUser_thenReturnTheUser() {
		// WHEN
		User result = userManagementService.getUser("jo");

		// THEN
		assertEquals(result.getEmailAddress(), "jo@tourGuide.com");
	}

	@Test
	public void givenAUser_whenGetUserId_thenReturnTheUserId() {
		// WHEN
		UUID result = userManagementService.getUserId("jo");

		// THEN
		assertEquals(result, UUID.fromString("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"));
	}

	@Test
	public void givenAUser_whenGetUserPreferences_thenReturnTheUserPreferencesByDefault() {
		// WHEN
		UserPreferences result = userManagementService.getUserPreferences("jo");

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
		assertTrue(allUsers.contains(user2));
	}

	@Test
	public void givenANewVisitedLocationInTheListOfAUser_whenGetUserRewards_thenReturnAEmptyList() {
		// GIVEN
		AttractionDTO attraction = new AttractionDTO("attractionName", "city", "state",
				UUID.fromString("333e4bf3-ee62-4a67-b7d7-b0dc06989c6e"));
		userManagementService.addUserReward("jo",
				new UserReward(new VisitedLocationDTO(UUID.fromString("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"),
						new LocationDTO(144, 255), new Date()), attraction));

		// WHEN
		List<UserReward> userRewards = userManagementService.getUserRewards(userManagementService.getUser("jo"));

		// THEN
		assertTrue(userRewards.size() == 1);
	}

	@Test
	public void givenAUserWithLocationHistory_whenGetUserLastLocation_thenReturnLastLocationOfTheUser() {
		// WHEN
		userManagementService.getUserLastLocation("jo");

		// THEN
		verify(locationProxy, Mockito.times(0)).trackLocation(anyString());
	}

	@Test
	public void givenAUserWithNoLocationHistory_whenGetUserLastLocation_thenReturnCallToLocationController() {
		// GIVEN
		User user2 = new User(UUID.randomUUID(), "josette", "000", "josette@tourGuide.com");
		userManagementService.addUser(user2);
		when(locationProxy.trackLocation("josette")).thenReturn("calculation on process");
		userManagementService.setLocationProxy(locationProxy);

		// WHEN
		Optional<LocationDTO> result = userManagementService.getUserLastLocation("josette");

		// THEN
		verify(locationProxy, Mockito.times(1)).trackLocation(anyString());
		assertEquals(result, Optional.empty());
	}

	@Test
	public void givenTwoUsers_whenGetAllCurrentLocations_thenReturnListOfIdAndLocations() {
		// GIVEN
		User user2 = new User(UUID.fromString("444e4bf3-ee62-4a67-b7d7-b0dc06989c6e"), "jon2", "000",
				"jon2@tourGuide.com");
		userManagementService.generateUserLocationHistory(user2);
		userManagementService.addUser(user2);

		// WHEN
		List<CurrentLocationDTO> currentLocations = userManagementService.getAllCurrentLocations();

		// THEN
		assertEquals(2, currentLocations.size());
		assertEquals(currentLocations.get(0).getUserId(), UUID.fromString("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"));
		assertEquals(currentLocations.get(1).getUserId(), UUID.fromString("444e4bf3-ee62-4a67-b7d7-b0dc06989c6e"));
	}

	@Test
	public void givenAUser_whenSetTripDeals_thenReturnListOfIdAndLocations() {
		// GIVEN
		List<ProviderDTO> providers = List.of(new ProviderDTO(UUID.randomUUID(), "tripName", 15.5));

		// WHEN
		userManagementService.setTripDeals("jo", providers);

		// THEN
		assertEquals("tripName", userManagementService.getUser("jo").getTripDeals().get(0).getName());
		assertEquals(15.5, userManagementService.getUser("jo").getTripDeals().get(0).getPrice());
	}

	@Test
	public void givenAUser_whenSetUserPreferences_thenReturnNewPreferences() {
		// GIVEN
		UserPreferences userPreferences = new UserPreferences(150, "EURO", 0, 250, 3, 4, 2, 2);

		// WHEN
		userManagementService.setPreferences("jo", userPreferences);

		// THEN
		assertEquals("EURO", userManagementService.getUser("jo").getUserPreferences().getCurrency());
		assertEquals(250, userManagementService.getUser("jo").getUserPreferences().getHighPricePoint());
		assertEquals(150, userManagementService.getUser("jo").getUserPreferences().getAttractionProximity());
		assertEquals(2, userManagementService.getUser("jo").getUserPreferences().getNumberOfChildren());
	}

}
