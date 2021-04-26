package fr.asterox.UserManagement.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import fr.asterox.UserManagement.controller.dto.CurrentLocationDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.proxy.LocationProxy;
import fr.asterox.UserManagement.tracker.Tracker;

@Service
public class UserManagementService implements IUserManagementService {
	@Autowired
	LocationProxy locationProxy;

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
			logger.debug("creating user with following username :" + user.getUserName());
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
	public Optional<LocationDTO> getUserLastLocation(String username) {
		logger.debug("getting last location of user :" + username);
		if (getUser(username).getVisitedLocations().isEmpty()) {
			logger.info("the calculation of last location is on process for user :" + username);
			locationProxy.trackLocation(username);
			return Optional.empty();
		}
		return getUser(username).getLastVisitedLocation().map(VisitedLocationDTO::getLocation);
	}

	@Override
	public List<CurrentLocationDTO> getAllCurrentLocations() {
		logger.debug("getting last location of all users");
		return this.getAllUsers().stream()
				.map(u -> new CurrentLocationDTO(u.getUserId(),
						u.getLastVisitedLocation().map(VisitedLocationDTO::getLocation).orElse(null)))
				.collect(Collectors.toList());
	}

	@Override
	public void setTripDeals(String userName, List<ProviderDTO> providers) {
		logger.debug("setting trip deals for user :" + userName);
		getUser(userName).setTripDeals(providers);
	}

	public List<ProviderDTO> getTripDeals(String userName) {
		logger.debug("getting trip deals for user :" + userName);
		return getUser(userName).getTripDeals();
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

	public LocationProxy setLocationProxy(LocationProxy locationProxy) {
		return this.locationProxy = locationProxy;
	}

	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	// Database connection will be used for external users, but for testing purposes
	// internal users are provided and stored in memory
	public final Map<String, User> internalUserMap = new HashMap<>();

	private void initializeInternalUsers() {

		User testUser = new User(UUID.fromString("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"), "jo", "000",
				"jo@tourGuide.com");
		generateUserLocationHistory(testUser);
		internalUserMap.put("jo", testUser);

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

	public void clearMap() {
		internalUserMap.clear();
		initializeInternalUsers();
	}

	public void generateUserLocationHistory(User user) {
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
