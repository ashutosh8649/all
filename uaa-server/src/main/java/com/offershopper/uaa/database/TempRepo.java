package com.offershopper.uaa.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.uaa.model.RegisterInfo;

public interface TempRepo extends MongoRepository<RegisterInfo, String>{

}
