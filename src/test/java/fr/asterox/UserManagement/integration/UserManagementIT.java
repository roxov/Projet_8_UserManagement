package fr.asterox.UserManagement.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.asterox.UserManagement.bean.User;
import fr.asterox.UserManagement.bean.UserPreferences;
import fr.asterox.UserManagement.bean.UserReward;
import fr.asterox.UserManagement.controller.dto.AttractionDTO;
import fr.asterox.UserManagement.controller.dto.LocationDTO;
import fr.asterox.UserManagement.controller.dto.ProviderDTO;
import fr.asterox.UserManagement.controller.dto.VisitedLocationDTO;
import fr.asterox.UserManagement.service.UserManagementService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserManagementIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserManagementService userManagementService;

	@BeforeEach
	public void setUp() {
		userManagementService.clearMap();
	}

	@Test
	void givenAUser_whenAddUser_thenReturns200AndPut() throws Exception {
		User user2 = new User(UUID.fromString("444e4bf3-ee62-4a67-b7d7-b0dc06989c6e"), "josette", "111",
				"josette@tourGuide.com");
		mockMvc.perform(
				put("/addUser").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user2)))
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/getUser?userName={userName}", "josette")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(111));
	}

	@Test
	public void givenAUsername_whenGetUser_thenReturnOkAndUser() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/getUser?userName={userName}", "jo").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"));
	}

	@Test
	public void givenAUsername_whenGetUserId_thenReturnOkAndUserId() throws Exception {
		mockMvc.perform(
				MockMvcRequestBuilders.get("/getUserId?userName={userName}", "jo").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void givenAUsername_whenGetUserPreferences_thenReturnOkAndUserPreferences() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getUserPreferences?userName={userName}", "jo")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.ticketQuantity").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.numberOfChildren").value(0))
				.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"));
	}

	@Test
	public void givenAUsername_whenGetLastLocation_thenReturnOk() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getLastLocation?userName={userName}", "internalUser0")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void givenAUserAndAddVisitedLocation_whenGetVisitedLocations_thenReturnsAListContainingTheLocation()
			throws Exception {
		VisitedLocationDTO visitedLocation = new VisitedLocationDTO(
				UUID.fromString("329e4bf3-ee62-4a67-b7d7-b0dc06989c6e"), new LocationDTO(144, 155), new Date());
		mockMvc.perform(put("/addVisitedLocation?userName={userName}", "jo").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(visitedLocation))).andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/getVisitedLocations?userName={userName}", "jo")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.[3].location.latitude").value(144));
	}

	@Test
	public void givenAUsernameAndAddUserReward_whenGetReward_thenReturnAddItToTheUser() throws Exception {
		UserReward userReward = new UserReward(
				new VisitedLocationDTO(UUID.randomUUID(), new LocationDTO(14.4, 25.5), new Date()),
				new AttractionDTO("name", "city", "state", UUID.fromString("444e4bf3-ee62-4a67-b7d7-b0dc06989c6e")),
				12);
		mockMvc.perform(MockMvcRequestBuilders.put("/addReward?userName={userName}", "internalUser0")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userReward)))
				.andExpect(status().isOk());
		mockMvc.perform(MockMvcRequestBuilders.get("/getUserRewards?userName={userName}", "internalUser0")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].rewardPoints").value(12));
	}

	@Test
	public void given100Users_whenGetAllCurrentLocations_thenReturnOkAndUsersList() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/getAllCurrentLocations").accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(101));
	}

	@Test
	public void givenAUsernameAndNewTripDeals_whenSetTripDeals_thenReturn200AndNewTripDeals() throws Exception {
		List<ProviderDTO> providers = new ArrayList<>();
		ProviderDTO provider = new ProviderDTO(UUID.fromString("555e4bf3-ee62-4a67-b7d7-b0dc06989c6e"), "providerName",
				200);
		providers.add(provider);

		mockMvc.perform(MockMvcRequestBuilders.post("/setTripDeals?userName={userName}", "jo")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(providers)))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/getTripDeals?userName={userName}", "jo")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("providerName"));
	}

	@Test
	public void givenAUsernameAndNewUserPreferences_whenSetUserPreferences_thenReturn200AndNewUserPreferences()
			throws Exception {
		UserPreferences userPreferences = new UserPreferences(150, "EURO", 0, 250, 3, 4, 2, 2);

		mockMvc.perform(MockMvcRequestBuilders.post("/setUserPreferences?userName={userName}", "internalUser0")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userPreferences)))
				.andExpect(status().isOk());

		mockMvc.perform(MockMvcRequestBuilders.get("/getUserPreferences?userName={userName}", "internalUser0")
				.accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.ticketQuantity").value(4))
				.andExpect(MockMvcResultMatchers.jsonPath("$.numberOfChildren").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("EURO"));
	}

}
