package com.offershopper.subscribedatabaseservice.controller;


/*
* Name: Subscription Module 
* Author:Ashutosh Kumar Mishra, Dinesh Verma 
* Date:7th Apr,2018
* Description: This module provides CRUD operation on database
* Required Files/Databases: SubscribeBean.class,MessageSender.class
*/
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.subscribedatabaseservice.database.SubscribeRepository;
import com.offershopper.subscribedatabaseservice.model.SubscribeBean;
import com.offershopper.subscribedatabaseservice.service.MessageSender;

@RestController
@CrossOrigin
@RequestMapping("/subscribe")
public class SubscribeController {

  @Autowired
  private MessageSender sendMessageToRabbit;
  
  List<SubscribeBean> subscribeBeans;
  
  private SubscribeRepository subscribeRepository;

  public SubscribeController(SubscribeRepository subscribeRepository) {
    this.subscribeRepository = subscribeRepository;

  }

  /*
   * Description: This method retrieves subscription from the database for a specific user 
   * Required Files/Databases: SubscribeBean.class,MessageSender.class
   */
  @HystrixCommand(fallbackMethod = "getFallback")
  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SubscribeBean> getAll() {
    String msg = "getting subscription data";
    sendMessageToRabbit.produceMsg(msg);
    List<SubscribeBean> subscribeBeans=subscribeRepository.findAll();
    if(subscribeBeans==null)
    {
      return Collections.emptyList();
    }
    return subscribeBeans;

  }

  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  public List<SubscribeBean> getFallback() {
    String msg = "Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return Collections.emptyList();
  }
  
  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  //@HystrixCommand(fallbackMethod = "getByIdFallback")
  @GetMapping(value = "/getUser/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<SubscribeBean> getByUserId(@PathVariable("userId") String userId) {

    List<SubscribeBean> subscribeBeanList = subscribeRepository.findAllByUserId(userId);

    if(subscribeBeanList.isEmpty())
    {
      sendMessageToRabbit.produceMsg("no subscription available");
      return null;
    }
    else {
      sendMessageToRabbit.produceMsg("getting subscription data");
      return subscribeBeanList;

    }


  }

  /*
   *  Description: if the above method throws exception then call this  method 
   * Required Files/Databases: MessageSender.class
   */
  
  public Optional<SubscribeBean> getByIdFallback(@PathVariable String userId) {
    String msg = "Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return Optional.empty();

  }


  /*
   * Description: This method add subscription to database .
   * Required Files/Databases: MessageSender.class
   */
  @HystrixCommand(fallbackMethod = "addByVendorIdFallback")
  @PostMapping(value = "/add/byvendorid", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> addByVendorId(@RequestBody SubscribeBean subscribeBean) {

    Optional<SubscribeBean> option = subscribeRepository.findByUserIdAndVendorId(subscribeBean.getUserId(),subscribeBean.getVendorId());
    if (option.isPresent()) {
      System.out.println("this product is already existing");
      return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    subscribeRepository.insert(subscribeBean);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  public ResponseEntity<HttpStatus> addByVendorIdFallback(@RequestBody SubscribeBean subscribeBean) {
    String msg = "Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

  /*
   * Description: This method add subscription to database .
   * Required Files/Databases: MessageSender.class
   */
  @HystrixCommand(fallbackMethod = "addByCategoryFallback")
  @PostMapping(value = "/add/bycategory", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<HttpStatus> addByCategory(@RequestBody SubscribeBean subscribeBean) {

    Optional<SubscribeBean> option = subscribeRepository.findByUserIdAndCategory(subscribeBean.getUserId(),subscribeBean.getCategory());
    if (option.isPresent()) {
      System.out.println("this product is already existing");
      return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

    subscribeRepository.insert(subscribeBean);
    return new ResponseEntity<>(HttpStatus.OK);
  }
  
  
  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  public ResponseEntity<HttpStatus> addByCategoryFallback(@RequestBody SubscribeBean subscribeBean) {
    String msg = "vendor Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
  
  
  /*
   * Description: delete any subscription entry 
   * Required Files/Databases:MessageSender.class
   */
  @HystrixCommand(fallbackMethod = "deleteByVendorIdFallback")
  @DeleteMapping(value = "/del/byvendorid/{userId}/{vendorId}")
  public ResponseEntity<HttpStatus> deleteByVendorId(@PathVariable("userId") String userId, @PathVariable("vendorId") String vendorId) {
    Optional<SubscribeBean> option = subscribeRepository.findByUserIdAndVendorId(userId, vendorId);
    if (option.isPresent()) {
      subscribeRepository.deleteById(option.get().getId());
      return new ResponseEntity<>(HttpStatus.OK);
    } else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  public ResponseEntity<HttpStatus> deleteByVendorIdFallback(@PathVariable("userId") String userId, @PathVariable("vendorId") String vendorId) {
    String msg = "category Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
  
  
  /*
   * Description: delete any subscription entry 
   * Required Files/Databases:MessageSender.class
   */
  @HystrixCommand(fallbackMethod = "deleteByCategoryFallback")
  @DeleteMapping(value = "/del/bycategory/{userId}/{category}")
  public ResponseEntity<HttpStatus> deleteByCategory(@PathVariable("userId") String userId, @PathVariable("category") String category) {
    Optional<SubscribeBean> option = subscribeRepository.findByUserIdAndCategory(userId, category);
    if (option.isPresent()) {
      subscribeRepository.deleteById(option.get().getId());
      return new ResponseEntity<>(HttpStatus.OK);
    } else
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  /*
   * Description: if the above method throws exception then call this method 
   * Required Files/Databases: MessageSender.class
   */
  public ResponseEntity<HttpStatus> deleteByCategoryFallback(@PathVariable("userId") String userId, @PathVariable("category") String category) {
    String msg = "Exception occured, inside fallback";
    sendMessageToRabbit.produceMsg(msg);
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }

}
