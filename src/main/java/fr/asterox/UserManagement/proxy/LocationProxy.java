package fr.asterox.UserManagement.proxy;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Location", url = "localhost:9004")
public interface LocationProxy {

	@GetMapping("/trackLocation")
	public String trackLocation(@RequestParam @NotNull(message = "username is compulsory") String userName);

	@GetMapping("/testCalculateLocation")
	public void testCalculateLocation(@RequestParam String userName);
}
