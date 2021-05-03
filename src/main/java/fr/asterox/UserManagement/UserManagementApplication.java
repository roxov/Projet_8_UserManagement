package fr.asterox.UserManagement;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("fr.asterox")
public class UserManagementApplication {
	private static final Logger LOGGER = LogManager.getLogger(UserManagementApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Initializing UserManagement");
		Locale.setDefault(Locale.US);
		SpringApplication.run(UserManagementApplication.class, args);
	}

}
