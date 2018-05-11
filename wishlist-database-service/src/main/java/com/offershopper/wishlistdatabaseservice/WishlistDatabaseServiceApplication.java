/*
 * Name of module : wishlist-database-service
 * Name of author : Gursharanjit Singh
 * Date : 05-04-2018
 * Description : This service provide data regarding wishlist of a user from the mongo database
 * Last-edit : 13-04-2018
 * Latest-edit : Added Hystrix and rabbitMQ
 */
package com.offershopper.wishlistdatabaseservice;

import java.io.IOException;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.xmlpull.v1.XmlPullParserException;

import brave.sampler.Sampler;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@EnableRabbit
@EnableEurekaClient
@EnableFeignClients("com.offershopper.wishlistdatabaseservice")
public class WishlistDatabaseServiceApplication {

  //for rabbit mq	  
  // name of exchange to be created
  public static final String EXCHANGE_NAME = "appExchange";
  // name of generic queue to be created
  public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
  // name of specific to be created
  public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
  // name of routing to be created
  public static final String ROUTING_KEY = "messages.key";
  //this is the main class
  public static void main(String[] args) {
	SpringApplication.run(WishlistDatabaseServiceApplication.class, args);
  }
  @Bean
  public Docket api() throws IOException, XmlPullParserException {    
	return new Docket(DocumentationType.SWAGGER_2);  
  }
  @Bean
  public Sampler defaultSampler() {
    return Sampler.ALWAYS_SAMPLE;
  }
  //for rabbit
  // creating exchange
  @Bean
  public TopicExchange appExchange() {
      return new TopicExchange(EXCHANGE_NAME);
  }  
  // creating generic queue
  @Bean
  public Queue appQueueGeneric() {
      return new Queue(QUEUE_GENERIC_NAME);
  } 
  // creating specific queue
  @Bean
  public Queue appQueueSpecific() {
      return new Queue(QUEUE_SPECIFIC_NAME);
  }    
  // binding generic queue with exchange with a routing key
  @Bean
  public Binding declareBindingGeneric() {
      return BindingBuilder.bind(appQueueGeneric()).to(appExchange()).with(ROUTING_KEY);
  }    
  // binding specific queue with exchange with a routing key
  @Bean
  public Binding declareBindingSpecific() {
      return BindingBuilder.bind(appQueueSpecific()).to(appExchange()).with(ROUTING_KEY);
  }


}
