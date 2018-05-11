package com.offershopper.offerdatabaseservice.model;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

//the document that will be created in the database
@Valid
@Document(collection="offers")
public class OfferBean {
  
  @Id
  String offerId;
  String userId;
  String offerTitle;
  
  
  
  
  LocalDateTime offerValidity;
  LocalDateTime dateOfAnnouncement;
  AddressBean address;
  String offerDescription;
  Float originalPrice;
  Float discount;
  Float offerRating;
  String offerCategories;
  String offerTerms;
  String keywords;
  String imageURL;

  // default constructor
  public OfferBean() {
  }

  //Parameterized constructor
  public OfferBean(String offerId,
	   String userId,
       String offerTitle,
       LocalDateTime offerValidity,
       LocalDateTime dateOfAnnouncement,
       AddressBean address,
       String offerDescription,
       Float originalPrice,
       Float discount,
       Float offerRating,
       String offerCategories,
       String offerTerms,
       String keywords,
       String imageURL) {
    
    super();
    this.offerId = offerId;
    this.userId = userId;
    this.offerTitle = offerTitle;
    this.offerValidity = offerValidity;
    this.dateOfAnnouncement = dateOfAnnouncement;
    this.address = address;
    this.offerDescription = offerDescription;
    this.originalPrice = originalPrice;
    this.discount = discount;
    this.offerRating = offerRating;
    this.offerCategories = offerCategories;
    this.offerTerms = offerTerms;
    this.keywords = keywords;
    this.imageURL = imageURL;
  }

  public String getKeywords() {
	return keywords;
}

public void setKeywords(String keywords) {
	this.keywords = keywords;
}

public String getOfferId() {
    return offerId;
  }

  public void setOfferId(String offerId) {
    this.offerId = offerId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getOfferTitle() {
    return offerTitle;
  }

  public void setOfferTitle(String offerTitle) {
    this.offerTitle = offerTitle;
  }

  public LocalDateTime getOfferValidity() {
    return offerValidity;
  }

  public void setOfferValidity(LocalDateTime offerValidity) {
    this.offerValidity = offerValidity;
  }

  public LocalDateTime getDateOfAnnouncement() {
    return dateOfAnnouncement;
  }

  public void setDateOfAnnouncement(LocalDateTime dateOfAnnouncement) {
    this.dateOfAnnouncement = dateOfAnnouncement;
  }

  public AddressBean getAddress() {
    return address;
  }

  public void setAddress(AddressBean address) {
    this.address = address;
  }

  public String getOfferDescription() {
    return offerDescription;
  }

  public void setOfferDescription(String offerDescription) {
    this.offerDescription = offerDescription;
  }

  public Float getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(Float originalPrice) {
    this.originalPrice = originalPrice;
  }

  public Float getDiscount() {
    return discount;
  }

  public void setDiscount(Float discount) {
    this.discount = discount;
  }

  public Float getOfferRating() {
    return offerRating;
  }

  public void setOfferRating(Float offerRating) {
    this.offerRating = offerRating;
  }

  public String getofferCategories() {
    return offerCategories;
  }

  public void setofferCategories(String offerCategories) {
    this.offerCategories = offerCategories;
  }

  public String getOfferTerms() {
    return offerTerms;
  }

  public void setOfferTerms(String offerTerms) {
    this.offerTerms = offerTerms;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

@Override
public String toString() {
	return "OfferBean [offerId=" + offerId + ", userId=" + userId + ", offerTitle=" + offerTitle + ", offerValidity="
			+ offerValidity + ", dateOfAnnouncement=" + dateOfAnnouncement + ", address=" + address
			+ ", offerDescription=" + offerDescription + ", originalPrice=" + originalPrice + ", discount=" + discount
			+ ", offerRating=" + offerRating + ", offerCategories=" + offerCategories + ", offerTerms=" + offerTerms
			+ ", keywords=" + keywords + ", imageURL=" + imageURL + "]";
}
}
