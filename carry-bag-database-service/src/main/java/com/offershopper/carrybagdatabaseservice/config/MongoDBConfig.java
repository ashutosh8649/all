package com.offershopper.carrybagdatabaseservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.offershopper.carrybagdatabaseservice.model.CarryBagBean;
import com.offershopper.carrybagdatabaseservice.repository.CarryBagRepository;

@EnableMongoRepositories(basePackageClasses= CarryBagRepository.class)
@Configuration
public class MongoDBConfig {
	
	@Bean
	CommandLineRunner commandLineRunner(CarryBagRepository carryBagRepository) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				carryBagRepository.save(new CarryBagBean("rakesh_001","offer_001","offer_image","offer_title",405l,365l,"22-05-2018","vendor_001"));
				
			}
		};
	}

}
