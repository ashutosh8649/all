package com.offershopper.uaa.feignproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uaa.model.RegisterInfo;

@FeignClient(value = "com.offershopper.uaa.feignproxy", url = "http://10.151.61.152:9000")
@RibbonClient(name = "uservendor-activity-service")
public interface UserVendorActivity {

	@PostMapping("/updatepassword")
	public String resetPassword(@RequestBody RegisterInfo obj ) ;
}
