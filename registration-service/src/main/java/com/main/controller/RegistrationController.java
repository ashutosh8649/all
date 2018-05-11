package com.main.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.main.modal.RegisterInfo;
import com.main.modal.UserInformation;
import com.main.service.UserRegistrationService;

@RestController
@CrossOrigin
public class RegistrationController {

	@Autowired
	UserRegistrationService userService;

	@PostMapping("/register")
	// adding validation using @valid
	public ResponseEntity<String> addUserInformation(@RequestBody RegisterInfo dataOfUserInBody) {
		System.out.println(dataOfUserInBody);
		try {
			userService.getUserInformation(dataOfUserInBody.getEmail());
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("Already Exists");
		} catch (Exception e) {
			// this is added to give a response that the uri that is going to be created is
			// successfully created
			//userService.addUserInformation(dataOfUserInBody);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(dataOfUserInBody.getEmail()).toUri();
			return ResponseEntity.created(location)
					.body(dataOfUserInBody.getEmail() + "," + dataOfUserInBody.getRole());
		}
	}
	
	// adding validation using @valid
	@PostMapping("/register/user")
	public ResponseEntity<String> addUser(@RequestBody RegisterInfo dataOfUserInBody) {
		userService.addUserInformation(dataOfUserInBody);
		return ResponseEntity.status(HttpStatus.CREATED).body("User Added");
	}

	@GetMapping("/register")
	public ResponseEntity<List<String>> getAllRegisteredUserInformation() {
		List<String> userNames = new ArrayList<String>();
		Iterator<RegisterInfo> iterator = userService.getAllRegisteredUserInformation().iterator();
		while (iterator.hasNext()) {
			RegisterInfo user = iterator.next();
			userNames.add(user.getEmail());
		}

		return ResponseEntity.status(HttpStatus.OK).body(userNames);
	}

	@GetMapping("/register/{id}")
	public ResponseEntity<Optional<RegisterInfo>> getUserInformation(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserInformation(id));
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<RegisterInfo>> allUsers(){
		List<RegisterInfo> users = userService.getAllRegisteredUserInformation();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> loginUser(@RequestBody UserInformation dataOfUserInBody) {

		RegisterInfo info = null;
		Optional<RegisterInfo> retrieverIformation = null;
		try {
			retrieverIformation = userService.getUserInformation(dataOfUserInBody.getEmail());
			info = retrieverIformation.get();
			String getPassword = info.getPassword();
			if (getPassword.equals(dataOfUserInBody.getPassword()))
				return ResponseEntity.status(HttpStatus.OK).body(dataOfUserInBody.getEmail() + "," + info.getRole());			
			else
				return ResponseEntity.status(HttpStatus.OK).body("Password InCorrect");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body("User Does not exist");
		}
	}
	@PostMapping("/updatepassword")
    public String resetPassword(@RequestBody RegisterInfo obj) {
        userService.deleteUserInformation(obj.getEmail());
        userService.addUserInformation(obj);
        return "reset";
    }
}