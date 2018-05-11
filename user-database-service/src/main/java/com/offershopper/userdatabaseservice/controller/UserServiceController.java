package com.offershopper.userdatabaseservice.controller;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.userdatabaseservice.MessageSender;
import com.offershopper.userdatabaseservice.model.UserBean;
import com.offershopper.userdatabaseservice.repository.UserRepository;

@RestController
@RequestMapping("users")
public class UserServiceController {
	protected Logger logger = LoggerFactory.getLogger(UserServiceController.class.getName());
	
/*	@Autowired
	private MessageSender messageSender;*/
	@Autowired
	private UserRepository userRepository;

	/*
	 * Name: updateProfile 
	 * Date: 5-April-2018 
	 * Description: update user profile in database 
	 * Required files: offerShopperDB database, UserBean,AddressBean
	 */

	
	  @PostMapping("/add") 
	  public String addUser(@RequestBody UserBean user) {
		  userRepository.save(user); 
		  return "user added";
	  }
	 

	@HystrixCommand(fallbackMethod = "updateProfileFallback")
	@PutMapping("/update")
	public ResponseEntity<Object> updateProfile(@RequestBody UserBean user) {
			Optional<UserBean> userToUpdate = userRepository.findById(user.getEmail());
			if (userToUpdate.isPresent()) {

				if (user.getAddress() != null && userToUpdate.get().getAddress() != user.getAddress()) {
					userToUpdate.get().setAddress(user.getAddress());
				}
				
				if(user.getOsCash()!=userToUpdate.get().getOsCash()) {
					userToUpdate.get().setOsCash(user.getOsCash());
				}
				
				if(user.getRole()!=userToUpdate.get().getRole()) {
					userToUpdate.get().setRole(user.getRole());
				}
				
				if(user.getSpinCount()!=userToUpdate.get().getSpinCount()) {
					userToUpdate.get().setSpinCount(user.getSpinCount());
				}
				
				if(user.getTimestamp()!=userToUpdate.get().getTimestamp()) {
					userToUpdate.get().setTimestamp(user.getTimestamp());
				}
				
				if(user.getPassword()!=userToUpdate.get().getPassword()) {
					userToUpdate.get().setPassword(user.getPassword());
				}

				if (user.getShopAddress() != null && userToUpdate.get().getShopAddress() != user.getShopAddress()) {
					userToUpdate.get().setShopAddress(user.getShopAddress());
				}

				if (user.getMobileNo() != null && userToUpdate.get().getMobileNo() != user.getMobileNo()) {
					Optional<UserBean> exist = userRepository.findByMobileNo(user.getMobileNo());
					if (!exist.isPresent()) {
						userToUpdate.get().setMobileNo(user.getMobileNo());
						userRepository.save(userToUpdate.get());
						return new ResponseEntity<Object>(HttpStatus.OK);
					}
					return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
				}

				if (user.getVendorMobileNo() != null
						&& userToUpdate.get().getVendorMobileNo() != user.getVendorMobileNo()) {
					Optional<UserBean> exist = userRepository.findByVendorMobileNo(user.getVendorMobileNo());
					if (!exist.isPresent()) {
						userToUpdate.get().setVendorMobileNo(user.getVendorMobileNo());
						userRepository.save(userToUpdate.get());
						return new ResponseEntity<>(HttpStatus.OK);
					}
					return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
				}
				userRepository.save(userToUpdate.get());
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
		
		return null;
	}

	public ResponseEntity<Object> updateProfileFallback(@RequestBody UserBean user) {
		return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/*
	 * Name: findUserByEmail 
	 * Date: 5-April-2018 
	 * Description: returns user with particular email id 
	 * Required files: offerShopperDB database,UserBean,AddressBean
	 */
	@HystrixCommand(fallbackMethod = "findUserByEmailFallback")
	@GetMapping("/details/{email}")
	public ResponseEntity<Optional<UserBean>> findUserByEmail(@PathVariable String email) {	
			Optional<UserBean> user = userRepository.findById(email);
			if (user.isPresent()) {
				user.get().setPassword(null);
		//		messageSender.produceMsg("details of user by email fetched");
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
			else {
				return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
			}
	}

	public ResponseEntity<Optional<UserBean>> findUserByEmailFallback(@PathVariable String email) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}

	/*
	 * Name: deleteCustomer 
	 * Date: 10-April-2018 
	 * Description: Deletes the user from database 
	 * Required files: offerShopperDB database,UserBean,AddressBean
	 */
	@HystrixCommand(fallbackMethod = "deleteUserFallback")
	@DeleteMapping("/delete/{email}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable String email) {
			Optional<UserBean> user = userRepository.findById(email);
			if (user.isPresent()) {
				userRepository.delete(user.get());
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
	}

	public ResponseEntity<Object> deleteUserFallback(@PathVariable String email) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/*
	 * Name: sendOsCash 
	 * Date: 18-April-2018 
	 * Description: Retrieves OsCash from database 
	 * Required files: offerShopperDB database,UserBean
	 
	@HystrixCommand(fallbackMethod = "sendOsCashFallback")
	@GetMapping("/osCash/{email}")
	public ResponseEntity<Integer> sendOsCash(@PathVariable String email) {
		Optional<UserBean> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			int cash=user.get().getOsCash();
			return ResponseEntity.status(HttpStatus.OK).body(cash);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
	public ResponseEntity<Integer> sendOsCashFallback(@PathVariable String email) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	 * Name: updateOsCash 
	 * Date: 18-April-2018 
	 * Description: Updates OsCash in database 
	 * Required files: offerShopperDB database,UserBean
	 
	@HystrixCommand(fallbackMethod = "updateOsCashFallback")
	@PutMapping("/osCash")
	public ResponseEntity<Object> updateOsCash(@RequestBody UserBean user) {
		Optional<UserBean> userToUpdate = userRepository.findByEmail(user.getEmail());
		
			int cash=user.getOsCash();
			return ResponseEntity.status(HttpStatus.OK).body(cash);
		}
		else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
		}
	}
	public ResponseEntity<Object> updateOsCashFallback(@PathVariable String email) {
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}*/

}
