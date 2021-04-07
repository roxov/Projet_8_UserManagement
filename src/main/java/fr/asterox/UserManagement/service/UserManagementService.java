package fr.asterox.UserManagement.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.LocationController;
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.tracker.Tracker;

@Service
public class UserManagementService implements IUserManagementService {
	@Autowired
	LocationController locationController;

	private Logger logger = LoggerFactory.getLogger(UserManagementService.class);
	public final Tracker tracker;
	boolean testMode = true;

	public UserManagementService() {

		if (testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}

	@Override
	public void addUser(User user) {
		logger.debug("adding new user");
		if (!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
	}

	@Override
	public User getUser(String userName) {
		logger.debug("getting user with following username :" + userName);
		return internalUserMap.get(userName);
	}

	@Override
	public UUID getUserId(String userName) {
		logger.debug("getting userId for user :" + userName);
		return internalUserMap.get(userName).getUserId();
	}

	@Override
	public UserPreferences getUserPreferences(String userName) {
		logger.debug("getting preferences of user :" + userName);
		return internalUserMap.get(userName).getUserPreferences();
	}

	@Override
	public List<User> getAllUsers() {
		logger.debug("getting all users");
		return internalUserMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public List<UserReward> getUserRewards(User user) {
		logger.debug("getting list of rewards for user :" + user.getUserName());
		return user.getUserRewards();
	}

	@Override
	public void addUserReward(String userName, UserReward userReward) {
		logger.debug("adding rewards to user :" + userName);
		getUser(userName).addUserReward(userReward);
	}

	@Override
	public LocationDTO getUserLastLocation(User user) {
		logger.debug("getting last location of user :" + user.getUserName());
		return (user.getVisitedLocations() == null) ? locationController.trackLocation(user.getUserName())
				: user.getLastVisitedLocation().location;

		// .size() > 0
	}

	@Override
	public List<CurrentLocationDTO> getAllCurrentLocations() {
		logger.debug("getting last location of all users");
		return this.getAllUsers().stream()
				.map(u -> new CurrentLocationDTO(u.getUserId(), u.getLastVisitedLocation().location))
				.collect(Collectors.toList());
	}

	@Override
	public void setTripDeals(String userName, List<ProviderDTO> providers) {
		logger.debug("setting trip deals for user :" + userName);
		getUser(userName).setTripDeals(providers);
	}

	@Override
	public void setPreferences(String userName, UserPreferences userPreferences) {
		logger.debug("setting user preferences for user :" + userName);
		getUser(userName).setUserPreferences(userPreferences);
	}

	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				tracker.stopTracking();
			}
		});
	}

	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	// Database connection will be used for external users, but for testing purposes
	// internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();

	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
			generateUserLocationHistory(user);

			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}

	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i -> {
			user.addToVisitedLocations(new VisitedLocationDTO(user.getUserId(),
					new LocationDTO(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}

	private double generateRandomLongitude() {
		double leftLimit = -180;
		double rightLimit = 180;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
		double rightLimit = 85.05112878;
		return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}

	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
		return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}

}
