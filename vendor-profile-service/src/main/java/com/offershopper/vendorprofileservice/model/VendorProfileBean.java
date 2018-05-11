package com.offershopper.vendorprofileservice.model;

import java.util.ArrayList;
import java.util.List;

public class VendorProfileBean {

  private String firstName;
  private String lastName;
  private String email;
  private AddressBean shopAddress;
  private String vendorMobileNo;
  List<OfferCouponsAndFeedbacksBean> offerCouponsAndFeedbacksBeanList=new ArrayList<OfferCouponsAndFeedbacksBean>();
  
  
  public VendorProfileBean() {
    super();
    // TODO Auto-generated constructor stub
  }


  public VendorProfileBean(String firstName, String lastName, String email, AddressBean shopAddress,
      String vendorMobileNo, List<OfferCouponsAndFeedbacksBean> offerCouponsAndFeedbacksBeanList) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.shopAddress = shopAddress;
    this.vendorMobileNo = vendorMobileNo;
    this.offerCouponsAndFeedbacksBeanList = offerCouponsAndFeedbacksBeanList;
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


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
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


  public List<OfferCouponsAndFeedbacksBean> getOfferCouponsAndFeedbacksBeanList() {
    return offerCouponsAndFeedbacksBeanList;
  }


  public void setOfferCouponsAndFeedbacksBeanList(List<OfferCouponsAndFeedbacksBean> offerCouponsAndFeedbacksBeanList) {
    this.offerCouponsAndFeedbacksBeanList = offerCouponsAndFeedbacksBeanList;
  }


  
  
}
