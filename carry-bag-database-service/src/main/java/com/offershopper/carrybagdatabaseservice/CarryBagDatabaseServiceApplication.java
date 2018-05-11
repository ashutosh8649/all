package com.offershopper.carrybagdatabaseservice;

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
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;


import brave.sampler.Sampler;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableRabbit
@EnableSwagger2
@EnableFeignClients("com.offershopper.carrybagdatabaseservice")
public class CarryBagDatabaseServiceApplication {
	
	 //for rabbit mq
	  
	  // name of exchange to be created
	  public static final String EXCHANGE_NAME = "appExchange";
	  // name of generic queue to be created
	  public static final String QUEUE_GENERIC_NAME = "appGenericQueue";
	  // name of specific to be created
	  public static final String QUEUE_SPECIFIC_NAME = "appSpecificQueue";
	  // name of routing to be created
	  public static final String ROUTING_KEY = "messages.key";
	  
	//for sleuth to generate id
		@Bean
	    public Sampler defaultSampler(){
	        return Sampler.ALWAYS_SAMPLE;
		}
		
	  

	public static void main(String[] args) {
		SpringApplication.run(CarryBagDatabaseServiceApplication.class, args);
	}
	
	
//	@Bean
//    public Docket api() throws IOException, XmlPullParserException {
//      MavenXpp3Reader reader = new MavenXpp3Reader();
//       Model model = reader.read(new FileReader("pom.xml"));
//       return new Docket(DocumentationType.SWAGGER_2);              
//  
//    }
	
	
		
	//For swagger to document the Service
	@Bean
	public Docket api() throws IOException, XmlPullParserException {
		return new Docket(DocumentationType.SWAGGER_2);
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
