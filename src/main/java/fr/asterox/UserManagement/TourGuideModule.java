package fr.asterox.UserManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.asterox.UserManagement.service.RewardsService;
import gpsUtil.GpsUtil;
import rewardCentral.RewardCentral;

@Configuration
public class TourGuideModule {

	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}

	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

}
