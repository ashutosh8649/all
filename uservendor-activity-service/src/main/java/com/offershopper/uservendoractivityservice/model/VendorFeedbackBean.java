package com.offershopper.uservendoractivityservice.model;

import java.time.LocalDateTime;

public class VendorFeedbackBean {
  private Float rating;
  private String feedback;
  private String offerTitle;
  private LocalDateTime offerValidity;
  private String offerDescription;
  private Float originalPrice;
  private Float discount;
  private String offerCategory;
  private String offerTerms;
  private String keywords;
  private String imageURL;
  
  public VendorFeedbackBean() {

  }
  
  public VendorFeedbackBean(Float rating, String feedback, String offerTitle, LocalDateTime offerValidity,
      String offerDescription, Float originalPrice, Float discount, Float offerRating, String offerCategory,
      String offerTerms, String keywords, String imageURL) {
    super();
    this.rating = rating;
    this.feedback = feedback;
    this.offerTitle = offerTitle;
    this.offerValidity = offerValidity;
    this.offerDescription = offerDescription;
    this.originalPrice = originalPrice;
    this.discount = discount;
    this.offerCategory = offerCategory;
    this.offerTerms = offerTerms;
    this.keywords = keywords;
    this.imageURL = imageURL;
  }
  public Float getRating() {
    return rating;
  }
  public void setRating(Float rating) {
    this.rating = rating;
  }
  public String getFeedback() {
    return feedback;
  }
  public void setFeedback(String feedback) {
    this.feedback = feedback;
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
  public String getKeywords() {
    return keywords;
  }
  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }
  public String getImageURL() {
    return imageURL;
  }
  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  @Override
  public String toString() {
    return "VendorFeedbackBean [rating=" + rating + ", feedback=" + feedback + ", offerTitle=" + offerTitle
        + ", offerValidity=" + offerValidity + ", offerDescription=" + offerDescription + ", originalPrice="
        + originalPrice + ", discount=" + discount + ", offerCategory=" + offerCategory
        + ", offerTerms=" + offerTerms + ", keywords=" + keywords + ", imageURL=" + imageURL + "]";
  }
  
  
}
