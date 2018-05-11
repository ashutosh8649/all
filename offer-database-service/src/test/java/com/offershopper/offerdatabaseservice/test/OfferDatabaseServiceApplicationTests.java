package com.offershopper.offerdatabaseservice.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.offershopper.offerdatabaseservice.database.OfferRepository;
import com.offershopper.offerdatabaseservice.model.AddressBean;
import com.offershopper.offerdatabaseservice.model.OfferBean;
import com.offershopper.offerdatabaseservice.rabbit.MessageSender;

@WebMvcTest
@RunWith(SpringRunner.class)
public class OfferDatabaseServiceApplicationTests {

  @Autowired
  MockMvc mockMvc;
  @MockBean
  private MessageSender messageSender;

  @MockBean
  OfferRepository offerRepository;

  List<OfferBean> offersList = new ArrayList<OfferBean>();
  OfferBean offer;

  //before every test case
  @Before
  public void setup() {

    offer = new OfferBean("1", "uid-1", "Fridge", LocalDateTime.of(2018, Month.APRIL, 4, 10, 10, 30),
        LocalDateTime.of(2018, Month.APRIL, 5, 10, 10, 30),
        new AddressBean("Om","220","this is my house","this is my house","this is my house",144514),
        "Buy two get one", 40000f, 2000f, 9f, "Electronics",
        "Valid only in specified time", "refrigerator, sony","url");

    offersList.add(offer); 
        
    offer = new OfferBean("2", "uid-1", "Mobile", LocalDateTime.of(2018, Month.APRIL, 4, 10, 10, 30),
        LocalDateTime.of(2018, Month.APRIL, 5, 10, 10, 30),
        new AddressBean("Gulati","240","this is my house","this is my house","this is my house",144514),
        "Buy two get one", 40000f, 2000f, 9f, "Electronics",
        "Valid only in specified time","Oppo, phone","url");
    
    offersList.add(offer);  
  }
  
  //after every test case
  @After
  public void cleanUp() {
    offer = null;
    offersList = null;
  }

  @Test
  public void getAllOffersTest() throws Exception {
    // mocking offer repository and receiving mock data
    Mockito.when(offerRepository.findByUserId(Mockito.anyString())).thenReturn(offersList);
    
    // mock request
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/vendor/uid-1/offers")
        .accept(MediaType.APPLICATION_JSON);
    // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    //json format that we expect
    String expected="[{\"offerId\":\"1\","
        + "\"userId\":\"uid-1\""
        + ",\"offerTitle\":\"Fridge\""
        + ",\"offerValidity\":\"2018-04-04T10:10:30\","
        + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
        + "\"address\":"
        + "{"
        + "\"name\":\"Om\","
        + "\"number\":\"220\","
        + "\"street\":\"this is my house\","
        + "\"city\":\"this is my house\","
        + "\"state\":\"this is my house\","
        + "\"zipCode\":144514"
        + "},"
        + "\"offerDescription\":\"Buy two get one\""
        + ",\"originalPrice\":40000.0,"
        + "\"discount\":2000.0,"
        + "\"offerRating\":9.0,"
        + "\"offerCategories\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\","
        + "\"keywords\":\"refrigerator, sony\","
        + "\"imageURL\":\"url\"}"
        
        + ","
        
        + "{\"offerId\":\"2\","
        + "\"userId\":\"uid-1\""
        + ",\"offerTitle\":\"Mobile\""
        + ",\"offerValidity\":\"2018-04-04T10:10:30\","
        + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
        + "\"address\":"
        + "{"
        + "\"name\":\"Gulati\","
        + "\"number\":\"240\","
        + "\"street\":\"this is my house\","
        + "\"city\":\"this is my house\","
        + "\"state\":\"this is my house\","
        + "\"zipCode\":144514"
        + "},"
        + "\"offerDescription\":\"Buy two get one\""
        + ",\"originalPrice\":40000.0,"
        + "\"discount\":2000.0,"
        + "\"offerRating\":9.0,"
        + "\"offerCategories\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\","
        + "\"keywords\":\"Oppo, phone\","
        + "\"imageURL\":\"url\"}"
        + "]";
    //comparing both objects
    assertEquals(expected, result.getResponse().getContentAsString());
  }
  
  @Test
  public void getSingleOfferTest() throws Exception {
    //mocking the findById method
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(offersList.get(0)));
    // mock request
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/offers/1").accept(MediaType.APPLICATION_JSON);
 // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
  //json format that we expect
    String expected="{\"offerId\":\"1\","
            + "\"userId\":\"uid-1\""
            + ",\"offerTitle\":\"Fridge\""
            + ",\"offerValidity\":\"2018-04-04T10:10:30\","
            + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
            + "\"address\":"
            + "{"
            + "\"name\":\"Om\","
            + "\"number\":\"220\","
            + "\"street\":\"this is my house\","
            + "\"city\":\"this is my house\","
            + "\"state\":\"this is my house\","
            + "\"zipCode\":144514"
            + "},"
            + "\"offerDescription\":\"Buy two get one\""
            + ",\"originalPrice\":40000.0,"
            + "\"discount\":2000.0,"
            + "\"offerRating\":9.0,"
            + "\"offerCategories\":\"Electronics\""
            + ",\"offerTerms\":\"Valid only in specified time\","
            + "\"keywords\":\"refrigerator, sony\","
            + "\"imageURL\":\"url\"}";
    
    assertEquals(expected, result.getResponse().getContentAsString());
  }
  
  
  
  
  @Test
  public void getSingleOfferNegativeTest() throws Exception {
    //mocking the findById method
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
    // mock request
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/offers/1").accept(MediaType.APPLICATION_JSON);
 // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
  //json format that we expect
    String expected="{\"offerId\":\"1\","
            + "\"userId\":\"uid-1\""
            + ",\"offerTitle\":\"Fridge\""
            + ",\"offerValidity\":\"2018-04-04T10:10:30\","
            + "\"dateOfAnnouncement\":\"2018-04-05T10:10:30\","
            + "\"address\":"
            + "{"
            + "\"name\":\"Om\","
            + "\"number\":\"220\","
            + "\"street\":\"this is my house\","
            + "\"city\":\"this is my house\","
            + "\"state\":\"this is my house\","
            + "\"zipCode\":144514"
            + "},"
            + "\"offerDescription\":\"Buy two get one\""
            + ",\"originalPrice\":40000.0,"
            + "\"discount\":2000.0,"
            + "\"offerRating\":9.0,"
            + "\"offerCategories\":\"Electronics\""
            + ",\"offerTerms\":\"Valid only in specified time\","
            + "\"keywords\":\"refrigerator, sony\","
            + "\"imageURL\":\"url\"}";
    //assertEquals(expected, result.getResponse().getContentAsString());
    assertNotEquals(expected, result.getResponse().getContentAsString());
  }
  
  
  
  
  //when the same record is not present
  @Test
  public void addOfferTest() throws Exception {
    //functions to be mocked
    //passing Optional.empty in case of 
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
    Mockito.when(offerRepository.save(Mockito.any(OfferBean.class))).thenReturn(offersList.get(1));
    //post request body
    String requestBody="{\"offerId\":\"1\","
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
        + "\"offerCategories\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\"}";
    // mock request
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/offers")
        .accept(MediaType.APPLICATION_JSON)
        .content(requestBody)
        .contentType(MediaType.APPLICATION_JSON);
 // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(201);
  }
  
  @Test
  public void updateOfferTest() throws Exception {
  //functions to be mocked
    //passing Optional.empty in case of 
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(offer));
    Mockito.when(offerRepository.save(Mockito.any(OfferBean.class))).thenReturn(offer);
    //new value of offer
    String updatedOffer="{\"offerId\":\"1\","
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
        + "\"offerCategories\":\"Electronics\""
        + ",\"offerTerms\":\"Valid only in specified time\"}";  
    
    
    // mock request
    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/offers/1")
        .accept(MediaType.APPLICATION_JSON)
        .content(updatedOffer)
        .contentType(MediaType.APPLICATION_JSON);
 // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
  }
  
  //when the offer to be deleted exists
  @Test
  public void deleteOfferTest() throws Exception {
    //mocking the methods present
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(offersList.get(0)));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/offers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    //sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
  }
  
  //when the offer doesnt exits
  @Test
  public void deleteNonExistingOfferTest() throws Exception {
    //mocking the methods present
    Mockito.when(offerRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/offers/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);
    //sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(404);
  }
        
}