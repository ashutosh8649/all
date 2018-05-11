package com.offershopper.offerdatabaseservice.database;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.offershopper.offerdatabaseservice.model.OfferBean;

//repository used to use save,findOne, findAll methods
public interface OfferRepository extends MongoRepository<OfferBean, String> {
  public List<OfferBean> findByUserId(String userId);
  
  @Query("{'address.city': ?0}")
  public List<OfferBean> findByCity(String city);
}
