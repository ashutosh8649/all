	/*
	 * Name: UserBean
	 * Date: 11-April-2018
	 * Description: The user object
	*/
package com.offershopper.referraldatabaseservice.model;

import javax.annotation.Generated;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class UserBean {
	 //member variables initialization
		@Id
		@Generated(value = { })
	    private String id;
	    private String firstName;
	    private String lastName;
	    private String password;
	    private String mobileNo;
	    private String email;
	    private String dob;
	    private AddressBean address;
	    private String gender;
	    private int spinCount;
	    private int creditPoint;
	    private AddressBean shopAddress;
	    private String vendorMobileNo;
	    private String timestamp;
	    private String role;
	    
	    //default constructor
		public UserBean() {
			super();	
		}
		//parameterized constructor
		public UserBean( String firstName, String lastName, String password, String mobileNo, String email,
				String dob, AddressBean address, String gender, int spinCount, int creditPoint, AddressBean shopAddress,
				String vendorMobileNo, String timestamp) {
			super();
		
			this.firstName = firstName;
			this.lastName = lastName;
			this.password = password;
			this.mobileNo = mobileNo;
			this.email = email;
			this.dob = dob;
			this.address = address;
			this.gender = gender;
			this.spinCount = spinCount;
			this.creditPoint = creditPoint;
			this.shopAddress = shopAddress;
			this.vendorMobileNo = vendorMobileNo;
			this.timestamp = timestamp;
		}
		//getters and setters
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getMobileNo() {
			return mobileNo;
		}
		public void setMobileNo(String mobileNo) {
			this.mobileNo = mobileNo;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getDob() {
			return dob;
		}
		public void setDob(String dob) {
			this.dob = dob;
		}
		public AddressBean getAddress() {
			return address;
		}
		public void setAddress(AddressBean address) {
			this.address = address;
		}
		public String getGender() {
			return gender;
		}
		public void setGender(String gender) {
			this.gender = gender;
		}
		public int getSpinCount() {
			return spinCount;
		}
		public void setSpinCount(int spinCount) {
			this.spinCount = spinCount;
		}
		public int getCreditPoint() {
			return creditPoint;
		}
		public void setCreditPoint(int creditPoint) {
			this.creditPoint = creditPoint;
		}
		public AddressBean getShopAddress() {
			return shopAddress;
		}
		public void setShopAddress(AddressBean shopAddress) {
			this.shopAddress = shopAddress;
		}
		public String getVendorMobileNo() {
			return vendorMobileNo;
		}
		public void setVendorMobileNo(String vendorMobileNo) {
			this.vendorMobileNo = vendorMobileNo;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		
		
		
}
