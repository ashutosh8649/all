package com.offershopper.vendorprofileservice.feignproxy;

import java.util.Optional;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.offershopper.vendorprofileservice.model.VendorDetailsBean;

/*@RibbonClient(name="user-database-service")*/
@FeignClient(name = "user-database-service")
public interface UserProxy {

/*      
   @GetMapping("/users/details/{userId}")
      public Optional<VendorDetailsBean> findUserByEmail(@PathVariable("userId") String userId);*/
  @GetMapping("/users/details/{email}")
  public ResponseEntity<Optional<VendorDetailsBean>> findUserByEmail(@PathVariable("email") String email);//it was id intially

}
