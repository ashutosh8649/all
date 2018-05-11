package com.offershopper.uservendoractivityservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.offershopper.uservendoractivityservice.model.UaaBean;

public interface UaaProxyRepository extends MongoRepository<UaaBean, String>
{

}
