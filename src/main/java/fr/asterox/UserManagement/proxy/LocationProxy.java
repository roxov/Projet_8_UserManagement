package fr.asterox.UserManagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Location", url = "localhost:9004")
public interface LocationProxy {

	@GetMapping("/trackLocation")
	public String trackLocation(@RequestParam String userName);

	@GetMapping("/testCalculateLocation")
	public void testCalculateLocation(@RequestParam String userName);
}
