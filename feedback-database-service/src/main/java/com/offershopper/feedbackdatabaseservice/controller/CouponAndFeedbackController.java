package com.offershopper.feedbackdatabaseservice.controller;
import java.util.Collections;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.feedbackdatabaseservice.database.*;
import com.offershopper.feedbackdatabaseservice.model.*;
import com.offershopper.feedbackdatabaseservice.rabbitmq.MessageSender;
@CrossOrigin
@RestController
@RequestMapping("/os")
public class CouponAndFeedbackController {
	
	@Autowired
	public CouponAndFeedbackRepository couponAndFeedbackRepository;
	    
	@Autowired
	private MessageSender sendMessageToRabbit;
	
    //adds feedback of user to DB
    @HystrixCommand(fallbackMethod="fallbackAddFeedback")
	@PostMapping("/addfeedback")
	public ResponseEntity<Object> addFeedback(@RequestBody CouponAndFeedbackBean coup)
	{
      if(couponAndFeedbackRepository.existsByOfferIdAndUserId(coup.getOfferId(), coup.getUserId())) {
        return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByOfferIdAndUserId(coup.getOfferId(), coup.getUserId()));
      }
      
      coup.setCouponId(new ObjectId().get().toString());
		couponAndFeedbackRepository.save(coup);
		sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.save(%s)", coup));
		return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByOfferIdAndUserId(coup.getOfferId(), coup.getUserId()));
	}
    
    //fallback method for addFeedback
	public ResponseEntity<Object> fallbackAddFeedback(@RequestBody CouponAndFeedbackBean coup) {
	    
		return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Service unavailable");

	}
	
	
  @HystrixCommand(fallbackMethod="fallbackAddFeedbackComment")
@PostMapping("/addfeedback/comment")
public ResponseEntity<Object> addFeedbackComment(@RequestBody CouponAndFeedbackBean coup)
{
  couponAndFeedbackRepository.save(coup);
  sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.save(%s)", coup));
  return ResponseEntity.status(HttpStatus.OK).body(coup);
}
  
  //fallback method for addFeedback
public ResponseEntity<Object> fallbackAddFeedbackComment(@RequestBody CouponAndFeedbackBean coup) {
    
  return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Optional.empty());

}
	
//gets feedbacks of an offer by its offerId
@GetMapping(value = "/getfeedback/couponId/{couponId}/vendorId/{vendorId}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> getFeedbackByCouponId(@PathVariable String couponId, @PathVariable String vendorId)
{
  
  sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.findById(%s)",couponId));
 if(couponAndFeedbackRepository.existsByCouponIdAndVendorId(couponId, vendorId)) {
  return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByCouponIdAndVendorId(couponId,vendorId)); 
 }
 return ResponseEntity.status(HttpStatus.OK).body(Optional.empty()); 

 
 }

	

//gets feedbacks of an offer by its offerId
@HystrixCommand(fallbackMethod="fallbackGetCouponByUserIdAndOfferId")
@GetMapping(value = "/getfeedback/userId/{userId}/offerId/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> getCouponByUserIdAndOfferId(@PathVariable String userId,
    @PathVariable String offerId)
{
  sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.findByOfferId(%s)",offerId));
    
  if(couponAndFeedbackRepository.existsByOfferIdAndUserId(offerId,userId)) {
    return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByOfferIdAndUserId(offerId,userId)); 
  }
  return ResponseEntity.status(HttpStatus.OK).body(new CouponAndFeedbackBean(
      null,null, null,0F,null,false,null
      )); 

  }

//fallback method for getFeedback
public ResponseEntity<Object> fallbackGetCouponByUserIdAndOfferId(@PathVariable String userId,
    @PathVariable String offerId)
{ 
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
}

	//gets feedbacks of an offer by its offerId
	@HystrixCommand(fallbackMethod="fallbackGetFeedback")
	@GetMapping(value = "/getfeedback/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CouponAndFeedbackBean>> getFeedback(@PathVariable String offerId)
	{
		sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.findByOfferId(%s)",offerId));
			return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByOfferId(offerId));	
	}
	
	//fallback method for getFeedback
	public ResponseEntity<List<CouponAndFeedbackBean>> fallbackGetFeedback(@PathVariable String offerId)
	{	
	    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
	}
	
	
	
	@HystrixCommand(fallbackMethod="fallbackGetFeedbackByVendorId")
  @GetMapping(value = "/getfeedback/vendorId/{vendorId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<CouponAndFeedbackBean>> getFeedbackByVendorId(@PathVariable String vendorId)
  {   System.out.println("entering the loop");
    sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.findByOfferId(%s)",vendorId));
      return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByVendorIdAndFeedbackNotNull(vendorId)); 
  }
  
  //fallback method for getFeedback
  public ResponseEntity<List<CouponAndFeedbackBean>> fallbackGetFeedbackByVendorId(@PathVariable String offerId)
  { 
      return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Collections.emptyList());
  }
  
	
	
	
	
	
	
  
  //gets feedbacks of an offer by its offerId and vendorId
 @HystrixCommand(fallbackMethod="fallbackGetCouponByVendorIdAndOfferId")
 @GetMapping(value = "/getfeedback/vendorId/{vendorId}/offerId/{offerId}", produces = MediaType.APPLICATION_JSON_VALUE)
 public ResponseEntity<Object> getCouponByVendorAndOfferId(@PathVariable String vendorId,
     @PathVariable String offerId)
 {
   sendMessageToRabbit.produceMsg(String.format("CouponAndFeedback.findByVendorIdAndOfferId(%s,%s)",offerId,vendorId));
     
   if(couponAndFeedbackRepository.existsByOfferIdAndVendorId(offerId,vendorId)) {
     return ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackRepository.findByOfferIdAndVendorId(offerId,vendorId)); 
   }
   return ResponseEntity.status(HttpStatus.OK).body(new CouponAndFeedbackBean(
       null,null, null,0F,null,false,null
       )); 

   }
 
 //fallback method for getFeedback
 public ResponseEntity<Object> fallbackGetCouponByVendorIdAndOfferId(@PathVariable String userId,
     @PathVariable String offerId)
 { 
     return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
 }
  
  
	
}
