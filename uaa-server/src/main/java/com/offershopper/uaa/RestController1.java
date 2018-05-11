package com.offershopper.uaa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestController1 {
	
	
	private Logger logger = LoggerFactory.getLogger(RestController1.class);
	
	@Autowired
	private NotificationService1 notificationService;

	@GetMapping("/signup")
	public String signup() {
		return "Please sign in for the service";
	}

	@GetMapping("/sign-success")
	public String signupsucess() {
		
		
		//user create
		User user = new User();
		user.setEmail("nikky101403122@gmail.com");
		user.setFirstname("Dinesh");
		user.setLastname("verma");
		
		//send notification 
		try {
			notificationService.sendNotification(user);
			
		}catch (MailException e){
			
			logger.info("**********"+e.getMessage());
			//error handel here 
		}
		
		return "thanku fpr registration with us ";
	}

}
