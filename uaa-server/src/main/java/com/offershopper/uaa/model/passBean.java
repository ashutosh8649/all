package com.offershopper.uaa.model;

public class passBean {
	
	private String jwe;
	private String password;
	
	
	
	public passBean() {
		super();
	}
	
	public passBean(String jwe, String password) {
		this.jwe = jwe;
		this.password = password;
	}
	public String getJwe() {
		return jwe;
	}
	public void setJwe(String jwe) {
		this.jwe = jwe;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
