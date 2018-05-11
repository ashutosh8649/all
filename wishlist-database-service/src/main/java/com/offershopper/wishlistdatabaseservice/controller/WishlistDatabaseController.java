package com.offershopper.wishlistdatabaseservice.controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.wishlistdatabaseservice.database.WishlistRepository;
import com.offershopper.wishlistdatabaseservice.model.WishlistBean;
import com.offershopper.wishlistdatabaseservice.rabbit.MessageSender;

@CrossOrigin
@RestController
public class WishlistDatabaseController {
  
  //creating instance of repository to save and get data from database
  @Autowired
  private WishlistRepository wishlistRepository;
  
  @Autowired
  private MessageSender messageSender;

        
  /*Name of method : addToWishlist
   *Description : This method has a post mapping and adds offers to wishlist
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @PostMapping("/add-to-wishlist")
  @HystrixCommand(fallbackMethod="addToWishlistFallback") 
  public ResponseEntity<Object> addToWishlist(@RequestBody WishlistBean wishlistBean) {
    if(wishlistRepository.existsByUserIdAndOfferId(wishlistBean.getUserId(),wishlistBean.getOfferId())) {
      messageSender.produceMessage("Offer already exist in wishlist");
      //returns 409
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
    else {
      wishlistRepository.insert(wishlistBean);
      messageSender.produceMessage("Added successfully");
      //returns 201
      return new ResponseEntity<>(HttpStatus.CREATED);
    }
  }
  
  /*Name of method : updateWishlistOffer
   *Description : This method has a put mapping and update offers in wishlist
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @PutMapping("/update-wishlist-offer")
  @HystrixCommand(fallbackMethod="updateWishlistOfferFallback") 
  public ResponseEntity<Object> updateWishlistOffer(@RequestBody WishlistBean wishlistBean) {
    List<WishlistBean> wishlistData = wishlistRepository.findAll();
    ListIterator<WishlistBean> listIterator =wishlistData.listIterator();
    Integer flag = 0;
    while(listIterator.hasNext()) {
      WishlistBean wish = listIterator.next();
      if((wishlistBean.getOfferId()).equals(wish.getOfferId())) {
        if(wishlistBean.getOfferTitle()!=null)
          wish.setOfferTitle(wishlistBean.getOfferTitle());
        if(wishlistBean.getOfferOriginalPrice()!=null)
          wish.setOfferOriginalPrice(wishlistBean.getOfferOriginalPrice());
        if(wishlistBean.getOfferDiscount()!=null)
          wish.setOfferDiscount(wishlistBean.getOfferDiscount());
        if(wishlistBean.getOfferImage()!=null)
          wish.setOfferImage(wishlistBean.getOfferImage());
        if(wishlistBean.getOfferValidity()!=null)
          wish.setOfferValidity(wishlistBean.getOfferValidity());
        wishlistRepository.save(wish);
        flag = 1;
      }
    }
    if(flag==1) {        
      messageSender.produceMessage("Updated successfully");
      //returns 200
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
      messageSender.produceMessage("Offer not updated");
      //returns 404
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }  
  
  /*Name of method : deleteWishlistOffer
   *Description : This method has a delete mapping and delete offers from everyone wishlist
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @DeleteMapping("/delete-wishlist-offer/{offerId}")
  @HystrixCommand(fallbackMethod="deleteWishlistOfferFallback") 
  public ResponseEntity<Object> deleteWishlistOffer(@PathVariable String offerId) {
    if(wishlistRepository.existsByOfferId(offerId)) {
      wishlistRepository.deleteByOfferId(offerId);
      messageSender.produceMessage("Deleted successfully");
      //returns 200
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
      messageSender.produceMessage("Offer doesn't exist in any of the user's wishlist");
      //returns 404
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
   
  /*Name of method : deleteWishlistUserOffer
   *Description : This method has a delete mapping and delete a offer from a particular user wishlist
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @DeleteMapping("/delete-wishlist-offer/{offerId}/{userId}")
  @HystrixCommand(fallbackMethod="deleteWishlistUserOfferFallback") 
  public ResponseEntity<Object> deleteWishlistUserOffer(@PathVariable String offerId,@PathVariable String userId) {
    if(wishlistRepository.existsByOfferIdAndUserId(offerId,userId)) {
      wishlistRepository.deleteByOfferIdAndUserId(offerId,userId);
      messageSender.produceMessage("Deleted successfully");
      //returns 200
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
      messageSender.produceMessage("Offer doesn't exist in user's wishlist");      
      //returns 404
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
    
  /*Name of method : deleteWishlistUser
   *Description : This method has a delete mapping and delete user wishlist on deleting user's account
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @DeleteMapping("/delete-wishlist-user/{userId}")
  @HystrixCommand(fallbackMethod="deleteUserWishlistFallback") 
  public ResponseEntity<Object> deleteUserWishlist(@PathVariable String userId) {
    if(wishlistRepository.existsByUserId(userId)) {
      wishlistRepository.deleteByUserId(userId);
      messageSender.produceMessage("Deleted successfully");
      //returns 200
      return new ResponseEntity<>(HttpStatus.OK);
    }
    else {
      messageSender.produceMessage("User doesn't exist in wishlist");
      //returns 404
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }
    
  /*Name of method : getWishlist
   *Description : This method has a get mapping and get user's wishlist
   *Date : 06-04-2018
   *Dependent on : wishlistRepository
   */
  @GetMapping("/getWishlist/{userId}")
  @HystrixCommand(fallbackMethod="getWishlistFallback") 
  public ResponseEntity<List<WishlistBean>> getWishlist(@PathVariable String userId) {
    if(wishlistRepository.existsByUserId(userId)) {
      messageSender.produceMessage("All offers in user's wishlist has been retrieved");
      //returns 200 along with all the offers
      return ResponseEntity.status(HttpStatus.OK).body(wishlistRepository.findByUserId(userId));
    }
    else {
      messageSender.produceMessage("Wishlist is empty");
      //returns 404
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.EMPTY_LIST); 
    }
  }
    
  //all fallback methods
  
  //fallback method which returns code 500
  public ResponseEntity<Object> addToWishlistFallback(@RequestBody WishlistBean wishlistBean) {
	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  public ResponseEntity<Object> updateWishlistOfferFallback(@RequestBody WishlistBean wishlistBean) {
	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  public ResponseEntity<Object> deleteWishlistOfferFallback(@PathVariable String offerId) {
	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  public ResponseEntity<Object> deleteWishlistUserOfferFallback(@PathVariable String offerId,@PathVariable String userId) {
	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  public ResponseEntity<Object> deleteUserWishlistFallback(@PathVariable String userId) {
	  return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  public ResponseEntity<List<WishlistBean>> getWishlistFallback(@PathVariable String userId) {    
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}
