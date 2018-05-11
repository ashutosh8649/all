package com.offershopper.offerdatabaseservice.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.offerdatabaseservice.database.OfferRepository;
import com.offershopper.offerdatabaseservice.model.OfferBean;
import com.offershopper.offerdatabaseservice.rabbit.MessageSender;

@CrossOrigin
@RestController
public class OfferController {

	// instance of repository to use mongo repository methods
	@Autowired
	private OfferRepository offersRepository;
	
	@Autowired
	private MessageSender messageSender;

	/*Name of method : addOffer
	 *Description : This method has a post mapping and adds orders
	 *Date : 06-04-2018
	 *Dependent on : offerRepository
	 */
	@HystrixCommand(fallbackMethod = "addOfferFallback")
	@PostMapping("/offers")
	public ResponseEntity<OfferBean> addOffer(@RequestBody OfferBean offer) {
		offer.setOfferId(new ObjectId().get().toString());
	
		offersRepository.save(offer);

		//returns 201
		messageSender.produceMessage("Offer has been added successfully");
		
		return ResponseEntity.status(HttpStatus.CREATED).body(offer);
	}

	/*Name of method : getOffer
   *Description : This method has a get mapping to retrieve all offers
   *Date : 06-04-2018
   *Dependent on : offerRepository
   */
	@GetMapping("/vendor/{userId}/offers")
	@HystrixCommand(fallbackMethod = "getOffersFallback")
	public ResponseEntity<List<OfferBean>> getOffers(@PathVariable String userId) {
		  List<OfferBean> allOffers = offersRepository.findByUserId(userId);
		  if(allOffers.isEmpty()) {
		    messageSender.produceMessage("No offer present in the database");
		  }
		  //returns 200 along with all the offers
		  else {
		    messageSender.produceMessage("All offers have been retireved");
		  }
		  return ResponseEntity.status(HttpStatus.OK).body(allOffers);
	}

	
	/*Name of method : getOfferById
   *Description : This method has a get mapping to get one order and if its not found it returns default
   *Date : 06-04-2018
   *Dependent on : offerRepository
   */
	@GetMapping("/offers/{id}")
	
	@HystrixCommand(fallbackMethod = "getOfferByIdFallback")
	public ResponseEntity<OfferBean> getOfferById(@PathVariable String id) {
  		Optional<OfferBean> offer = offersRepository.findById(id);
  		if (!offer.isPresent()) {
  		  //returns 404 along with a default bean
  		  messageSender.produceMessage("Offer not found");
  		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new OfferBean());
  		}
  		else {
  		  //returns 200 along with the offerBean found
  		  messageSender.produceMessage("Offer retrieved successfully");
  			return ResponseEntity.status(HttpStatus.OK).body(offer.get());
  		}
	}


  /*Name of method : updateOffer
   *Description : This method has a put mapping to update a previously added offer
   *Date : 06-04-2018
   *Dependent on : offerRepository
   */
	@PutMapping("/offers/{id}")
	@HystrixCommand(fallbackMethod = "updateOfferFallback")
	public ResponseEntity<OfferBean> updateOffer(@PathVariable String id, 
	    @RequestBody OfferBean offer) {
  		Optional<OfferBean> offerToBeUpdated = offersRepository.findById(offer.getOfferId());
  		if (offerToBeUpdated.isPresent()) {
  			offersRepository.save(offer);
  			//returns ok along with the new offer added
  			messageSender.produceMessage("Offer updated successfully");
  			return ResponseEntity.status(HttpStatus.OK).body(offer); 
  			} 
  		else {
  		  //return not found along with the original offer
  		  messageSender.produceMessage("Offer not found");
  		  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(offerToBeUpdated.get());
  		}
	}

	/*Name of method : deleteOffer
   *Description : This method has a delete mapping to delete a previously added offer
   *Date : 06-04-2018
   *Dependent on : offerRepository
   */
	@HystrixCommand(fallbackMethod = "deleteOfferFallback")
	@DeleteMapping("/offers/{id}")
	public ResponseEntity<OfferBean> deleteOffer(@PathVariable String id) {
  		Optional<OfferBean> offer = offersRepository.findById(id);
  		if (offer.isPresent()) {
  			offersRepository.delete(offer.get());
  			//return ok along with the object that was deleted
  			messageSender.produceMessage("Offer deleted successfully");
        return ResponseEntity.status(HttpStatus.OK).body(offer.get()); 
  		} else {
  		  //return 404
  		  messageSender.produceMessage("Offer to be deleted wasnt found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  		}
	}
	
	/*Name of method : getOffersByLocation
   *Description : This method has a get mapping to get offers by location
   *Date : 04-05-2018
   *Dependent on : offerRepository
   */
	@GetMapping("/offers/location/{location}")
	public ResponseEntity<List<OfferBean>> getOffersByLocation(@PathVariable String location) {
	  List<OfferBean> offers = offersRepository.findByCity(location);
	  if(offers.isEmpty()) {
	    messageSender.produceMessage("Offers retrieved successfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	  }
	  else {
	    messageSender.produceMessage("no offer found");
	    return ResponseEntity.status(HttpStatus.OK).body(offers);
	  }
	}

	//all fallback methods
	
	// fallback method which returns code 500
	public ResponseEntity<OfferBean> addOfferFallback(OfferBean offer) {
	  messageSender.produceMessage("Add offer fallback");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	public ResponseEntity<OfferBean> updateOfferFallback(String id, OfferBean offer) {
	  messageSender.produceMessage("Update offer fallback");
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	public ResponseEntity<List<OfferBean>> getOffersFallback(String userId) {
	  messageSender.produceMessage("Get offers fallback");
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);	  
	}
	
	public ResponseEntity<OfferBean> getOfferByIdFallback(String id) {
	  messageSender.produceMessage("Get offers by ID fallback");
	   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new OfferBean());
	}
	
	public ResponseEntity<OfferBean> deleteOfferFallback(String id) {
	  messageSender.produceMessage("Delete offer fallback");
	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
