package com.ecommerce.controller;

import com.ecommerce.exception.CartException;
import com.ecommerce.exception.CartItemException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.exception.ProductException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import com.ecommerce.request.AddToCartRequest;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.CartService;
import com.ecommerce.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private UserService userService;
    private CartService cartService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCartHandler(@RequestHeader("Authorization") String jwt)
            throws UserException, CartException {

        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findCart(user.getId());
        return new ResponseEntity<>(cart, HttpStatus.OK);

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestHeader("Authorization") String jwt, @RequestBody AddToCartRequest req)
            throws UserException, ProductException, CartException, CartItemException {

        User user = userService.findUserProfileByJwt(jwt);
        cartService.addToCart(user.getId(), req);
        ApiResponse res = new ApiResponse("Item Added To Cart Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse> clearCart(@RequestHeader("Authorization") String jwt)
            throws UserException, CartException {

        User user = userService.findUserProfileByJwt(jwt);
        cartService.clearCart(user.getId());
        ApiResponse res = new ApiResponse("Cart Cleared Successfully", true);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
