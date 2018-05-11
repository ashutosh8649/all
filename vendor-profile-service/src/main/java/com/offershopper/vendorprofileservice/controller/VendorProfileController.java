package com.offershopper.vendorprofileservice.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.swing.JOptionPane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.offershopper.vendorprofileservice.service.MessageSender;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.offershopper.vendorprofileservice.feignproxy.CouponAndFeedbackProxy;
import com.offershopper.vendorprofileservice.feignproxy.OfferProxy;
import com.offershopper.vendorprofileservice.feignproxy.UserProxy;
import com.offershopper.vendorprofileservice.model.CouponAndFeedbackBean;
import com.offershopper.vendorprofileservice.model.CouponAndFeedbackModifiedBean;
import com.offershopper.vendorprofileservice.model.OfferBean;
import com.offershopper.vendorprofileservice.model.OfferCouponsAndFeedbacksBean;
import com.offershopper.vendorprofileservice.model.VendorDetailsBean;
import com.offershopper.vendorprofileservice.model.VendorProfileBean;

@RestController
@CrossOrigin
@EnableFeignClients("com.offershopper.vendorprofileservice.controller")
public class VendorProfileController {

  
  private MessageSender sendMessageToRabbit;
  
  private VendorDetailsBean vendorDetails, vendorDetailsFinalBean;
  //private Optional<VendorDetailsBean> vendorDetailsBean=Optional.empty();
  @Autowired
  private UserProxy userproxy;
  @Autowired
  private OfferProxy offerProxy;
  @Autowired
  private CouponAndFeedbackProxy couponAndFeedbackProxy;
  public VendorProfileBean vendorProfileBean;////
  private OfferCouponsAndFeedbacksBean offerCouponsAndFeedbacksSingleBean;//
  private List<OfferCouponsAndFeedbacksBean> offerCouponsAndFeedbacksBeanList=new ArrayList<OfferCouponsAndFeedbacksBean>();
  private OfferBean offerBean;
  private CouponAndFeedbackBean couponAndFeedbackSingleBean;
  private CouponAndFeedbackModifiedBean couponsAndFeedbackModifiedSingleBean;//
  private List<CouponAndFeedbackModifiedBean> couponsAndFeedbackModifiedBeanList=new ArrayList<CouponAndFeedbackModifiedBean>();
  
  
  //finds profile of vendor by id
  @HystrixCommand(fallbackMethod = "getFallback")
  @GetMapping("/vendor-profile/{email}")
  public VendorProfileBean seeProfile(@PathVariable String email) {
    System.out.println("before user");
    ResponseEntity<Optional<VendorDetailsBean>> vendorDetails = userproxy.findUserByEmail(email); 
    System.out.println("after user");
    System.out.println(vendorDetails);
    //vendorDetailsBean=vendorDetails.getBody();
    System.out.println("Before offer");
    System.out.println(email);
    ResponseEntity<List<OfferBean>> responseOfferBeans=offerProxy.getOffers(email);
    System.out.println(responseOfferBeans);

    List<OfferBean> offerBeans=responseOfferBeans.getBody();//check use of iterator in list??
    System.out.println(offerBeans);

    Iterator<OfferBean> it=offerBeans.iterator();
    
    while(it.hasNext()) {
      
      offerBean=it.next();
      
      if(offerBean.getUserId().equals(email)) {
        
        String offerId=offerBean.getOfferId();
        System.out.println(offerId);

        System.out.println("Before coupon");
       
        ResponseEntity<List<CouponAndFeedbackBean>> couponAndFeedbackBeans=couponAndFeedbackProxy.getFeedback(offerId);

        System.out.println("After- coupon");

        List<CouponAndFeedbackBean> couponAndFeedbackList=couponAndFeedbackBeans.getBody();
        Iterator<CouponAndFeedbackBean> iterator=couponAndFeedbackList.iterator();

        while(iterator.hasNext()) {
          couponAndFeedbackSingleBean=iterator.next();
          System.out.println(couponAndFeedbackSingleBean.getFeedback());

          couponsAndFeedbackModifiedSingleBean=new CouponAndFeedbackModifiedBean(couponAndFeedbackSingleBean.getCouponId(),
                                                                                  couponAndFeedbackSingleBean.getFeedback());
          couponsAndFeedbackModifiedBeanList.add(couponsAndFeedbackModifiedSingleBean);
          System.out.println("after constructor");


        }
          

        offerCouponsAndFeedbacksSingleBean = new OfferCouponsAndFeedbacksBean(offerId, offerBean.getOfferTitle(),
                                                                      offerBean.getOfferValidity(),
                                                                      offerBean.getDateOfAnnouncement(),
                                                                      offerBean.getOfferDescription(), 
                                                                      offerBean.getOriginalPrice(),
                                                                      offerBean.getDiscount(),
                                                                      offerBean.getOfferRating(),
                                                                      offerBean.getOfferCategory(), 
                                                                      offerBean.getOfferTerms(),
                                                                      couponsAndFeedbackModifiedBeanList);
                                                                      
      }
      offerCouponsAndFeedbacksBeanList.add(offerCouponsAndFeedbacksSingleBean);
      System.out.println("after offerCouponsAndFeedbacksBeanList");
    }
    
    if(vendorDetails.getBody().isPresent()) {

      vendorDetailsFinalBean=vendorDetails.getBody().get();

      vendorProfileBean= new VendorProfileBean( vendorDetailsFinalBean.getFirstName(),
                                                vendorDetailsFinalBean.getLastName(), 
                                                vendorDetailsFinalBean.getEmail(),
                                                vendorDetailsFinalBean.getShopAddress(), 
                                                vendorDetailsFinalBean.getVendorMobileNo(),
                                                offerCouponsAndFeedbacksBeanList); 
System.out.println("hvgdhafbkj");
    }
  
    
    return vendorProfileBean;
  
 }
  public VendorProfileBean getFallback(@PathVariable String email) {
    String msg = "Exception occured, inside fallback";
    System.out.println(msg);
    //sendMessageToRabbit.produceMsg(msg);
    return null;
    
  }
  
 /* @HystrixCommand(fallbackMethod = "fallback")
  @GetMapping("offer/{email}")
  public List<OfferCouponsAndFeedbacksBean> getOfferList(@PathVariable String email){
    ResponseEntity<List<OfferBean>> responseOfferBeans=offerProxy.getOffers(email);
    List<OfferBean> offerBeans=responseOfferBeans.getBody();//check use of iterator in list??
    Iterator<OfferBean> it=offerBeans.iterator();
    
    while(it.hasNext()) {
      
      offerBean=it.next();
      JOptionPane.showMessageDialog(null,offerBean.getOfferCategory());
      
      if(offerBean.getUserId().equals(email)) {
        
        String offerId=offerBean.getOfferId();
       
        ResponseEntity<List<CouponAndFeedbackBean>> couponAndFeedbackBeans=couponAndFeedbackProxy.getFeedback(offerId);
        List<CouponAndFeedbackBean> couponAndFeedbackList=couponAndFeedbackBeans.getBody();
        Iterator<CouponAndFeedbackBean> iterator=couponAndFeedbackList.iterator();
        
        while(iterator.hasNext()) {
          couponAndFeedbackSingleBean=iterator.next();
          
          couponsAndFeedbackModifiedSingleBean=new CouponAndFeedbackModifiedBean(couponAndFeedbackSingleBean.getCouponId(),
                                                                                  couponAndFeedbackSingleBean.getFeedback());
          couponsAndFeedbackModifiedBeanList.add(couponsAndFeedbackModifiedSingleBean);
        }
          

        offerCouponsAndFeedbacksSingleBean = new OfferCouponsAndFeedbacksBean(offerId, offerBean.getOfferTitle(),
                                                                      offerBean.getOfferValidity(),
                                                                      offerBean.getDateOfAnnouncement(),
                                                                      offerBean.getOfferDescription(), 
                                                                      offerBean.getOriginalPrice(),
                                                                      offerBean.getDiscount(),
                                                                      offerBean.getOfferRating(),
                                                                      offerBean.getOfferCategory(), 
                                                                      offerBean.getOfferTerms(),
                                                                      couponsAndFeedbackModifiedBeanList);
      }
      offerCouponsAndFeedbacksBeanList.add(offerCouponsAndFeedbacksSingleBean);
    }
    return offerCouponsAndFeedbacksBeanList;
  }
  
  public VendorProfileBean fallback(@PathVariable String email) {
    String msg = "Exception occured, inside fallback";
    System.out.println(msg);
    //sendMessageToRabbit.produceMsg(msg);
    return null;
    
  }
  */
  
}
