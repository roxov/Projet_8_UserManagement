package fr.asterox.UserManagement.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.helper.InternalTestHelper;
import fr.asterox.UserManagement.proxy.LocationProxy;
import fr.asterox.UserManagement.service.UserManagementService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPerformance {
	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private LocationProxy locationProxy;
	/*
	 * A note on performance improvements:
	 * 
	 * The number of users generated for the high volume tests can be easily
	 * adjusted via this method:
	 * 
	 * InternalTestHelper.setInternalUserNumber(100000);
	 * 
	 * 
	 * These tests can be modified to suit new solutions, just as long as the
	 * performance metrics at the end of the tests remains consistent.
	 * 
	 * These are performance metrics that we are trying to hit:
	 * 
	 * highVolumeTrackLocation: 100,000 users within 15 minutes:
	 * assertTrue(TimeUnit.MINUTES.toSeconds(15) >=
	 * TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 *
	 */

	@BeforeEach
	public void setUp() {
		Locale.setDefault(Locale.US);
		InternalTestHelper.setInternalUserNumber(1);
	}

	@Test
	public void givenAHighVolumeTrackLocation_whenTrackLocation_thenReturn15MinutesAnswer() {
		List<User> allUsers = new ArrayList<>();
		allUsers = userManagementService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		for (User user : allUsers) {
			locationProxy.trackLocation(user.getUserName());
		}
		stopWatch.stop();
		userManagementService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: "
				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}

}
