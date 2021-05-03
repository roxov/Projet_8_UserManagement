package fr.asterox.UserManagement.integration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RequestPerformanceTest {

//	@Autowired
//	UserManagementService userManagementService;
//
//	@Autowired
//	LocationProxy locationProxy;
//
//	@Autowired
//	RewardsCentralProxy rewardsCentralProxy;

//	List<User> allUsers;
//
//	@BeforeEach
//	public void setUp() {
//		allUsers = new ArrayList<>();
//		allUsers = userManagementService.getAllUsers();
//	}
//
//	@Disabled
//	@Test
//	public void givenAHighVolumeTrackLocation_whenTrackLocation_thenReturnAnswerWithin15Minutes() throws Exception {
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (User user : allUsers) {
//			locationProxy.trackLocation(user.getUserName());
//		}
//		stopWatch.stop();
//		userManagementService.tracker.stopTracking();
//
//		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
//				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//	@Disabled
//	@Test
//	public void givenAHighVolumeCalculateRewards_whenCalculateRewards_thenReturnAnswerWithin20Minutes()
//			throws Exception {
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (User user : allUsers) {
//			rewardsCentralProxy.calculateRewards(user.getUserName());
//			Thread.sleep(15);
//		}
//		stopWatch.stop();
//		userManagementService.tracker.stopTracking();
//
//		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
//				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//	@Disabled
//	@Test
//	public void givenAHighVolumeTrackLocation_whenCalculateLocation_thenReturnAnswerWithin15Minutes() throws Exception {
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (User user : allUsers) {
//			locationProxy.testCalculateLocation(user.getUserName());
//		}
//		stopWatch.stop();
//		userManagementService.tracker.stopTracking();
//
//		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users): Time Elapsed: "
//				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//	@Disabled
//	@Test
//	public void givenAHighVolumeOfUsers_whenDoCalculationOfRewards_thenReturnAnswerWithin20Minutes() throws Exception {
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (User user : allUsers) {
//			rewardsCentralProxy.testCalculateRewards(user.getUserName());
//		}
//		stopWatch.stop();
//		userManagementService.tracker.stopTracking();
//
//		System.out.println("For highVolumeTrackLocation (" + allUsers.size() + "users):Time Elapsed: "
//				+ TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}

}
