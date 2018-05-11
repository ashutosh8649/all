package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uservendoractivityservice.model.UserBean;
/*
 * File name:ProfileProxy
 * Author: Rajneesh,Gursharan,Anand and Yashika
 * Date: 13-April-2018
 * Description: This service is a feign client service interacting with user-database-service and provides the functions to retrieve user details or update them.
 */
@FeignClient(name = "user-database-service", url = "10.151.61.106:9001")
@RibbonClient(name="user-database-service")
public interface ProfileProxy 
{
  @GetMapping("/users/details/{userId}")
  public Optional<UserBean> findUserByEmail(@PathVariable("userId") String userId);

  @PutMapping("/users/update")
  public ResponseEntity<Object> updateProfile(@RequestBody UserBean user);
}
