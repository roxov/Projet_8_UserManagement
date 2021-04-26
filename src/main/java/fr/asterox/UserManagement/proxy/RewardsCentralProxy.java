package fr.asterox.UserManagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RewardsCentral", url = "localhost:9003")
public interface RewardsCentralProxy {

	@PutMapping("/calculateRewards")
	public void calculateRewards(@RequestParam String userName);

	@PutMapping("/testCalculateRewards")
	public void testCalculateRewards(@RequestParam String userName);
}
