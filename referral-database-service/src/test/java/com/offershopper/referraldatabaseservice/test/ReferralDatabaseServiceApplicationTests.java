/*
 * File name:ReferralDatabaseServiceApplicationTests
 * Author: Vishruty
 * Date: 13-April-2018
 * Description: Test cases for the controller
 * Version: 2.0
*/
package com.offershopper.referraldatabaseservice.test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
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

import com.offershopper.referraldatabaseservice.MessageSender;
import com.offershopper.referraldatabaseservice.model.AddressBean;
import com.offershopper.referraldatabaseservice.model.UserBean;
import com.offershopper.referraldatabaseservice.repository.UserRepository;


@RunWith(SpringRunner.class)
@WebMvcTest
public class ReferralDatabaseServiceApplicationTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	private MessageSender messageSender;
	@MockBean
	UserRepository userRepository;
	UserBean userBean;
	AddressBean homeAddress;
	AddressBean shopAddress;
	 @Before
	  public void setup() 
	  {
		  homeAddress=new AddressBean("d","e","f",155501);
		  shopAddress=new AddressBean("d","e","f",155501);
		  userBean=new UserBean("Riya","a","pwd1","93360488723","vish@gmail.com","1/4/77",homeAddress,"f",0,0,shopAddress,"56456","gjkshdbh");
	  }
	 
	 @After
	  public void cleanUp() {
	    userBean = null;
	    homeAddress=null;
	    shopAddress=null;
	  }
	 
	@Test
	public void addSpinTest() throws Exception{
		UserBean bean = mock(UserBean.class);
		 //mocking the methods present
	    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userBean);
	    Mockito.when(userRepository.save(Mockito.any(UserBean.class))).thenReturn(userBean);
	    Mockito.when(bean.getSpinCount()).thenReturn(1);
	    String newUser="{\"id\":\"5ac77a17efce7b2cc78428ed\","
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
	    		+ "\"timestamp\":\"dgjkshdbh\"}";
	    
	    // mock request
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/referral/spin/paper@gmail.com/vish@gmail.com")
	        .accept(MediaType.APPLICATION_JSON)
	        .content(newUser)
	        .contentType(MediaType.APPLICATION_JSON);
	 // sending mock request at the url
	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	    assertThat(result.getResponse().getStatus()).isEqualTo(200);
	}
	
	
	
	@Test
	public void addSpinTestNeg() throws Exception{
		UserBean bean = mock(UserBean.class);
		 //mocking the methods present
	    Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);
	    String newUser="{\"id\":\"5ac77a17efce7b2cc78428ed\","
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
	    		+ "\"timestamp\":\"dgjkshdbh\"}";
	    
	    // mock request
	    RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/referral/spin/paper@gmail.com/vish@gmail.com")
	        .accept(MediaType.APPLICATION_JSON)
	        .content(newUser)
	        .contentType(MediaType.APPLICATION_JSON);
	 // sending mock request at the url
	    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	    assertThat(result.getResponse().getStatus()).isEqualTo(204);
	}
}
