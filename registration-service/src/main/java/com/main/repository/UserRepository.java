package com.main.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.main.modal.RegisterInfo;

public interface UserRepository extends MongoRepository<RegisterInfo, String> {

	@SuppressWarnings("unchecked")
	public RegisterInfo save(RegisterInfo dataOfUserInBody);

}
