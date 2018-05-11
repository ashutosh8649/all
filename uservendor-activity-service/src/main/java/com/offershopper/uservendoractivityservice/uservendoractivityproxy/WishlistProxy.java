package com.offershopper.uservendoractivityservice.uservendoractivityproxy;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.offershopper.uservendoractivityservice.model.OfferBean;
import com.offershopper.uservendoractivityservice.model.WishlistBean;

@FeignClient("wishlist-database-service")
@RibbonClient(name="wishlist-database-service")
public interface WishlistProxy
{
  @GetMapping("/getWishlist/{userId}")
  public ResponseEntity<List<WishlistBean>> getWishlist(@PathVariable("userId") String userId);

  @DeleteMapping("/delete-wishlist-offer/{offerId}/{userId}")
  public ResponseEntity<Object> deleteWishlistUserOffer(@PathVariable("offerId") String offerId,@PathVariable("userId") String userId);

  @PostMapping("/add-to-wishlist")
  public ResponseEntity<Object> addToWishlist(@RequestBody WishlistBean wishlist);

  @PutMapping("/update-wishlist-offer")
  public ResponseEntity<Object> updateWishlistOffer(@RequestBody WishlistBean wishlistBean);
}
