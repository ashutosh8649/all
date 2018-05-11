package com.offershopper.uaa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService1 {
	
    
	private JavaMailSender javamailsender;
	
	
	@Autowired
	public NotificationService1(JavaMailSender javamailsender) {
		this.javamailsender=javamailsender;
	}
	
	
	public void sendNotification(User user) throws MailException{
		
		// send notification;
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo("nikky101403122@gmail.com");
		mail.setFrom("mubina101403122@gmail.com");
		mail.setSubject("Verification for Offer Shoffer");
		mail.setText("Click the link for compleate eregistration");
		
		javamailsender.send(mail);
		
	}

}
