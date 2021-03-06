package com.offershopper.vendorprofileservice.feignproxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.offershopper.vendorprofileservice.model.CouponAndFeedbackBean;

@FeignClient(name="feedback-database-service")
public interface CouponAndFeedbackProxy {

  @GetMapping("/os/getfeedback/{offerId}")
  public ResponseEntity<List<CouponAndFeedbackBean>> getFeedback(@PathVariable("offerId") String offerId);
}
