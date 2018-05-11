package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uservendoractivityservice.model.SubscribeBean;
/*
 * File name:SubscribeProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with subscribe-database-service and provides the functions to add offes  , retrieve offers details or delete offers from subscribed database.
 */
@FeignClient("subscribe-database-service")
@RibbonClient(name="subscribe-database-service")
public interface SubscribeProxy 
{
  @DeleteMapping("/subscribe/del/byvendorid/{userId}/{vendorId}")
  public ResponseEntity<HttpStatus> deleteByVendorId(@PathVariable("userId") String userId, @PathVariable("vendorId") String vendorId);

  @GetMapping(value = "/subscribe/getUser/{userId}")
  public ResponseEntity<List<SubscribeBean>> getByUserId(@PathVariable("userId") String userId); 

  @PostMapping(value = "/subscribe/add/byvendorid")
  public ResponseEntity<HttpStatus> addByVendorId(@RequestBody SubscribeBean subscribeBean);

}
