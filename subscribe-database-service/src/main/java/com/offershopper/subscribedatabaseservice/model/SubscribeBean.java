package com.offershopper.subscribedatabaseservice.model;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SubscribeBean {


  @Id
  @Generated(value = { })
  private String id;
  
	private String userId;

	private String vendorId;
	private String category;
	
	//default constructor
	
	public SubscribeBean() {
		super();
	}

  public SubscribeBean(String id, String userId, String vendorId, String category) {
    super();
    this.id = id;
    this.userId = userId;
    this.vendorId = vendorId;
    this.category = category;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }
	
	
	
}
