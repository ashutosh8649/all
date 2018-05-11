package com.offershopper.uservendoractivityservice.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

public class FeedbackBean {
    @Id
    private String couponId;
    @NotNull
    private String userId;
    @NotNull
    private String offerId;
    @NotNull
    private Float rating;
    private String feedback;
    private boolean vendorValidationFlag;
    private String vendorId;
 

 public FeedbackBean() {
    
    }
    
 public FeedbackBean(String couponId,
        String userId, String offerId,Float rating,String feedback,
        boolean vendorValidationFlag,
        String vendorId
        ) {
        super();
        this.couponId = couponId;
        this.userId = userId;
        this.offerId = offerId;
        this.rating = rating;
        this.feedback=feedback;
        this.vendorValidationFlag=vendorValidationFlag;
        this.vendorId=vendorId;
    }

public String getVendorId() {
   return vendorId;
 }

 public void setVendorId(String vendorId) {
   this.vendorId = vendorId;
 }

 public boolean isVendorValidationFlag() {
   return vendorValidationFlag;
 }

 public void setVendorValidationFlag(boolean vendorValidationFlag) {
   this.vendorValidationFlag = vendorValidationFlag;
 }

 public String getCouponId() {
        return couponId;
    }
 
 
 public void setCouponId(String couponId) {
   this.couponId = couponId;
 }

    public String getUserId() {
        return userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public Float getRating() {
        return rating;
    }

    public String getFeedback() {
        return feedback;
    }
}

