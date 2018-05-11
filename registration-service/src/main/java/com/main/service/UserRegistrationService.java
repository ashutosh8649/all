package com.main.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.main.exception.UserNotFountException;
import com.main.modal.RegisterInfo;
import com.main.repository.UserRepository;

@Service
public class UserRegistrationService {

	@Autowired
	UserRepository userRepository;

	public List<RegisterInfo> getAllRegisteredUserInformation() {
		List<RegisterInfo> allUsersFound = new ArrayList<>();
		userRepository.findAll().forEach(allUsersFound::add);
		return allUsersFound;
	}

	public Optional<RegisterInfo> getUserInformation(String id) {
		Optional<RegisterInfo> userFound = userRepository.findById(id);
		if (!userFound.isPresent()) {
			throw new UserNotFountException("id-" + id);
		}
		return userFound;
	}

	public void addUserInformation(RegisterInfo dataOfUserInBody) {
		userRepository.save(dataOfUserInBody);

	}

	public void modifyUserInformation(RegisterInfo dataOfUserInBody) {
		userRepository.save(dataOfUserInBody);

	}

	public void deleteUserInformation(String id) {
		Optional<RegisterInfo> userFound = userRepository.findById(id);
		if (!userFound.isPresent()) {
			throw new UserNotFountException("id-" + id);
		}
		userRepository.deleteById(id);
	}

}
