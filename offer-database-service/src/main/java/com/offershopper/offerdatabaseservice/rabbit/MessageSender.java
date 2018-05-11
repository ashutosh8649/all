package com.offershopper.offerdatabaseservice.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offershopper.offerdatabaseservice.OfferDatabaseServiceApplication;

@Service
public class MessageSender {
  @Autowired
  private AmqpTemplate amqpTemplate;
  
  public void produceMessage(String message) {
      //using the template defining the needed parameters- exchange name,key and message
      amqpTemplate.convertAndSend(OfferDatabaseServiceApplication
          .EXCHANGE_NAME, OfferDatabaseServiceApplication.ROUTING_KEY, message);
    }
  }
