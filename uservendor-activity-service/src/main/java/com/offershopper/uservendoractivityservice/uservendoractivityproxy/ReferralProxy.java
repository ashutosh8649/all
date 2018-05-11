package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
/*
 * File name:ReferralProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with referral-database-service and provides the functions to update user data on the basis of referance given by them to another user.
 */
@FeignClient("referral-database-service")
@RibbonClient(name="referral-database-service")
public interface  ReferralProxy 
{
  @PutMapping("/spin/{userId1}/{userId2}")
  public ResponseEntity<Object> addSpin(@PathVariable ("userId1") String userId1,@PathVariable ("userId2") String userId2);
}
