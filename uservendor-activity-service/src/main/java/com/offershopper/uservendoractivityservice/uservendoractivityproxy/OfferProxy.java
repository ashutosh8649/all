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

import com.offershopper.uservendoractivityservice.model.OfferBean;
/*
 * File name:OfferProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with offer-database-service and provides the functions to add offes  , retrieve offers details, modify offers or delete offers.
 */
@FeignClient(name="offer-database-service")
@RibbonClient(name="offer-database-service")
public interface OfferProxy 
{   
  @PostMapping("/offers")
  public ResponseEntity<Object> addOffer(@RequestBody OfferBean offerBean);

  @PutMapping("/offers/{id}")
  public ResponseEntity<OfferBean> updateOffer(@PathVariable(value="id") String id, @RequestBody OfferBean offerBean);

  @DeleteMapping("/offers/{id}")
  public ResponseEntity<Object> deleteOffer(@PathVariable(value="id") String id);

  @GetMapping("/offers")
  public List<OfferBean> getOffers();
  
  @GetMapping("/offers/{id}")
  public ResponseEntity<OfferBean> getOfferById(@PathVariable(value="id") String id);
}
