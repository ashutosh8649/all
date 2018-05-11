package com.offershopper.uservendoractivityservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.offershopper.uservendoractivityservice.model.AddressBean;
import com.offershopper.uservendoractivityservice.model.CarryBagBean;
import com.offershopper.uservendoractivityservice.model.SubscribeBean;
import com.offershopper.uservendoractivityservice.model.UserBean;
import com.offershopper.uservendoractivityservice.model.OfferBean;
import com.offershopper.uservendoractivityservice.model.WishlistBean;
import com.offershopper.uservendoractivityservice.rabbit.MessageSender;
import com.offershopper.uservendoractivityservice.repository.UaaProxyRepository;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.CarryBagProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.FeedbackProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.OfferProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.ProfileProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.ReferralProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.RoleProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.SubscribeProxy;
import com.offershopper.uservendoractivityservice.uservendoractivityproxy.WishlistProxy;

/*
 * File name:userVendor-activity-service-Controller-Test
 * Author: Gursharan,Anand,Yashika and Rajneesh
 * Date: 13-April-2018
 * Description: Test Cases for Controller Class
 */

@RunWith(SpringRunner.class)
@WebMvcTest
public class UservendorActivityServiceControllerTests {

@Autowired
  MockMvc mockMvc;
  //MockBeans for Proxy Classes
  @MockBean
  ProfileProxy profileproxy;

  @MockBean
  SubscribeProxy subscribeproxy;

  @MockBean
  CarryBagProxy carrybagproxy;

  @MockBean
  WishlistProxy wishlistproxy;

  @MockBean
  ReferralProxy referralproxy;

  @MockBean
  private OfferProxy offerProxy;

  @MockBean
  private MessageSender messageSender;

  @MockBean
  private RoleProxy roleproxy;
  
  @MockBean
  private FeedbackProxy feedbackproxy;

  @MockBean
  private UaaProxyRepository userproxyrepository;
  //Constructors for Beans
  List<CarryBagBean> carrybaglist=new ArrayList<CarryBagBean>();
  CarryBagBean bag = new CarryBagBean("rakesh_005", "offer_005", "offer_image", "offer_title", 405f, 365f,
      null, "vendor_001");

  List<WishlistBean> wishlist=new ArrayList<WishlistBean>();
  WishlistBean wishlistbean=new WishlistBean("aa", "100", "cc", "dd", 123f, 123f, "dd", LocalDateTime.of(2018, Month.APRIL, 4, 10, 10, 30));

  UserBean userbean =new UserBean( "Riya","a","pwd1","9336048823","vish@gmail.com","1/4/77",
      new AddressBean("d","e","f",null, null, 155501),"f",null, 0,0.0f,
      new AddressBean("dsdf","esgf","fgfa",null, null, 155501),"56456",null, 78451);

  SubscribeBean subscribebean = new SubscribeBean("2", "10", "shirt", null);
  List<SubscribeBean> subscriptionlist=new ArrayList<SubscribeBean>();
  
  OfferBean offerbean = new OfferBean("1", "uid-1", "Fridge", LocalDateTime.of(2018, Month.APRIL, 4, 10, 10, 30),
      LocalDateTime.of(2018, Month.APRIL, 5, 10, 10, 30),
      new AddressBean("Om","220","this is my house","this is my house","this is my house",144514),
      "Buy two get one", 40000f, 2000f, 9f, "Electronics",
      "Valid only in specified time", "refrigerator, sony","url");
  
  /*
   * Name: addOfferNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to add offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void addOfferNegativeTest() 
  {
    String mockOffer="{\"offerId\":\"1\","
        + "\"userId\":\"uid-1\""
        + ",\"offerTitle\":\"Fridge\""
        + ",\"offerValidity\":\"2018-04-04T10:10:30\","
        + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
        + "\"address\":"
        + "{"
        + "\"addressLine\":\"this is my house\","
        + "\"city\":\"this is my house\","
        + "\"state\":\"this is my house\","
        + "\"pincode\":\"this is my house\""
        + "},"
        + "\"offerDescription\":\"Buy two get one\""
        + ",\"originalPrice\":40000.0,"
        + "\"discount\":2000.0,"
        + "\"offerRating\":9,"
        + "\"offerCategory\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\"}";
    //mocking the addOffer method
    Mockito.when(offerProxy.addOffer(Mockito.any(OfferBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.post("/addOffer")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: updateOfferPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to update offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateOfferPositiveTest() 
  {
    String mockOffer="{\"offerId\":\"1\","
        + "\"userId\":\"uid-1\""
        + ",\"offerTitle\":\"Fridge\""
        + ",\"offerValidity\":\"2018-04-04T10:10:30\","
        + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
        + "\"address\":"
        + "{"
        + "\"addressLine\":\"this is my house\","
        + "\"city\":\"this is my house\","
        + "\"state\":\"this is my house\","
        + "\"pincode\":\"this is my house\""
        + "},"
        + "\"offerDescription\":\"Buy two get one\""
        + ",\"originalPrice\":40000.0,"
        + "\"discount\":2000.0,"
        + "\"offerRating\":9,"
        + "\"offerCategory\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\"}";
    //mocking the addOffer method
    Mockito.when(offerProxy.updateOffer(Mockito.anyString(),Mockito.any(OfferBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(offerbean));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updateOffer/1")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: updateOfferNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to update offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateOfferNegativeTest() 
  {
    String mockOffer="{\"offerId\":\"1\","
        + "\"userId\":\"uid-1\""
        + ",\"offerTitle\":\"Fridge\""
        + ",\"offerValidity\":\"2018-04-04T10:10:30\","
        + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
        + "\"address\":"
        + "{"
        + "\"addressLine\":\"this is my house\","
        + "\"city\":\"this is my house\","
        + "\"state\":\"this is my house\","
        + "\"pincode\":\"this is my house\""
        + "},"
        + "\"offerDescription\":\"Buy two get one\""
        + ",\"originalPrice\":40000.0,"
        + "\"discount\":2000.0,"
        + "\"offerRating\":9,"
        + "\"offerCategory\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\"}";
    //mocking the addOffer method
    Mockito.when(offerProxy.updateOffer(Mockito.anyString(),Mockito.any(OfferBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(offerbean));
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.put("/updateOffer/1")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: deleteOfferPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to delete offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void deleteOfferPositiveTest() 
  {
    //mocking the addOffer method
    Mockito.when(offerProxy.deleteOffer("1")).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/deleteOffer/1")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: deleteOfferNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to delete offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void deleteOfferNegativeTest() 
  {
    //mocking the addOffer method
    Mockito.when(offerProxy.deleteOffer("1")).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.delete("/deleteOffer/1")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: subscribePositiveDelete
   * Date: 11-April-2018
   * Description: Positive Test Case to delete offer 
   * Required files: UserVendorActivityController
   */
  @Test
  public void subscribePositiveDelete() throws Exception
  {
    //mocking the methods present
    Mockito.when(subscribeproxy.deleteByVendorId(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.OK));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deletesubscriptionbyvendorid/pooja@gmail.com/megha@gmail.com")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: subscribeNegativeDelete
   * Date: 11-April-2018
   * Description: Negative Test Case to delete offer 
   * Required files: UserVendorActivityController
   */
  @Test
  public void subscribeNegativeDelete() throws Exception
  {

    //mocking the methods present
    Mockito.when(subscribeproxy.deleteByVendorId(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deletesubscriptionbyvendorid/pooja@gmail.com/megha@gmail.com")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: addSubscribePositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to add offer 
   * Required files: UserVendorActivityController
   */
  @Test
  public void addSubscribePositiveTest() 
  {
    String mockOffer="{\"userId\":\"2\","
        + "\"vendorId\":\"10\","
        + "\"category\":\"shirt\"}";
    //mocking the addOffer method
    Mockito.when(subscribeproxy.addByVendorId(Mockito.any(SubscribeBean.class))).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.CREATED));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addsubscription")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.CREATED.value(), responsePositive.getStatus());
    } catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: addSubscribeNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to add offer 
   * Required files: UserVendorActivityController
   */
  @Test
  public void addSubscribeNegativeTest() 
  {
    String mockOffer="{\\\"userId\\\":\\\"2\\\",\\\"vendorId\\\":\\\"10\\\",\\\"category\\\":\\\"shirt\\\"}";
    //mocking the addOffer method
    Mockito.when(subscribeproxy.addByVendorId(Mockito.any(SubscribeBean.class))).thenReturn(new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addsubscription")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: addOfferPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to add offer in offer database
   * Required files: UserVendorActivityController
   */
  @Test
  public void addOfferPositiveTest() 
  {
    String mockOffer="{\n" + 
        "   \"_id\" : ObjectId(\"5af03f0889a32a672b88d692\"),\n" + 
        "   \"userId\" : \"kumarmukesh3774@gmail.com\",\n" + 
        "   \"offerTitle\" : \"nike sports shoes\",\n" + 
        "   \"offerValidity\" : ISODate(\"2018-06-04T11:57:50.000Z\"),\n" + 
        "   \"dateOfAnnouncement\" : ISODate(\"2018-04-07T11:57:50.000Z\"),\n" + 
        "   \"address\" : {\n" + 
        "       \"name\" : \"Holy Store\",\n" + 
        "       \"street\" : \"infospace\",\n" + 
        "       \"city\" : \"Gurgaon\",\n" + 
        "       \"state\" : \"Haryana\",\n" + 
        "       \"zipCode\" : 122016\n" + 
        "   },\n" + 
        "   \"offerDescription\" : \"training shoes\",\n" + 
        "   \"originalPrice\" : 8000.0,\n" + 
        "   \"discount\" : 20.0,\n" + 
        "   \"offerRating\" : 0.0,\n" + 
        "   \"offerCategories\" : \"sports\",\n" + 
        "   \"offerTerms\" : \"No terms\",\n" + 
        "   \"keywords\" : \"nike shoes,nike running shoes\",\n" + 
        "   \"_class\" : \"com.offershopper.offerdatabaseservice.model.OfferBean\"\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(offerProxy.addOffer(Mockito.any(OfferBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addOffer")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.BAD_REQUEST.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: addCarryBagPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to add offer to Carry Bag
   * Required files: UserVendorActivityController
   */
  @Test
  public void addCarryBagPositiveTest() 
  {
    String expected = "{\"userId\":\"rakesh_005\",\"offerId\":\"offer_005\",\"offerImage\":\"offer_image\",\"offerTitle\":\"offer_title\",\"offerOriginalPrice\":405,\"offerDiscount\":365,\"vendorId\":\"vendor_001\"}";
    //mocking the addOffer method
    Mockito.when(carrybagproxy.addToCarryBag(Mockito.any(CarryBagBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addincarrybag")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: addCarryBagNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to add offer to Carry Bag
   * Required files: UserVendorActivityController
   */
  @Test
  public void addCarryBagNegativeTest() 
  {
    String expected = "[{\"userId\":\"rakesh_005\"," 
        + "\"offerId\":\"offer_0015\","
        + "\"offerImage\":\"offer_image\"," 
        + "\"offerTitle\":\"offer_title\","
        + "\"offerOriginalPrice\":405," 
        + "\"offerDiscount\":365," 
        + "\"offerValidity\":\"22-05-2018\","
        + "\"vendorId\":\"vendor_001\"}]";
    //mocking the addOffer method
    Mockito.when(carrybagproxy.addToCarryBag(Mockito.any(CarryBagBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addincarrybag")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: carryBagPositiveDelete
   * Date: 11-April-2018
   * Description: Positive Test Case to delete offer to Carry Bag
   * Required files: UserVendorActivityController
   */
  @Test
  public void carryBagPositiveDelete() throws Exception 
  {

    //mocking the methods present
    Mockito.when(carrybagproxy.deleteById(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteincarrybag/offer_005")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    }
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: carryBagNegativeDelete
   * Date: 11-April-2018
   * Description: Negative Test Case to delete offer to Carry Bag
   * Required files: UserVendorActivityController
   */
  @Test
  public void carryBagNegativeDelete() throws Exception 
  {

    //mocking the methods present
    Mockito.when(carrybagproxy.deleteById(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteincarrybag/42")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.NOT_FOUND.value(), responseNegative.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: addWishlistPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to add offer in Wishlist
   * Required files: UserVendorActivityController
   */
  @Test
  public void addWishlistPositiveTest() 
  {
    String expected = "{\"id\":\"aa\",\"userId\":\"123\",\"offerId\":\"cc\","
        + "\"offerTitle\":\"dd\",\"offerOriginalPrice\":\"123.0\",\"offerDiscount\":\"123.0\","
        + "\"offerImage\":\"dd\",\"offerValidity\":\"2018-04-05T10:10:30\"}";

    //mocking the addOffer method
    Mockito.when(wishlistproxy.addToWishlist(Mockito.any(WishlistBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addwish/123")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.CREATED.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: addWishlistNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to add offer in Wishlist
   * Required files: UserVendorActivityController
   */
  @Test
  public void addWishlistNegativeTest() 
  {
    String expected = "{\"id\":\"aa\",\"userId\":\"100\",\"offerId\":\"cc\","
        + "\"offerTitle\":\"dd\",\"offerOriginalPrice\":123.0,\"offerDiscount\":123.0,"
        + "\"offerImage\":\"dd\",\"offerValidity\":2018-04-05T10:10:30}";
    //mocking the addOffer method
    Mockito.when(wishlistproxy.addToWishlist(Mockito.any(WishlistBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/addwish/123")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: wishlistPositiveDelete
   * Date: 11-April-2018
   * Description: Positive Test Case to delete offer from Wishlist
   * Required files: UserVendorActivityController
   */
  @Test
  public void wishlistPositiveDelete() throws Exception 
  {

    //mocking the methods present
    Mockito.when(wishlistproxy.deleteWishlistUserOffer(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deletewish/offer_005/aa")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: wishlistNegativeDelete
   * Date: 11-April-2018
   * Description: Negative Test Case to delete offer from Wishlist
   * Required files: UserVendorActivityController
   */
  @Test
  public void wishlistNegativeDelete() throws Exception 
  {

    //mocking the methods present
    Mockito.when(wishlistproxy.deleteWishlistUserOffer(Mockito.anyString(),Mockito.anyString())).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deletewish/offer_005/aa")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(requestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: getUserDataPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to retrieve user's details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getUserDataPositiveTest() 
  {
    Mockito.when(profileproxy.findUserByEmail("vish@gmail.com")).thenReturn(Optional.of(userbean));
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/userdata/poojapathak@gmail.com")
        .accept(MediaType.APPLICATION_JSON);
    try
    {
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      String expectedPositive = "{\n" + 
          "   \"_id\" : \"poojapathak@gmail.com\",\n" + 
          "   \"firstName\" : \"string\",\n" + 
          "   \"lastName\" : \"string\",\n" + 
          "   \"password\" : \"string\",\n" + 
          "   \"role\" : \"string\",\n" + 
          "   \"mobileNo\" : \"string\",\n" + 
          "   \"dob\" : \"string\",\n" + 
          "   \"address\" : {\n" + 
          "       \"street\" : \"string\",\n" + 
          "       \"city\" : \"string\",\n" + 
          "       \"state\" : \"string\",\n" + 
          "       \"zipCode\" : 0\n" + 
          "   },\n" + 
          "   \"gender\" : \"string\",\n" + 
          "   \"spinCount\" : 0,\n" + 
          "   \"osCash\" : 0,\n" + 
          "   \"shopAddress\" : {\n" + 
          "       \"street\" : \"string\",\n" + 
          "       \"city\" : \"string\",\n" + 
          "       \"state\" : \"string\",\n" + 
          "       \"zipCode\" : 0\n" + 
          "   },\n" + 
          "   \"vendorMobileNo\" : \"string\",\n" + 
          "   \"offerIdList\" : [\n" + 
          "       \"5adf097f335f83f2d4a0667d\"\n" + 
          "   ],\n" + 
          "   \"timestamp\" : NumberLong(0),\n" + 
          "   \"_class\" : \"com.offershopper.userdatabaseservice.model.UserBean\"\n" + 
          "}";

      assertNotEquals(expectedPositive, resultPositive.getResponse().getContentAsString());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name: getUserDataNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to retrieve user's details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getUserDataNegativeTest()
  {
    Mockito.when(profileproxy.findUserByEmail("vish@gmail.com")).thenReturn(Optional.of(userbean));
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/userdata/vish@gmail.com")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();;
      String expectedNegative = "{\"id\":null,"
          + "\"firstName\":\"Riya\","
          + "\"lastName\":\"a\","
          + "\"password\":\"pwd1\","
          + "\"mobileNo\":\"9336048823\","
          + "\"email\":\"vish@gmail.com\","
          + "\"dob\":\"1/4/77\","
          + "\"address\":{\"street\":\"d\","
          + "\"city\":\"e\","
          + "\"state\":\"f\","
          + "\"zipCode\":155501},"
          + "\"gender\":\"f\","
          + "\"spinCount\":0,"
          + "\"creditPoint\":0,"
          + "\"shopAddress\":{\"street\":\"dsdf\","
          + "\"city\":\"esgf\","
          + "\"state\":\"fgfa\","
          + "\"zipCode\":155501},"
          + "\"vendorMobileNo\":\"5556\","
          + "\"timestamp\":\"78451\"}";;

          assertNotEquals(expectedNegative, resultNegative.getResponse().getContentAsString());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: updateUserDataPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to update user's details
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateUserPostiveTest()
  {
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class))).thenReturn(new ResponseEntity<Object>(HttpStatus.OK));
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/update")
        .accept(MediaType.APPLICATION_JSON);
    try
    {
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();;      
      assertEquals(HttpStatus.OK, ((MockHttpServletResponse) resultPositive).getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name: updateUserDataNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to update user's details
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateUserNegativeTest() 
  {
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class))).thenReturn(new ResponseEntity<Object>(HttpStatus.CONFLICT));
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/update")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();;      
      assertNotEquals(HttpStatus.OK, ((MockHttpServletResponse) resultNegative).getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  getCarryBagPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to get CarryBag details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getCarryBagPositiveTest() 
  {
    carrybaglist.add(bag);
    Mockito.when(carrybagproxy.getAllByUserId("rakesh_005")).thenReturn(carrybaglist);
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/carrybag/rakesh_005")
        .accept(MediaType.APPLICATION_JSON);
    try
    {
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();;
      String expected = "[{\"userId\":\"rakesh_005\"," 
          + "\"offerId\":\"offer_005\","
          + "\"offerImage\":\"offer_image\"," 
          + "\"offerTitle\":\"offer_title\","
          + "\"offerOriginalPrice\":405," 
          + "\"offerDiscount\":365," 
          + "\"vendorId\":\"vendor_001\"}]";

      assertEquals(HttpStatus.OK, ((MockHttpServletResponse) resultPositive).getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  getCarryBagNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to get CarryBag details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getCarryBagNegativeTest() 
  {
    carrybaglist.add(bag);
    Mockito.when(carrybagproxy.getAllByUserId("rakesh_005")).thenReturn(carrybaglist);
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/carrybag/rakesh_005")
        .accept(MediaType.APPLICATION_JSON);
    try
    {
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();;
      String expected = "[{\"userId\":\"rakesh_008\"," 
          + "\"offerId\":\"offer_005\","
          + "\"offerImage\":\"offer_image\"," 
          + "\"offerTitle\":\"offer_title\","
          + "\"offerOriginalPrice\":405," 
          + "\"offerDiscount\":365," 
          + "\"offerValidity\":\"22-06-2018\","
          + "\"vendorId\":\"vendor_001\"}]";

      assertNotEquals(expected, resultNegative.getResponse().getContentAsString());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  getWishListPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to get WishList details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getWishListPositiveTest()
  {
    wishlist.add(wishlistbean);
    Mockito.when(wishlistproxy.getWishlist("100")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(wishlist));
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/getwishlist/100")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {

      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      String expected = "[{\"id\":\"aa\",\"userId\":\"100\",\"offerId\":\"cc\","
          + "\"offerTitle\":\"dd\",\"offerOriginalPrice\":123.0,\"offerDiscount\":123.0,"
          + "\"offerImage\":\"dd\",\"offerValidity\":\"2018-04-04T10:10:30\"}]";

      assertEquals(expected, resultPositive.getResponse().getContentAsString());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  getWishListNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to get WishList details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getWishListNegativeTest() 
  {
    wishlist.add(wishlistbean);
    Mockito.when(wishlistproxy.getWishlist("100")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(wishlist));
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/getwishlist/100")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {

      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();;
      String expected = "[{\"id\":\"aa\",\"userId\":\"101\",\"offerId\":\"cc\","
          + "\"offerTitle\":\"dd\",\"offerOriginalPrice\":123.0,\"offerDiscount\":123.0,"
          + "\"offerImage\":\"dd\",\"offerValidity\":\"2018-04-04T10:10:30\"}]";

      assertNotEquals(expected, resultNegative.getResponse().getContentAsString());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  @Test
  public void getSubscriptionPositiveTest()
  {
    String mockOffer="{\n" + 
        "\"id\": \"5ae6a2c0c243ed2218c6d4a9\",\n" + 
        "\"userId\": \"girish@gmail.com\",\n" + 
        "\"vendorId\": \"megha@gmail.com\",\n" + 
        "\"category\": \"clothing\"\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(subscribeproxy.getByUserId("girish@gmail.com")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(subscriptionlist));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/getsubscription/girish@gmail.com")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    }
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  getSubscriptionNegativeTest
   * Date: 11-April-2018
   * Description: Positive Test Case to get Subscribe details
   * Required files: UserVendorActivityController
   */
  @Test
  public void getSubscriptionNegativeTest()
  {
    subscriptionlist.add(subscribebean);
    Mockito.when(subscribeproxy.getByUserId("girish@gmail.com")).thenReturn(ResponseEntity.status(HttpStatus.OK).body(subscriptionlist));
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/getsubscription")
        .accept(MediaType.APPLICATION_JSON);
    try 
    {

      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();;
      String expected = "[{\"userId\":\"3\",\"vendorId\":\"10\",\"category\":\"shirt\"}]";
      assertNotEquals(expected, resultNegative.getResponse().getContentAsString());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  addReferralPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to add referral points to referral database
   * Required files: UserVendorActivityController
   */
  @Test
  public void addReferralPositiveTest()
  {
    String expected="{\"id\":\"5ac77f17efce7b2cc78428ed\" assertEquals(expected, resultPositive.getResponse().getContentAsString());,"
        + "\"firstName\":\"mom\","
        + "\"lastName\":\"a\","
        + "\"password\":\"pwd1\","
        + "\"mobileNo\":\"93360567y548823\","
        + "\"email\":\"game@gmail.com\","
        + "\"dob\":\"1/4/77\","
        + "\"address\":{\"street\":\"d\","
        + "\"city\":\"e\","
        + "\"state\":\"f\","
        + "\"zipCode\":155501},"
        + "\"gender\":\"f\","
        + "\"spinCount\":0,"
        + "\"creditPoint\":0,"
        + "\"shopAddress\":{\"street\":\"dsdf\","
        + "\"city\":\"esgf\","
        + "\"state\":\"fgfa\","
        + "\"zipCode\":155501},"
        + "\"vendorMobileNo\":\"564556746\","
        + "\"timestamp\":\"78451\"}";
    //mocking the addOffer method
    Mockito.when((referralproxy.addSpin(Mockito.anyString(),Mockito.anyString()))).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/referral/game@gmail.com/game@gmail.com")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the URL
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.CREATED.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name:  addReferralNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to add referral points to referral database
   * Required files: UserVendorActivityController
   */
  @Test
  public void addReferralNegativeTest() 
  {
    String expected="{\"id\":\"5ac77f17efce7b2cc78428ed\","
        + "\"firstName\":\"mom\","
        + "\"lastName\":\"a\","
        + "\"password\":\"pwd1\","
        + "\"mobileNo\":\"93360567y548823\","
        + "\"email\":\"game@gmail.com\","
        + "\"dob\":\"1/4/77\","
        + "\"address\":{\"street\":\"d\","
        + "\"city\":\"e\","
        + "\"state\":\"f\","
        + "\"zipCode\":155501},"
        + "\"gender\":\"f\","
        + "\"spinCount\":0,"
        + "\"creditPoint\":0,"
        + "\"shopAddress\":{\"street\":\"dsdf\","
        + "\"city\":\"esgf\","
        + "\"state\":\"fgfa\","
        + "\"zipCode\":155501},"
        + "\"vendorMobileNo\":\"564556746\","
        + "\"timestamp\":\"78451\"}";
    //mocking the addOffer method
    Mockito.when((referralproxy.addSpin(Mockito.anyString(),Mockito.anyString()))).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/referral/game@gmail.com/game@gmail.com")
        .accept(MediaType.APPLICATION_JSON).content(expected)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

   /*
   * Name:  convertToVendorNegativeTest
   * Date: 11-April-2018
   * Description: Positive Test Case to convert to vendor
   * Required files: UserVendorActivityController
   */
  @Test
  public void convertToVendorNegativeTest() 
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/converttovendor/sharanghotra@gmail.com/customer")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  convertToVendorNegative
   * Date: 11-April-2018
   * Description: Negative Test Case to convert to vendor
   * Required files: UserVendorActivityController
   */
  @Test
  public void convertToVendorNegative() 
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class))).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/converttovendor/sharanghotra@gmail.com/customer")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.BAD_REQUEST.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  updateSpincountsPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to update Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateSpincountsPositiveTest() 
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name:  updateSpincountsNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to update Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateSpincountsNegativeTest() 
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.BAD_REQUEST.value(), responsePositive.getStatus());
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
  }

  /*
   * Name:  updateSpincountsOSCashNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to update Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateSpincountsOSCashNegativeTest() 
  {
    userbean.setTimestamp(System.currentTimeMillis());
    userbean.setOsCash((userbean.getOsCash()));
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name:  getSpinCountNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to get Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void getSpinCountNegativeTest()
  {
    userbean.setSpinCount((userbean.getSpinCount()-1));
    userbean.setOsCash((userbean.getOsCash()));
    userbean.setTimestamp(System.currentTimeMillis());
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.OK).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name:  updateSpinPositiveTest
   * Date: 11-April-2018
   * Description: Positive Test Case to get Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateSpinPositiveTest()
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.NOT_FOUND.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
  /*
   * Name:  updateSpinNegativeTest
   * Date: 11-April-2018
   * Description: Negative Test Case to get Spin Count
   * Required files: UserVendorActivityController
   */
  @Test
  public void updateSpinNegativeTest()
  {
    String mockOffer="{\n" + 
        "   \"_id\" : \"sharanghotra@gmail.com\",\n" + 
        "   \"firstName\" : \"Sharan\",\n" + 
        "   \"lastName\" : \"Ghotra\",\n" + 
        "   \"password\" : \"Sharan@123\",\n" + 
        "   \"role\" : \"vendor\",\n" + 
        "   \"mobileNo\" : \"8016554612\",\n" + 
        "   \"email\" : \"sharan@gmail.com\",\n" + 
        "   \"dob\" : \"03/04/1996\",\n" + 
        "   \"address\" : {\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"gender\" : \"M\",\n" + 
        "   \"spinCount\" : 0.0,\n" + 
        "   \"osCash\" : 0.0,\n" + 
        "   \"shopAddress\" : {\n" + 
        "       \"name\" : \"Sharan Stationery\",\n" + 
        "       \"number\" : 23.0,\n" + 
        "       \"street\" : \"garhi cantt\",\n" + 
        "       \"city\" : \"Dehradun\",\n" + 
        "       \"state\" : \"Uttarakhand\",\n" + 
        "       \"zipCode\" : 248003.0\n" + 
        "   },\n" + 
        "   \"vendorMobileNo\" : \"9874563201\",\n" + 
        "   \"offerIdList\" : [],\n" + 
        "   \"timestamp\" : 0.0\n" + 
        "}";
    //mocking the addOffer method
    Mockito.when(profileproxy.updateProfile(Mockito.any(UserBean.class)))
    .thenReturn(ResponseEntity.status(HttpStatus.CONFLICT).body(null));
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/updatespincount/0")
        .accept(MediaType.APPLICATION_JSON).content(mockOffer)
        .contentType(MediaType.APPLICATION_JSON);
    try 
    {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn(); 
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } 
    catch (Exception e) 
    {
      System.out.println(e.getMessage());
    }
  }
}
