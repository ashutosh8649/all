package com.offershopper.uservendoractivityservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserBean {
  private String firstName;
  private String lastName;
  @Size(min = 6, max = 12, message = "Password should be atleast of length 6 and max of length 12")
  private String password;
  private String role;
  private String mobileNo;
  @Id
  private String email;
  private String dob;
  private AddressBean address;
  private String gender;
  private int spinCount;
  private Float osCash;
  private AddressBean shopAddress;
  private String vendorMobileNo;
  private List<String> offerIdList = new ArrayList<String>();
  private long timestamp;
  // default constructor
  public UserBean() {
    super();
  }
  // parameterized constructor
  public UserBean(String firstName, String lastName,
      @Size(min = 6, max = 12, message = "Password should be atleast of length 6 and max of length 12") String password,
      String mobileNo, String email, String dob, AddressBean address, String gender, String role, int spinCount,
      Float i, AddressBean shopAddress, String vendorMobileNo, List<String> offerIdList, long timestamp) 
  {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.mobileNo = mobileNo;
    this.email = email;
    this.dob = dob;
    this.address = address;
    this.gender = gender;
    this.role = role;
    this.spinCount = spinCount;
    this.osCash = i;
    this.shopAddress = shopAddress;
    this.vendorMobileNo = vendorMobileNo;
    this.offerIdList = offerIdList;
    this.timestamp = timestamp;
  }

  // getters and setters
  public String getFirstName() 
  {
    return firstName;
  }

  public String getRole() 
  {
    return role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName() 
  {

    return lastName;
  }
  public void setLastName(String lastName) 
  {
    this.lastName = lastName;
  }

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getMobileNo()
  {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) 
  {
    this.mobileNo = mobileNo;
  }

  public String getEmail() 
  {
    return email;
  }

  public void setEmail(String email) 
  {
    this.email = email;
  }

  public String getDob() 
  {
    return dob;
  }

  public void setDob(String dob)
  {
    this.dob = dob;
  }

  public AddressBean getAddress() 
  {
    return address;
  }

  public void setAddress(AddressBean address) 
  {
    this.address = address;
  }

  public String getGender() 
  {
    return gender;
  }

  public void setGender(String gender) 
  {
    this.gender = gender;
  }

  public int getSpinCount() 
  {
    return spinCount;
  }

  public void setSpinCount(int spinCount) 
  {
    this.spinCount = spinCount;
  }

  public AddressBean getShopAddress() 
  {
    return shopAddress;
  }

  public void setShopAddress(AddressBean shopAddress) 
  {
    this.shopAddress = shopAddress;
  }

  public String getVendorMobileNo() 
  {
    return vendorMobileNo;
  }

  public void setVendorMobileNo(String vendorMobileNo)
  {
    this.vendorMobileNo = vendorMobileNo;
  }

  public long getTimestamp() 
  {
    return timestamp;
  }

  public void setTimestamp(long l)
  {
    this.timestamp = l;
  }

  public List<String> getOfferIdList() 
  {
    return offerIdList;
  }

  public void setOfferIdList(List<String> list) 
  {
    this.offerIdList = list;
  }

  public Float getOsCash() 
  {
    return osCash;
  }

  public void setOsCash(Float osCash) 
  {
    this.osCash = osCash;
  }

  @Override
  public String toString() 
  {
    return "UserBean [firstName=" + firstName + ", lastName=" + lastName + ", password=" + password + ", role=" + role
        + ", mobileNo=" + mobileNo + ", email=" + email + ", dob=" + dob + ", address=" + address + ", gender=" + gender
        + ", spinCount=" + spinCount + ", osCash=" + osCash + ", shopAddress=" + shopAddress + ", vendorMobileNo="
        + vendorMobileNo + ", offerIdList=" + offerIdList + ", timestamp=" + timestamp + "]";
  }
}

