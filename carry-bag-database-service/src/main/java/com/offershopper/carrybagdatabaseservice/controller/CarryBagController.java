package com.offershopper.carrybagdatabaseservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.carrybagdatabaseservice.model.CarryBagBean;
import com.offershopper.carrybagdatabaseservice.rabbit.MessageSender;
import com.offershopper.carrybagdatabaseservice.repository.CarryBagRepository;
@CrossOrigin
@Component
@RestController

/*
* File name:CarryBagController
* Author: Mukesh,Anand
* Date: 13-April-2018
* Description: Controller Class of databse service interacting with UI and provides carrybag details
* Referred Files: Model Classes: CarryBagBean mongodbconfig.java,carrybagdatabaseservice.application*/


public class CarryBagController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	//using messege sender service to send message to rabbitmq
	
	@Autowired
	private CarryBagRepository carryBagRepository;
	
	@Autowired
	private MessageSender messageSender;


	//get method to get all carrybag offers 
	@HystrixCommand(fallbackMethod="fallbackGetAllBag")
	@GetMapping(value="/bag/userId",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CarryBagBean>> getAllUser() 
	{

		logger.info(String.format("%s",carryBagRepository.findAll() ));
		messageSender.produceMessage("All offers have been retireved from get mapping  ");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(carryBagRepository.findAll());
		
	
	}

	
//fallback method for getAllUser() method
	public ResponseEntity<List<CarryBagBean>> fallbackGetAllBag() 
	{
		List <CarryBagBean> emptyList=new ArrayList<CarryBagBean>();
		emptyList.add(new CarryBagBean("default","default","default","default",
		0l,0l,"default","default"));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emptyList);
	}
	
	
// method to get the offers list by id
	@HystrixCommand(fallbackMethod="fallbackGetAllBagById")
	@GetMapping(value="/bag/userId/{userId}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<CarryBagBean>> getAllByUserId(@PathVariable String userId)
	{

		logger.info(String.format("%s",carryBagRepository.findByUserId(userId) ));
		messageSender.produceMessage("single  offers has been retireved from get mapping  ");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(carryBagRepository.findByUserId(userId));
	
	}
	
	//Fallback method for getting the offer from the list

	public ResponseEntity<List<CarryBagBean>> fallbackGetAllBagById(@PathVariable String userId) {
		List <CarryBagBean> emptyList=new ArrayList<CarryBagBean>();
		emptyList.add(new CarryBagBean("default","default","default","default",
		0l,0l,"default","default"));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(emptyList);
	}
	
	
	//method for adding ofers into carrybag list
	@HystrixCommand(fallbackMethod="fallbackaddToCarryBag")
	@PostMapping("/bag/add")
	public ResponseEntity<Object>  addToCarryBag(@RequestBody CarryBagBean entity) throws Exception 
	{
		
		if(carryBagRepository.existsById(entity.getOfferId())) 
		{
			
			logger.info(String.format("Offer Already exist" ));
			messageSender.produceMessage("offer already exist you can't add it to the list");
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}

			carryBagRepository.insert(entity);
			logger.info(String.format("this offer is saved in the database-> %s",entity));
			messageSender.produceMessage("offer is added to the list");
			return ResponseEntity.status(HttpStatus.CREATED).body(null);
	}


	//Fallback method for adding the offer to the list
	public ResponseEntity<Object>  fallbackaddToCarryBag(@RequestBody CarryBagBean entity) 
	{
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	
	//method to update carrybag 
	@HystrixCommand(fallbackMethod="fallbackUpdateCarryBag")
	@PutMapping("/bag/update")
	public ResponseEntity<Object> updateCarryBag(@RequestBody CarryBagBean entity)throws Exception  
	{
		    if(carryBagRepository.existsById(entity.getOfferId())) 
		    {

	                        carryBagRepository.save(entity);
				messageSender.produceMessage("offer is updated to the list");
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}
		 
	        messageSender.produceMessage("offer is not found in the list");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	//Fallback method for adding the offer to the list
	   public ResponseEntity<Object> fallbackUpdateCarryBag(@RequestBody CarryBagBean entity) 
	   {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);  
	   }
	
	//method to delete offer from carry bag 
	@HystrixCommand(fallbackMethod="fallbackDeleteFromCarryBag")
	@DeleteMapping("/bag/offerId/{id}")
	public ResponseEntity<Object> deleteById(@PathVariable String id) 
	{
		
		 if(carryBagRepository.existsByofferId(id)) 
		 {
			carryBagRepository.deleteById(id);
			logger.info(String.format("this offer is deleted from database"));
		        messageSender.produceMessage("offer is delete from  the list");
		        return ResponseEntity.status(HttpStatus.OK).body(null);
		 }
		 
		 else
		 {
			    messageSender.produceMessage("offer is not found to delete from  the list");
			    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		 }
	}
		 
		
		    

	//Fallback method for delete the offer from the list
	public ResponseEntity<Object> fallbackDeleteFromCarryBag(@PathVariable String id) 
	{
		

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	
	
}
