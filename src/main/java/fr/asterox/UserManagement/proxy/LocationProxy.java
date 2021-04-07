package fr.asterox.UserManagement.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.asterox.UserManagement.controller.dto.LocationDTO;

@FeignClient(name = "Location", url = "localhost:9004")
public interface LocationProxy {

	@RequestMapping("/trackLocation")
	public LocationDTO trackLocation(@RequestParam String userName);
}
