package com.offershopper.vendorprofileservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

public class VendorDetailsBean {
   

  private String firstName;
  private String lastName;
  private String password;
  private String role;
  private String mobileNo;
  
  @Id
  private String email;
  private String dob;
  private AddressBean address;
  private String gender;
  private int spinCount;
  private int osCash;
  private AddressBean shopAddress;
  private String vendorMobileNo;
  private List<String> offerIdList = new ArrayList<String>();//??
  private String timestamp;
  
  //default constructor
public VendorDetailsBean() {
  super();  
}
//parameterized constructor

public VendorDetailsBean(String firstName, String lastName, String password, String role, String mobileNo, String email,
    String dob, AddressBean address, String gender, int spinCount, int osCash, AddressBean shopAddress,
    String vendorMobileNo, List<String> offerIdList, String timestamp) {
  super();
  this.firstName = firstName;
  this.lastName = lastName;
  this.password = password;
  this.role = role;
  this.mobileNo = mobileNo;
  this.email = email;
  this.dob = dob;
  this.address = address;
  this.gender = gender;
  this.spinCount = spinCount;
  this.osCash = osCash;
  this.shopAddress = shopAddress;
  this.vendorMobileNo = vendorMobileNo;
  this.offerIdList = offerIdList;
  this.timestamp = timestamp;
}

public String getFirstName() {
  return firstName;
}

public void setFirstName(String firstName) {
  this.firstName = firstName;
}

public String getLastName() {
  return lastName;
}

public void setLastName(String lastName) {
  this.lastName = lastName;
}

public String getPassword() {
  return password;
}

public void setPassword(String password) {
  this.password = password;
}

public String getRole() {
  return role;
}

public void setRole(String role) {
  this.role = role;
}

public String getMobileNo() {
  return mobileNo;
}

public void setMobileNo(String mobileNo) {
  this.mobileNo = mobileNo;
}

public String getEmail() {
  return email;
}

public void setEmail(String email) {
  this.email = email;
}

public String getDob() {
  return dob;
}

public void setDob(String dob) {
  this.dob = dob;
}

public AddressBean getAddress() {
  return address;
}

public void setAddress(AddressBean address) {
  this.address = address;
}

public String getGender() {
  return gender;
}

public void setGender(String gender) {
  this.gender = gender;
}

public int getSpinCount() {
  return spinCount;
}

public void setSpinCount(int spinCount) {
  this.spinCount = spinCount;
}

public int getOsCash() {
  return osCash;
}

public void setOsCash(int osCash) {
  this.osCash = osCash;
}

public AddressBean getShopAddress() {
  return shopAddress;
}

public void setShopAddress(AddressBean shopAddress) {
  this.shopAddress = shopAddress;
}

public String getVendorMobileNo() {
  return vendorMobileNo;
}

public void setVendorMobileNo(String vendorMobileNo) {
  this.vendorMobileNo = vendorMobileNo;
}

public List<String> getOfferIdList() {
  return offerIdList;
}

public void setOfferIdList(List<String> offerIdList) {
  this.offerIdList = offerIdList;
}

public String getTimestamp() {
  return timestamp;
}

public void setTimestamp(String timestamp) {
  this.timestamp = timestamp;
}

}
