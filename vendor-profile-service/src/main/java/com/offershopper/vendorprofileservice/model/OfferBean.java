package com.offershopper.vendorprofileservice.model;

import java.time.LocalDateTime;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class OfferBean {

  @Id
  String offerId;

  @Size(min = 2, max = 30)
  String userId;

  String offerTitle;

  // offerImage - doubt

  LocalDateTime offerValidity;

  LocalDateTime dateOfAnnouncement;

  AddressBeanOffer address;
  
  String offerDescription;

  Float originalPrice;

  Float discount;//

  String keyword;
  
  Integer offerRating;

  String offerCategory;

  String offerTerms;
  String imageURL;

  // offerGallery - doubt

  // default constructor
  public OfferBean() {

  }

  public OfferBean(String offerId, @Size(min = 2, max = 30) String userId, String offerTitle,
      LocalDateTime offerValidity, LocalDateTime dateOfAnnouncement, AddressBeanOffer address, String offerDescription,
      Float originalPrice, Float discount, String keyword, Integer offerRating, String offerCategory,
      String offerTerms, String imageURL) {
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
    this.keyword = keyword;
    this.offerRating = offerRating;
    this.offerCategory = offerCategory;
    this.offerTerms = offerTerms;
    this.imageURL= imageURL;
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

  public AddressBeanOffer getAddress() {
    return address;
  }

  public void setAddress(AddressBeanOffer address) {
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

  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }

  public Integer getOfferRating() {
    return offerRating;
  }

  public void setOfferRating(Integer offerRating) {
    this.offerRating = offerRating;
  }

  public String getOfferCategory() {
    return offerCategory;
  }

  public void setOfferCategory(String offerCategory) {
    this.offerCategory = offerCategory;
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


}
