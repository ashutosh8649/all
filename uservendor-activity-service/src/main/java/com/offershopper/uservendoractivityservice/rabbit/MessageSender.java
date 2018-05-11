package com.offershopper.uservendoractivityservice.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offershopper.uservendoractivityservice.UservendorActivityServiceApplication;

@Service
public class MessageSender {
  @Autowired
  private AmqpTemplate amqpTemplate;

  public void produceMessage(String message) {
    //using the template defining the needed parameters- exchange name,key and message
    amqpTemplate.convertAndSend(UservendorActivityServiceApplication
        .EXCHANGE_NAME, UservendorActivityServiceApplication.ROUTING_KEY, message);
    System.out.println("Send msg = " + message);
  }
}

