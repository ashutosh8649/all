package com.offershopper.vendorprofileservice.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offershopper.vendorprofileservice.VendorProfileServiceApplication;

@Service
public class MessageSender {
  @Autowired
  private AmqpTemplate amqpTemplate;

   /*
   * Name: produceMsg
   * Author:Ashutosh Kumar Mishra, Dinesh Verma Date:
   * 7th Apr,2018 Discription: This method sends msg to rabbitmq
   */
  public void produceMsg(String msg) {
    amqpTemplate.convertAndSend(VendorProfileServiceApplication.EXCHANGE_NAME, VendorProfileServiceApplication.ROUTING_KEY, msg);
    System.out.println("msg"+msg);
  }

}
