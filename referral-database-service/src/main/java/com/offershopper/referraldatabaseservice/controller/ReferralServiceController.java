package com.offershopper.referraldatabaseservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.referraldatabaseservice.MessageSender;
import com.offershopper.referraldatabaseservice.model.UserBean;
import com.offershopper.referraldatabaseservice.repository.UserRepository;

@RestController
@RequestMapping("referral")

public class ReferralServiceController {
	@Autowired
	private MessageSender messageSender;

	@Autowired
	private UserRepository userRepository;

	/*
	 * Name: addSpin Date: 11-April-2018 Description: update spin count for the user
	 * who referred and for the user who has been referred Required files:
	 * offerShopperDB database
	 */
	@HystrixCommand(fallbackMethod = "addSpinFallback")
	@PutMapping("/spin/{email1}/{email2}")
	public ResponseEntity<Object> addSpin(@PathVariable String email1, @PathVariable String email2) {
		UserBean user1 = userRepository.findByEmail(email1);
		UserBean user2 = userRepository.findByEmail(email2);
		if (user1 == null || user2 == null) {
			return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
		}
		user1.setSpinCount(user1.getSpinCount() + 1);
		userRepository.save(user1);
		user2.setSpinCount(user2.getSpinCount() + 1);
		userRepository.save(user2);
		messageSender.produceMsg("spin count incremented for both users");
		return new ResponseEntity<Object>(HttpStatus.OK);

	}

	public ResponseEntity<Object> addSpinFallback(@PathVariable String email1, @PathVariable String email2) {
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
