package com.offershopper.uaa.database;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.uaa.model.tokenBean;

public interface tokenRepo extends MongoRepository<tokenBean, String> {

}
