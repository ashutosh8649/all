package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/*
 * File name:RoleProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with uaa-server and provides the functions to verify token and vendor registration.
 */
@FeignClient("uaa-server")
@RibbonClient(name="uaa-server")
public interface RoleProxy
{
  @GetMapping("/token/register/vendor/{uname}/{role}")
  public ResponseEntity<String> vendorRegistration(@PathVariable(value="uname")  String uname, @PathVariable (value="role")  String role);

  @GetMapping("/verifytoken/{applicationToken}")
  public String verifyTokenComingFromService(@PathVariable(value="applicationTokenhttps://github.com/singhrajneesh/VendorPage.git")  String applicationToken);
}
