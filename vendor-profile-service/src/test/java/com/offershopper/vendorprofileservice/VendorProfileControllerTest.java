/*package com.offershopper.vendorprofileservice;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.offershopper.vendorprofileservice.feignproxy.CouponAndFeedbackProxy;
import com.offershopper.vendorprofileservice.feignproxy.OfferProxy;
import com.offershopper.vendorprofileservice.feignproxy.UserProxy;
import com.offershopper.vendorprofileservice.model.AddressBean;
import com.offershopper.vendorprofileservice.model.CouponAndFeedbackBean;
import com.offershopper.vendorprofileservice.model.CouponAndFeedbackModifiedBean;
import com.offershopper.vendorprofileservice.model.OfferBean;
import com.offershopper.vendorprofileservice.model.OfferCouponsAndFeedbacksBean;
import com.offershopper.vendorprofileservice.model.VendorDetailsBean;
import com.offershopper.vendorprofileservice.model.VendorProfileBean;


@RunWith(SpringRunner.class)
@WebMvcTest
public class VendorProfileControllerTest {
  
  @Autowired
  MockMvc mockMvc;
  
  @MockBean
  UserProxy userproxy;
  @MockBean
  OfferProxy offerProxy;
  @MockBean
  CouponAndFeedbackProxy couponAndFeedbackProxy;
  @MockBean
  VendorProfileBean vendorProfileBean;
  @MockBean
  OfferCouponsAndFeedbacksBean offersAndFeedbackSingleBean;
  @MockBean
  OfferCouponsAndFeedbacksBean offersAndFeedbackBean;
  @MockBean
  OfferBean offerBean;
  @MockBean
  CouponAndFeedbackBean couponAndFeedbackSingleBean;
  @MockBean
  CouponAndFeedbackModifiedBean couponsAndFeedbackModifiedSingleBean;
  @MockBean
  CouponAndFeedbackModifiedBean couponAndFeedbackModified;
  //add messageSender also
  
  
  AddressBean addressBean;
  
  VendorDetailsBean vendorDetailsBean;
  ResponseEntity<Optional<VendorDetailsBean>> vendorDetailsresponseEntity;

  OfferBean singleofferBean;
  List<OfferBean> offerBeansList;
  ResponseEntity<List<OfferBean>> offerBeansListResponseEntity;
  
  CouponAndFeedbackBean couponAndFeedbackBean;
  List<CouponAndFeedbackBean> couponAndFeedbackBeanList;
  ResponseEntity<List<CouponAndFeedbackBean>> couponAndFeedbackBeansResponseEntity;
  
  

  
  @Before
  public void setup() {

    addressBean= new AddressBean("street","city","state",123);
    vendorDetailsBean= new VendorDetailsBean("id","fname","lname","password","mobile","email",
        "dob",addressBean,"gender",1,2,addressBean,"vm","ts");
    Optional option=Optional.of(vendorDetailsBean);
    vendorDetailsresponseEntity=ResponseEntity.status(HttpStatus.OK).body(option);
    
    singleofferBean=new OfferBean("oi","ui","ot",LocalDateTime.of(2018,Month.APRIL,17,11,33),
        LocalDateTime.of(2018,Month.APRIL,17,11,33),addressBean,"od",2.0f,2.0f,2,"oc","od");
    
    offerBeansList.add(offerBean);
    offerBeansListResponseEntity= ResponseEntity.status(HttpStatus.OK).body(offerBeansList);
    
    couponAndFeedbackBean= new CouponAndFeedbackBean("couponId","userId","offerId",2,"feedback");
    couponAndFeedbackBeanList.add(couponAndFeedbackBean);
    couponAndFeedbackBeansResponseEntity= ResponseEntity.status(HttpStatus.OK).body(couponAndFeedbackBeanList);
  }
  
  @Test
  public void testSeeProfile() {

    Mockito.when(userproxy.findUserByEmail(Mockito.anyString())).thenReturn(vendorDetailsresponseEntity);
    Mockito.when(offerProxy.getOffers(Mockito.anyString())).thenReturn(offerBeansListResponseEntity);
    Mockito.when(couponAndFeedbackProxy.getFeedback(Mockito.anyString())).thenReturn(couponAndFeedbackBeansResponseEntity);

    
  }


}
*/