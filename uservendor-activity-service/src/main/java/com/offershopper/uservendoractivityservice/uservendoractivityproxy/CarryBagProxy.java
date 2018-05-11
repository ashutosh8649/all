package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uservendoractivityservice.model.CarryBagBean;
import com.offershopper.uservendoractivityservice.model.OfferBean;
/*
 * File name:CarryBagProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with carrybag-database-service and provides the functions to add offes  , retrieve offers details or delete offers from carrybag.
 */
@FeignClient("carry-bag-database-service")
@RibbonClient(name="carry-bag-database-service")
public interface CarryBagProxy 
{
  @GetMapping("/bag/user-id/{userId}")
  public List<CarryBagBean> getAllByUserId(@PathVariable("userId") String userId);

  @PostMapping("/bag/add")
  public ResponseEntity<Object> addToCarryBag(@RequestBody CarryBagBean carrybean);

  @DeleteMapping("/bag/userId/{userId}/offerId/{offerId}")
  public ResponseEntity<Object> deleteById(@PathVariable ("userId") String userId,@PathVariable ("offerId") String offerId);

  @PutMapping("/bag/update")
  public ResponseEntity<Object> updateCarryBag(@RequestBody CarryBagBean carrybagBean);
}
