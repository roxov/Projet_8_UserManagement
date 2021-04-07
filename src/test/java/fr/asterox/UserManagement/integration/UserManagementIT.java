package fr.asterox.UserManagement.integration;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.service.UserManagementService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserManagementIT {
	@LocalServerPort
	private Integer port;

	private String baseUrl;

	UserManagementService userManagementService;
	User user;

	@BeforeEach
	public void setUp() {
		baseUrl = "http://localhost:" + port;
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

//	@Test
//	public void givenAUserWithNoLocationHistory_whenGetUserLastLocation_thenReturnTrackLocation() {
//		// GIVEN
//		Location location = new Location(145.4, 185.6);
//
//		// WHEN
//		userManagementService.getUserLastLocation(user);
//
//		// THEN
//		assertEquals(location, user.getLastVisitedLocation().location);
//	}

}
