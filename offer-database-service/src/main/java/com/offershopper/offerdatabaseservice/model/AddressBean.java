package com.offershopper.offerdatabaseservice.model;

public class AddressBean {
  // contains the house number, street etc
  String name;
  String number;
  String street;
  String city;
  String state;
  int zipCode;

  // default constructor
  public AddressBean() {

  }

  // Parameterized constructor
  public AddressBean(String name, String number, String street, String city, String state, int zipCode) {
    super();
    this.name = name;
    this.number = number;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
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
  
  

}
