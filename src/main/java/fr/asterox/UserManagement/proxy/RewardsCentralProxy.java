package fr.asterox.UserManagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "RewardsCentral", url = "localhost:9003")
public interface RewardsCentralProxy {

	@RequestMapping("/calculateRewards")
	public void calculateRewards(@RequestParam String userName);
}
