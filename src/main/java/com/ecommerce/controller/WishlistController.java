package com.ecommerce.controller;

import com.ecommerce.dto.WishlistDto;
import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.model.Wishlist;
import com.ecommerce.request.AddToWishlistRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.UserService;
import com.ecommerce.service.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private UserService userService;
    private WishlistService wishlistService;

    @GetMapping("/")
    public ResponseEntity<Wishlist> getUserWishlist(@RequestHeader("Authorization") String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Wishlist wishlist = wishlistService.findUserWishlist(user.getId());
        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToWishlist(@RequestHeader("Authorization") String jwt,
                                                         @RequestBody AddToWishlistRequest addToWishlistRequest) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        wishlistService.addToWishlist(user.getId(), addToWishlistRequest);
        ApiResponse res = new ApiResponse("Item Added to Wishlist Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/remove/{wishlistItemId}")
    public ResponseEntity<ApiResponse> removeItemFromWishlist(@RequestHeader("Authorization") String jwt,
                                                              @PathVariable Long wishlistItemId) throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        wishlistService.removeFromWishlist(user.getId(), wishlistItemId);
        ApiResponse res = new ApiResponse("Item Removed from Wishlist Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

}
