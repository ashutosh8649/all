package com.offershopper.uaa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usersession_1")
public class UaaModel {
	
	@Id
	private String userId;
	private String token ; 
	private String publicKey;
	private int activeUser;
	
	public UaaModel() {
		super();
	}

	public UaaModel(String userId, String token, String publicKey, int activeUser) {
		super();
		this.userId = userId;
		this.token = token;
		this.publicKey = publicKey;
		this.activeUser = activeUser;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPublicKey() {
		return publicKey;
	}
	
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	
	public int getActiveUser() {
		return activeUser;
	}
	
	public void setActiveUser(int activeUser) {
		this.activeUser = activeUser;
	}

}
