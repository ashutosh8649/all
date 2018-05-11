package com.offershopper.uaa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class tokenBean {
	@Id
	private String jwe;
	private String email;
	
	public tokenBean() {
		super();

	}
	
	
	
	public tokenBean(String jwe, String email) {
		super();
		this.jwe = jwe;
		this.email = email;
	}
	public String getJwe() {
		return jwe;
	}
	public void setJwe(String jwe) {
		this.jwe = jwe;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
