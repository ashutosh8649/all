package com.offershopper.subscribedatabaseservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.offershopper.subscribedatabaseservice.database.SubscribeRepository;
import com.offershopper.subscribedatabaseservice.model.SubscribeBean;
import com.offershopper.subscribedatabaseservice.service.MessageSender;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SubscribeControllerTest {

  SubscribeBean getBean, addBean, delBean;
  List<SubscribeBean> subscribeBeans;

  @Autowired
  MockMvc mockMvc;

  @MockBean
  SubscribeBean subscribeBean;

  @MockBean
  SubscribeRepository subscribeRepository;

  @MockBean
  MessageSender messageSender;

  // inializing variables before each test case
  @Before
  public void setup() {
    getBean = new SubscribeBean("1","2", "10", "shirt");
    addBean = new SubscribeBean("2","4", "2", "tshirt");
    delBean = new SubscribeBean("3","42", "12", "shoes");
    subscribeBeans = new ArrayList<SubscribeBean>();
  }

  @Test
  public void testGetAll() {

    subscribeBeans.add(getBean);
    // mocking the methods present
    Mockito.when(subscribeRepository.findAll()).thenReturn(subscribeBeans);
    try {
      // sending mock request at the url
      MvcResult result = mockMvc.perform(get("/subscribe/get").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();
      System.out.println("\n" + result.getResponse().getContentAsString() + "\nHello\n");
      String expected = "[{\"userId\":\"2\",\"vendorId\":\"10\",\"category\":\"shirt\"}]";
      JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    Mockito.verify(subscribeRepository).findAll();
  }

  @Test
  public void testNegativeGetAll() {

    subscribeBeans.add(getBean);
    // mocking the methods present
    Mockito.when(subscribeRepository.findAll()).thenReturn(Collections.emptyList());

    try {
      // sending mock request at the url
      MvcResult result = mockMvc.perform(get("/subscribe/get").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk()).andReturn();
      System.out.println("\n" + result.getResponse().getContentAsString() + "\nHello\n");
     // String negativeExpected = "[{\"userId\":\"3\",\"vendorId\":\"1\",\"category\":\"jeans\"}]";
      //assertThat(equals(result.getResponse().getStatus()==HttpStatus.NOT_FOUND.value()));
      //assertThat(result.getResponse().getStatus()).isEqualTo(404);
      String negativeExpected="[]";
      JSONAssert.assertEquals(negativeExpected, result.getResponse().getContentAsString(), false);

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    Mockito.verify(subscribeRepository).findAll();
  }



  @Test
  public void testAddSubscribeBean() throws Exception {

    String exampleBean = "{\"userId\":\"4\",\"vendorId\":\"2\",\"category\":\"tshirt\"}";
    // mocking the methods present returned by the repository method
    Mockito.when(subscribeRepository.findByUserIdAndVendorId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
    // returned by the repository method
    Mockito.when(subscribeRepository.insert(Mockito.any(SubscribeBean.class))).thenReturn(addBean);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscribe/add/byvendorid").accept(MediaType.APPLICATION_JSON)
        .content(exampleBean).contentType(MediaType.APPLICATION_JSON);
    // sending mock request at the url, returned by the controller method
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();
    assertEquals(HttpStatus.OK.value(), response.getStatus());

  }

  @Test
  public void testAddSubscribeBeanExisting() throws Exception {

    String exampleBean = "{\"userId\":\"4\",\"vendorId\":\"2\",\"category\":\"tshirts\"}";
    // mocking the methods present
    Mockito.when(subscribeRepository.findByUserIdAndVendorId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(addBean));
    Mockito.when(subscribeRepository.insert(Mockito.any(SubscribeBean.class))).thenReturn(addBean);
    RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/subscribe/add/byvendorid").accept(MediaType.APPLICATION_JSON)
        .content(exampleBean).contentType(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    // sending mock request at the url
    MockHttpServletResponse response = result.getResponse();
    assertThat(result.getResponse().getStatus()).isEqualTo(409);

  }

  @Test
  public void testDeleteByVendorId() throws Exception {
    // mocking the methods present
    Mockito.when(subscribeRepository.findByUserIdAndVendorId(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(delBean));
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/subscribe/del/byvendorid/42/12")
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
    // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(200);
  }

  @Test
  public void negativeTestDeleteByVendorId() throws Exception {
    // mocking the methods present
    Mockito.when(subscribeRepository.findByUserIdAndVendorId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
    RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/subscribe/del/byvendorid/12/22")
        .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);
    // sending mock request at the url
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    assertThat(result.getResponse().getStatus()).isEqualTo(404);
  }

}
