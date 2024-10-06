package com.ecommerce.service.impl;

import com.ecommerce.exception.CartItemException;
import com.ecommerce.exception.UserException;
import com.ecommerce.service.CartItemService;
import com.ecommerce.service.CartService;
import com.ecommerce.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ecommerce.exception.ProductException;
import com.ecommerce.modal.Cart;
import com.ecommerce.modal.CartItem;
import com.ecommerce.modal.Product;
import com.ecommerce.modal.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.request.AddItemRequest;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImplementation.class);

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    public Cart findUserCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId);
        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;
        for (CartItem cartsItem : cart.getCartItems()) {
            totalPrice += cartsItem.getPrice();
            totalDiscountedPrice += cartsItem.getDiscountedPrice();
            totalItem += cartsItem.getQuantity();
        }

        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(cart.getCartItems().size());
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setDiscount(totalPrice - totalDiscountedPrice);
        cart.setTotalItem(totalItem);

        return cartRepository.save(cart);

    }

    @Override
    public void addCartItem(Long userId, AddItemRequest req) throws ProductException {
        try {
            // Find the user's cart and the product to be added
            Cart cart = cartRepository.findByUserId(userId);
            Product product = productService.findProductById(req.getProductId());

            // Handling quantities
            int quantities = req.getQuantity();
            if (quantities == 0){
                quantities = 1;
            }

            // Check if the cart already contains the item
            CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

            if (isPresent == null) {
                // If the item is not in the cart, create a new CartItem
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantities);
                cartItem.setUserId(userId);

                // Set price and size for the new item
                int price = quantities * product.getDiscountedPrice();
                cartItem.setPrice(price);
                cartItem.setSize(req.getSize());

                // Save and add the new CartItem to the cart
                CartItem createdCartItem = cartItemService.createCartItem(cartItem);
                cart.getCartItems().add(createdCartItem);
            } else {
                // If the item is already in the cart, update its quantity and price
                int updatedQuantity = isPresent.getQuantity() + quantities;
                isPresent.setQuantity(updatedQuantity);

                int updatedPrice = updatedQuantity * product.getDiscountedPrice();
                isPresent.setPrice(updatedPrice);

                // Call the existing updateCartItem method
                cartItemService.updateCartItem(userId, isPresent.getId(), isPresent);

                logger.info("Cart item updated: ID={}, New Quantity={}, New Price={}",
                        isPresent.getId(), updatedQuantity, updatedPrice);
            }
        } catch (CartItemException | UserException e) {
            logger.error("Error updating cart item for user {}: {}", userId, e.getMessage());
            throw new ProductException("Failed to update cart item : " + e);
        }
    }

}
