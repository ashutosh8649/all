package com.offershopper.uaa.feignproxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uaa.model.RegisterInfo;

import feign.Headers;

@FeignClient(value = "com.offershopper.uaa.feignproxy", url = "http://10.151.61.152:9000")
@RibbonClient(name = "registration-service")
public interface RegisterProxyRepo {

	@PostMapping("/register")
	@Headers("Content-Type: application/json")
	public ResponseEntity<String> addUserInformation(@RequestBody RegisterInfo dataOfUserInBody);

	@GetMapping("/all")
	public ResponseEntity<List<RegisterInfo>> allUsers();

	@PostMapping("/register/user")
	public ResponseEntity<String> addUser(@RequestBody RegisterInfo dataOfUserInBody);

}
