package com.offershopper.wishlistdatabaseservice.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offershopper.wishlistdatabaseservice.WishlistDatabaseServiceApplication;

@Service
public class MessageSender {
  @Autowired
  private AmqpTemplate amqpTemplate;
  
  public void produceMessage(String message) {
      //using the template defining the needed parameters- exchange name,key and message
      amqpTemplate.convertAndSend(WishlistDatabaseServiceApplication
          .EXCHANGE_NAME, WishlistDatabaseServiceApplication.ROUTING_KEY, message);
      System.out.println("Send msg = " + message);
    }
  }

