package com.offershopper.uservendoractivityservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CarryBagBean
{

  private String userId;

  //primary key is offerId
  private String offerId;

  private String offerImage;

  private String offerTitle;

  private Float offerOriginalPrice;

  private Float offerDiscount;

  private LocalDateTime offerValidity;

  private String vendorId;
  //constructor without field
  public CarryBagBean() 
  {
    super();
  }
  //constructor using parameters
  public CarryBagBean(String userId, String offerId, String offerImage, String offerTitle,
      Float offerOriginalPrice,Float offerDiscount, LocalDateTime offerValidity,
      String vendorId) 
  {
    super();
    this.userId = userId;
    this.offerId = offerId;
    this.offerImage = offerImage;
    this.offerTitle = offerTitle;
    this.offerOriginalPrice=offerOriginalPrice;
    this.offerDiscount=offerDiscount;
    this.offerValidity = offerValidity;
    this.vendorId = vendorId;
  }
  //Getter and setter methods
  public String getUserId() 
  {
    return userId;
  }

  public void setUserId(String userId) 
  {
    this.userId = userId;
  }

  public String getOfferId() 
  {
    return offerId;
  }

  public void setOfferId(String offerId) 
  {
    this.offerId = offerId;
  }

  public String getOfferImage()
  {
    return offerImage;
  }

  public void setOfferImage(String offerImage) 
  {
    this.offerImage = offerImage;
  }

  public String getOfferTitle()
  {
    return offerTitle;
  }

  public Float getOfferOriginalPrice() 
  {
    return offerOriginalPrice;
  }

  public void setOfferOriginalPrice(Float float1) 
  {
    this.offerOriginalPrice = float1;
  }

  public Float getOfferDiscount() 
  {
    return offerDiscount;
  }

  public void setOfferDiscount(Float float1) 
  {
    this.offerDiscount = float1;
  }

  public void setOfferTitle(String offerTitle) 
  {
    this.offerTitle = offerTitle;
  }

  public LocalDateTime getOfferValidity() 
  {
    return offerValidity;
  }

  public void setOfferValidity(LocalDateTime localDateTime)
  {
    this.offerValidity = localDateTime;
  }

  public String getVendorId()
  {
    return vendorId;
  }

  public void setVendorId(String vendorId) 
  {
    this.vendorId = vendorId;
  }

  @Override
  public String toString() 
  {
    return "CarryBagBean [userId=" + userId + ", offerId=" + offerId + ", offerImage=" + offerImage + ", offerTitle="
        + offerTitle + ", offerOriginalPrice=" + offerOriginalPrice + ", offerDiscount=" + offerDiscount
        + ", offerValidity=" + offerValidity + ", vendorId=" + vendorId + "]";
  }



}

