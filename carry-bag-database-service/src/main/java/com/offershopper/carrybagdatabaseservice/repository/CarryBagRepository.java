package com.offershopper.carrybagdatabaseservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.carrybagdatabaseservice.model.CarryBagBean;

public interface CarryBagRepository extends MongoRepository<CarryBagBean, String> {

	List<CarryBagBean> findByUserId(String userId);


	boolean existsByofferId(String id);

}
