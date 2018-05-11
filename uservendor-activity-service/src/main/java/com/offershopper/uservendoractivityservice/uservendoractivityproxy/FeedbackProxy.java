package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.offershopper.uservendoractivityservice.model.FeedbackBean;
@FeignClient(name = "feedback-database-service", url = "10.151.60.93:9006")
@RibbonClient(name="feedback-database-service")
public interface FeedbackProxy {
  
  @GetMapping(value = "/os/getfeedback/vendorId/{vendorId}")
  public ResponseEntity<List<FeedbackBean>> getFeedbackByVendorId(@PathVariable (value="vendorId") String vendorId);

}
