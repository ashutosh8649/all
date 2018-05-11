package com.offershopper.wishlistdatabaseservice.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.offershopper.wishlistdatabaseservice.controller.WishlistDatabaseController;
import com.offershopper.wishlistdatabaseservice.database.WishlistRepository;
import com.offershopper.wishlistdatabaseservice.model.WishlistBean;
import com.offershopper.wishlistdatabaseservice.rabbit.MessageSender;

@RunWith(SpringRunner.class)
//@SpringBootTest
@WebMvcTest(value = WishlistDatabaseController.class)
public class WishlistDatabaseServiceApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  WishlistRepository wishlistRepository;
  
  @MockBean
  private MessageSender messageSender;

  WishlistBean mockWishlistBean = new WishlistBean("aa", "100", "cc", "dd", 123f, 123f, "dd", LocalDateTime.of(2018, Month.APRIL, 4, 10, 10, 30));
  List<WishlistBean> mockList = new ArrayList<WishlistBean>();

  // Function to Test Positive Get Mapping of showing offers in user's wishlist
  @Test
  public void getWishlistPositiveTest() {
    mockList.add(mockWishlistBean);
    //mocking the existsByUserId method
    Mockito.when(wishlistRepository.existsByUserId("100")).thenReturn(true);
    //mocking the findByUserId method
    Mockito.when(wishlistRepository.findByUserId("100")).thenReturn(mockList);
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/getWishlist/100")
          .accept(MediaType.APPLICATION_JSON);
    //json format that we expect
    String expected = "[{\"id\":\"aa\","
    		+ "\"userId\":\"100\","
    		+ "\"offerId\":\"cc\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":123.0,"
            + "\"offerDiscount\":123.0,"
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":2018-04-05T10:10:30}]";
    try {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();            
      JSONAssert.assertEquals(expected, resultPositive.getResponse().getContentAsString(), false);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  // Function to Test Negative Get Mapping of showing offers in user's wishlist
  @Test
  public void getWishlistNegativeTest() {
    mockList.add(mockWishlistBean);
    //mocking the existsByUserId method
    Mockito.when(wishlistRepository.existsByUserId("101")).thenReturn(false);
    //mocking the findByUserId method
    Mockito.when(wishlistRepository.findByUserId("100")).thenReturn(mockList);
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.get("/getWishlist/101")
          .accept(MediaType.APPLICATION_JSON);
    //json format that we expect
    String expected = "[{\"id\":\"aa\","
    		+ "\"userId\":\"100\","
    		+ "\"offerId\":\"cc\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":\"123.0\","
            + "\"offerDiscount\":\"123.0\","
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":\"2018-04-05T10:10:30\"}]";
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();            
      JSONAssert.assertNotEquals(expected, resultNegative.getResponse().getContentAsString(), false);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  // Function to Test Post Positive Mapping of adding offer in user wishlist
  @Test
  public void addToWishlistPositveTest() {
    String validWishlistOffer = "{\"id\":\"aa\","
    		+ "\"userId\":\"123\","
    		+ "\"offerId\":\"cc\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":\"123.0\","
            + "\"offerDiscount\":\"123.0\","
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":\"2018-04-05T10:10:30\"}";
    //mocking the save method
    Mockito.when(wishlistRepository.save(Mockito.any(WishlistBean.class))).thenReturn(mockWishlistBean);
    //mocking the existsByUserIdAndOfferId method
    Mockito.when(wishlistRepository.existsByUserIdAndOfferId("123","cc")).thenReturn(false);
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.post("/add-to-wishlist")
    	  .accept(MediaType.APPLICATION_JSON).content(validWishlistOffer)
          .contentType(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.CREATED.value(), responsePositive.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Test Post Negative Mapping of adding offer in user wishlist
  @Test
  public void addToWishlistNegativeTest() {
    String invalidWishlistOffer = "{\"id\":\"aa\","
    		+ "\"userId\":\"123\","
    		+ "\"offerId\":\"cc\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":\"123.0\","
            + "\"offerDiscount\":\"123.0\","
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":\"2018-04-05T10:10:30\"}";
    //mocking the save method
    Mockito.when(wishlistRepository.save(Mockito.any(WishlistBean.class))).thenReturn(mockWishlistBean);
    //mocking the existsByUserIdAndOfferId method
    Mockito.when(wishlistRepository.existsByUserIdAndOfferId("123","cc")).thenReturn(true);
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.post("/add-to-wishlist")
          .accept(MediaType.APPLICATION_JSON).content(invalidWishlistOffer)
          .contentType(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Positive Test Delete Mapping of deleting offer in user wishlist
  @Test
  public void deleteWishlistOfferPositiveTest() {
	//mocking the existsByOfferId method
	Mockito.when(wishlistRepository.existsByOfferId("100")).thenReturn(true);
	//mock request
	RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-offer/100")
          .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
        assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
      } catch (Exception e) {
          System.out.println(e.getMessage());
      }
  }
  
  //Function to Negative Test Delete Mapping of deleting offer in user wishlist
  @Test
  public void deleteWishlistOfferNegativeTest() {
	//mocking the existsByOfferId method
	Mockito.when(wishlistRepository.existsByOfferId("101")).thenReturn(false);
	//mock request
	RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-offer/101")
          .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertEquals(HttpStatus.NOT_FOUND.value(), responseNegative.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Test Positive Delete Mapping of deleting all user's offer in user wishlist
  @Test
  public void deleteWishlistUserPositiveTest() {
	//mocking the existsByUserId method
	Mockito.when(wishlistRepository.existsByUserId("100")).thenReturn(true);
	//mock request
	RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-user/100")
  		  .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url	
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
      
  //Function to Test Negative Delete Mapping of deleting all user's offer in user wishlist
  @Test
  public void deleteWishlistUserNegativeTest() {
	//mocking the existsByUserId method
	Mockito.when(wishlistRepository.existsByUserId("101")).thenReturn(false);
	//mock request
	RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-user/101")
          .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertEquals(HttpStatus.NOT_FOUND.value(), responseNegative.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Test Positive Delete Mapping of deleting offer in user wishlist
  @Test
  public void deleteWishlistUserOfferPositiveTest() {
	//mocking the existsByOfferIdAndUserId method
	Mockito.when(wishlistRepository.existsByOfferIdAndUserId("cc","100")).thenReturn(true);
	//mock request
	RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-offer/cc/100")
          .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Test Delete Mapping of deleting offer in user wishlist
  @Test
  public void deleteWishlistUserOfferNegativeTest() {
    //mocking the existsByOfferIdAndUserId method
	Mockito.when(wishlistRepository.existsByOfferIdAndUserId("cc","101")).thenReturn(false);
	//mock request
	RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.delete("/delete-wishlist-offer/cc/101")
          .accept(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertEquals(HttpStatus.NOT_FOUND.value(), responseNegative.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  //Function to Test Positive Update Mapping of updating offer in wishlist
  @Test
  public void updateWishlistOfferPositveTest() {
    mockList.add(mockWishlistBean);
    String validWishlistOffer = "{\"id\":\"aa\","
    		+ "\"userId\":\"100\","
    		+ "\"offerId\":\"cc\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":\"123.0\","
            + "\"offerDiscount\":\"123.0\","
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":\"2018-04-05T10:10:30\"}";
	//mocking the findAll method
    Mockito.when(wishlistRepository.findAll()).thenReturn(mockList);
    //mock request
    RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.put("/update-wishlist-offer")
          .accept(MediaType.APPLICATION_JSON).content(validWishlistOffer)
          .contentType(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
      MockHttpServletResponse responsePositive = resultPositive.getResponse();
      assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
  
  //Function to Test Negative Update Mapping of updating offer in wishlist
  @Test
  public void updateWishlistOfferNegatveTest() {
    mockList.add(mockWishlistBean);
    String invalidWishlistOffer = "{\"id\":\"aa\","
    		+ "\"userId\":\"100\","
    		+ "\"offerId\":\"ccd\","
            + "\"offerTitle\":\"dd\","
            + "\"offerOriginalPrice\":\"123.0\","
            + "\"offerDiscount\":\"123.0\","
            + "\"offerImage\":\"dd\","
            + "\"offerValidity\":\"2018-04-05T10:10:30\"}";
    //mocking the findAll method
    Mockito.when(wishlistRepository.findAll()).thenReturn(mockList);
    //mock request
    RequestBuilder negativeRequestBuilder = MockMvcRequestBuilders.put("/update-wishlist-offer")
          .accept(MediaType.APPLICATION_JSON).content(invalidWishlistOffer)
          .contentType(MediaType.APPLICATION_JSON);
    try {
      //sending mock request at the url
      MvcResult resultNegative = mockMvc.perform(negativeRequestBuilder).andReturn();
      MockHttpServletResponse responseNegative = resultNegative.getResponse();
      assertEquals(HttpStatus.NOT_FOUND.value(), responseNegative.getStatus());
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}