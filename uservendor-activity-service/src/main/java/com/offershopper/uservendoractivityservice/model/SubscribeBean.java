package com.offershopper.uservendoractivityservice.model;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class SubscribeBean {
  @Id
  @Generated(value = { })
  private String id;
  
  private String userId;
  private String vendorId;
  private String shopName;

  //default constructor
  public SubscribeBean() {
    super();
  }

  //Parameterized constructor
  public SubscribeBean(String id,String userId, String vendorId, String shopName) 
  {
    super();
    this.id = id;
    this.userId = userId;
    this.vendorId = vendorId;
    this.shopName = shopName;
  }
  
  //Getters and Setters
  public String getId() 
  {
    return id;
  }

  public void setId(String id) 
  {
    this.id = id;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId) 
  {
    this.userId = userId;
  }

  public String getVendorId()
  {
    return vendorId;
  }

  public void setVendorId(String vendorId) 
  {
    this.vendorId = vendorId;
  }

  public String getshopName() 
  {
    return shopName;
  }

  public void shopName(String shopName)
  {
    this.shopName = shopName;
  }

  @Override
  public String toString() {
    return "SubscribeBean [userId=" + userId + ", vendorId=" + vendorId + ", shopName=" + shopName + "]";
  }




}
