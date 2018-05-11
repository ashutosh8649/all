/*
	 * Name: UserRepository
	 * Date: 11-April-2018
	 * Description: The mongo repository
	*/
package com.offershopper.referraldatabaseservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.referraldatabaseservice.model.UserBean;

public interface UserRepository extends MongoRepository<UserBean, String>  {

	UserBean findByEmail(String email1);
}
