package com.offershopper.uservendoractivityservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//document created when storing data regarding wishlist of a user
@Document
public class WishlistBean 
{
  //member variables initialization
  @Id
  private String id;
  private String userId;
  private String offerId;
  private String offerTitle;
  private Float offerOriginalPrice;
  private Float offerDiscount;
  private String offerImage;
  private LocalDateTime offerValidity;
  public String getId() 
  {
    return id;
  }
  // default constructor
  public WishlistBean()
  {
    super();
  }
  //getters and setters
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

  public String getOfferId() 
  {
    return offerId;
  }

  public void setOfferId(String offerId) 
  {
    this.offerId = offerId;
  }

  public String getOfferTitle() 
  {
    return offerTitle;
  }

  public void setOfferTitle(String offerTitle) 
  {
    this.offerTitle = offerTitle;
  }

  public Float getOfferOriginalPrice()
  {
    return offerOriginalPrice;
  }

  public void setOfferOriginalPrice(Float offerOriginalPrice)
  {
    this.offerOriginalPrice = offerOriginalPrice;
  }

  public Float getOfferDiscount()
  {
    return offerDiscount;
  }

  public void setOfferDiscount(Float offerDiscount) 
  {
    this.offerDiscount = offerDiscount;
  }

  public String getOfferImage() 
  {
    return offerImage;
  }

  public void setOfferImage(String offerImage) 
  {
    this.offerImage = offerImage;
  }
  public LocalDateTime getOfferValidity()
  {
    return offerValidity;
  }

  public void setOfferValidity(LocalDateTime offerValidity) 
  {
    this.offerValidity = offerValidity;
  }

  //Parameterized constructor
  public WishlistBean(String id, String userId, String offerId, String offerTitle, Float offerOriginalPrice,
      Float offerDiscount, String offerImage, LocalDateTime offerValidity)
  {
    super();
    this.id = id;
    this.userId = userId;
    this.offerId = offerId;
    this.offerTitle = offerTitle;
    this.offerOriginalPrice = offerOriginalPrice;
    this.offerDiscount = offerDiscount;
    this.offerImage = offerImage;
    this.offerValidity = offerValidity;
  }

  @Override
  public String toString() 
  {
    return "WishlistBean [id=" + id + ", userId=" + userId + ", offerId=" + offerId + ", offerTitle=" + offerTitle
        + ", offerOriginalPrice=" + offerOriginalPrice + ", offerDiscount=" + offerDiscount + ", offerImage="
        + offerImage + ", offerValidity=" + offerValidity + "]";
  }
}
