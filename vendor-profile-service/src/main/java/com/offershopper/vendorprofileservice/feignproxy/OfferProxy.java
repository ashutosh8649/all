package com.offershopper.vendorprofileservice.feignproxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.offershopper.vendorprofileservice.model.OfferBean;
@FeignClient(name="offer-database-service")
public interface OfferProxy {
  
  @GetMapping("/vendor/{userId}/offers")
  public ResponseEntity<List<OfferBean>> getOffers(@PathVariable("userId") String userId); 
  
  

}
