package com.offershopper.vendorprofileservice.model;

public class AddressBean {
  //member variables initialization
  private String number;
  private String street;
  private String city;
  private String state;
  private int zipCode;
    
    //default constructor
  public AddressBean() {
    super();
  }
  //paramerized constructor
  public AddressBean(String number, String street, String city, String state, int zipCode) {
    super();
    this.number = number;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }
  public String getStreet() {
    return street;
  }
  public void setStreet(String street) {
    this.street = street;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getState() {
    return state;
  }
  public void setState(String state) {
    this.state = state;
  }
  public int getZipCode() {
    return zipCode;
  }
  public void setZipCode(int zipCode) {
    this.zipCode = zipCode;
  }
  

    
    
}
