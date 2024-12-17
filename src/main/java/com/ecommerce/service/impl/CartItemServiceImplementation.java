package com.ecommerce.service.impl;

import java.util.Optional;

import com.ecommerce.service.CartItemService;
import com.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartItemRepository;
import com.ecommerce.repository.CartRepository;

@AllArgsConstructor
@Service
public class CartItemServiceImplementation implements CartItemService {

    private static final Logger logger = LoggerFactory.getLogger(CartItemServiceImplementation.class);

    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    @Override
    public CartItem createCartItem(CartItem cartItem) {

        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice() * cartItem.getQuantity());
        return cartItemRepository.save(cartItem);

    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {

        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if (user.getId().equals(userId)) {

            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getQuantity() * item.getProduct().getDiscountedPrice());

            return cartItemRepository.save(item);

        } else {
            throw new CartItemException("You can't update  another users cart_item");
        }

    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        return cartItemRepository.isCartItemExist(cart, product, size, userId);
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {

        System.out.println("userId- " + userId + " cartItemId " + cartItemId);

        CartItem cartItem = findCartItemById(cartItemId);

        User user = userService.findUserById(cartItem.getUserId());
        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItem.getId());
        } else {
            throw new UserException("you can't remove another users item");
        }

    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {

        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isEmpty()) {
            throw new CartItemException("cartItem not found with id : " + cartItemId);
        }
        return opt.get();

    }

}
