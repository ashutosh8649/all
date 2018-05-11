package com.offershopper.subscribedatabaseservice.database;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.subscribedatabaseservice.model.SubscribeBean;

public interface SubscribeRepository extends MongoRepository<SubscribeBean, String>{
  
  public List<SubscribeBean> findAllByUserId(String userId);
  public List<SubscribeBean> findByVendorId(String vendorId);
  public List<SubscribeBean> findByCategory(String category);
  public Optional<SubscribeBean> findByUserIdAndVendorId(String userId,String vendorId);
  public Optional<SubscribeBean> findByUserIdAndCategory(String userId,String category);


}
