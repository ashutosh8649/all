package com.offershopper.userdatabaseservice.contoller.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
<<<<<<< HEAD
=======
import com.offershopper.userdatabaseservice.model.AddressBean;
import com.offershopper.userdatabaseservice.MessageSender;
>>>>>>> 0a4ac14f89d77436ae6dafd8d206996d1bdb3f29
import com.offershopper.userdatabaseservice.controller.UserServiceController;
import com.offershopper.userdatabaseservice.model.AddressBean;
import com.offershopper.userdatabaseservice.model.UserBean;
import com.offershopper.userdatabaseservice.repository.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserServiceControllerTests {
	@Autowired
	MockMvc mockMvc;
	@MockBean
	private MessageSender messageSender;
	@MockBean
	UserRepository userRepository;

<<<<<<< HEAD
	@Test	
	 public void getSingleUserById() throws Exception {
		    //mocking the findById method
		    Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(userBean));
		    // mock request
		    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/details/game@gmail.com").accept(MediaType.APPLICATION_JSON);
		 // sending mock request at the url
		    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		  //json format that we expect
		    String exampleString="{\"firstName\":\"mom\","
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
		    		+ "\"timestamp\":123456}";
		    assertEquals(exampleString, result.getResponse().getContentAsString());
		  }
		  
	//negative test cases
	
=======
	UserBean userBean;
	AddressBean homeAddress;
	AddressBean shopAddress;
	List<String> offerIdList;

	@Before
	public void setup() {
		homeAddress = new AddressBean("","","", "", "", 453);
		shopAddress = new AddressBean("","","", "", "", 453);
		offerIdList = new ArrayList<String>();
		offerIdList.add("offer-032");
		userBean = new UserBean("Riya", "a", "pwd1","vendor", "93360488723", "vish@gmail.com", "1/4/77", homeAddress, "f", 0, 0,
				shopAddress, "56456", offerIdList, 2345);
	}

	@After
	public void cleanUp() {
		userBean = null;
		homeAddress = null;
		shopAddress = null;
	}

>>>>>>> 0a4ac14f89d77436ae6dafd8d206996d1bdb3f29
	@Test
	public void retrieveUserByIdPositive() throws Exception {
		// mocking the findById method
		Mockito.when(userRepository.findById("vish@gmail.com")).thenReturn(Optional.of(userBean));
		// mock request
		RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.get("/users/details/vish@gmail.com")
				.accept(MediaType.APPLICATION_JSON);
		// sending mock request at the url
		try {
			MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
			// json format that we expect
			String exampleString = "{\"firstName\":\"Riya\",\"lastName\":\"a\",\"password\":null,\"role\":\"vendor\",\"mobileNo\":\"93360488723\",\"email\":\"vish@gmail.com\",\"dob\":\"1/4/77\",\"address\":{\"name\":\"\",\"number\":\"\",\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zipCode\":453},\"gender\":\"f\",\"spinCount\":0,\"osCash\":0,\"shopAddress\":{\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zipCode\":453}\n" + 
					"		,\"vendorMobileNo\":\"56456\",\"offerIdList\":[\"offer-032\"],\"timestamp\":2345}";
			JSONAssert.assertEquals(exampleString, resultPositive.getResponse().getContentAsString(), false);
			MockHttpServletResponse responsePositive = resultPositive.getResponse();

			assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
		} catch (Exception e) {
			System.out.println("Error during testing positive values for getfeedback() \n" + e.getMessage());
		}
	}

	// negative test cases

	@Test
	public void retrieveUserByIdNegative() throws Exception {
		// mocking the findById method

		Optional<UserBean> temp_user = userRepository.findById(userBean.getEmail());
		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(temp_user);
		// mock request
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/details/game@gmail.com")
				.accept(MediaType.APPLICATION_JSON);
		// sending mock request at the url
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		// json format that we expect
		String exampleString = "{\"firstName\":\"Riya\",\"lastName\":\"a\",\"password\":null,\"role\":\"vendor\",\"mobileNo\":\"93360488723\",\"email\":\"vish@gmail.com\",\"dob\":\"1/4/77\",\"address\":{\"name\":\"\",\"number\":\"\",\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zipCode\":453},\"gender\":\"f\",\"spinCount\":0,\"osCash\":0,\"shopAddress\":{\"street\":\"\",\"city\":\"\",\"state\":\"\",\"zipCode\":453}\n" + 
				"		,\"vendorMobileNo\":\"56456\",\"offerIdList\":[\"offer-032\"],\"timestamp\":2345}";
		assertNotEquals(exampleString, result.getResponse().getContentAsString());
	}

	@Test
	public void testDeleteUserPositive() {

		Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(userBean));	
		//doNothing().when(userRepository).delete(userBean);
		Mockito.doNothing().when(userRepository).delete(Mockito.any(UserBean.class));
		RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/users/delete/vish@gmail.com")
				.accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult resultPositive = mockMvc.perform(positiveRequestBuilder).andReturn();
			MockHttpServletResponse responsePositive = resultPositive.getResponse();
			
			assertEquals(HttpStatus.OK.value(), responsePositive.getStatus());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testDeleteUserNegative() {
		
		Mockito.when(userRepository.findById("vish@gmail.com")).thenReturn(Optional.of(userBean));
		RequestBuilder positiveRequestBuilder = MockMvcRequestBuilders.delete("/users/delete/game@gmail.com")
				.accept(MediaType.APPLICATION_JSON);
		try {
			MvcResult resultNegative = mockMvc.perform(positiveRequestBuilder).andReturn();
			MockHttpServletResponse responseNegative = resultNegative.getResponse();
			assertNotEquals(HttpStatus.OK.value(), responseNegative.getStatus());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
