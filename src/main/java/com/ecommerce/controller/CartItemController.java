package com.ecommerce.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.User;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart_items")
public class CartItemController {

    private CartItemService cartItemService;
    private UserService userService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(@RequestHeader("Authorization") String jwt, @PathVariable Long cartItemId)
            throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res = new ApiResponse("Item Remove From Cart", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(@RequestHeader("Authorization") String jwt,
                                                          @PathVariable Long cartItemId, @RequestBody CartItem cartItem)
            throws CartItemException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);

    }

}
