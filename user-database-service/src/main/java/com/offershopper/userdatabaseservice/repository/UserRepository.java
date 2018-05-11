package com.offershopper.userdatabaseservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.userdatabaseservice.model.UserBean;


public interface UserRepository extends MongoRepository<UserBean, String>  {

	Optional<UserBean> findByMobileNo(String mobile);

	Optional<UserBean> findByVendorMobileNo(String vendorMobileNo);

	Optional<UserBean> findByEmail(String email);
}
