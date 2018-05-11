package com.offershopper.wishlistdatabaseservice.database;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.wishlistdatabaseservice.model.WishlistBean;

//repository used to get data and save data in the database
public interface WishlistRepository extends MongoRepository<WishlistBean,String>{

  void deleteByOfferId(String offerId);

  void deleteByUserId(String userId);

  boolean existsByUserIdAndOfferId(String userId, String offerId);

  boolean existsByUserId(String userId);

  List<WishlistBean> findByUserId(String userId);

  boolean existsByOfferId(String offerId);

  boolean existsByOfferIdAndUserId(String offerId, String userId);

  void deleteByOfferIdAndUserId(String offerId, String userId);
}
