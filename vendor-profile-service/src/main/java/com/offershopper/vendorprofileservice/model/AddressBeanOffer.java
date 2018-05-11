package com.offershopper.vendorprofileservice.model;

public class AddressBeanOffer {

  private String name;
  private String number;
  private String street;
  private String city;
  private String state;
  int zipCode;
  public AddressBeanOffer() {
    super();
    // TODO Auto-generated constructor stub
  }
  public AddressBeanOffer(String name, String number, String street, String city, String state, int zipCode) {
    super();
    this.name = name;
    this.number = number;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
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
