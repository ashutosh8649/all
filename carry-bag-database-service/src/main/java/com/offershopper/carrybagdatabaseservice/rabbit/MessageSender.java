package com.offershopper.carrybagdatabaseservice.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offershopper.carrybagdatabaseservice.CarryBagDatabaseServiceApplication;


@Service
public class MessageSender {
  @Autowired
  private AmqpTemplate amqpTemplate;
  
  public void produceMessage(String message) {
      //using the template defining the needed parameters- exchange name,key and message
      amqpTemplate.convertAndSend(CarryBagDatabaseServiceApplication
          .EXCHANGE_NAME, CarryBagDatabaseServiceApplication.ROUTING_KEY, message);
      System.out.println("Send msg = " + message);
    }
  }

