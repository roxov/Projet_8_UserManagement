package fr.asterox.UserManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import gpsUtil.GpsUtil;

@Configuration
public class UserManagementModule {

	@Bean
	public GpsUtil getGpsUtil() {
		return new GpsUtil();
	}

}
