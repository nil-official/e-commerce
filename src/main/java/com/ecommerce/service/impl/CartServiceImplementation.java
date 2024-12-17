package com.ecommerce.service.impl;

import com.ecommerce.exception.CartException;
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
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.request.AddToCartRequest;

@Slf4j
@Service
@AllArgsConstructor
public class CartServiceImplementation implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImplementation.class);

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    @Override
    public void createCart(User user) throws CartException {

        // Check if the user already has a cart
        Cart existingCart = cartRepository.findByUserId(user.getId());
        if (existingCart != null) {
            throw new CartException("Cart already exists for user ID: " + user.getId());
        }

        // Create a new cart for the user
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

    }

    @Override
    public Cart findCart(Long userId) throws CartException {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new CartException("Cart not found for user ID: " + userId);
        }

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
    public void addToCart(Long userId, AddToCartRequest req) throws ProductException, CartException, CartItemException {

        try {
            // Validate inputs
            if (req == null || req.getProductId() == null || req.getSize() == null) {
                throw new ProductException("Invalid request: Product ID and Size must not be null.");
            }

            // Retrieve the user's cart and the product to be added
            Cart cart = cartRepository.findByUserId(userId);
            Product product = productService.findProductById(req.getProductId());

            // Handle quantities
            int quantities = req.getQuantity();
            if (quantities < 0) {
                throw new ProductException("Quantity cannot be negative.");
            } else if (quantities == 0) {
                quantities = 1;
            }

            // Check if the requested size is available
            checkSizeAvailability(product, req.getSize(), quantities);

            // Check if the cart already contains the item
            CartItem existingCartItem = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);

            if (existingCartItem == null) {
                // If the item is not in the cart, create a new CartItem
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(cart);
                cartItem.setQuantity(quantities);
                cartItem.setUserId(userId);
                cartItem.setSize(req.getSize());

                // Set price for the new item
                int price = quantities * product.getDiscountedPrice();
                cartItem.setPrice(price);

                // Save and add the new CartItem to the cart
                CartItem createdCartItem = cartItemService.createCartItem(cartItem);
                cart.getCartItems().add(createdCartItem);
            } else {
                // If the item is already in the cart, update its quantity and price
                int updatedQuantity = existingCartItem.getQuantity() + quantities;

                // Check if the requested size is available
                checkSizeAvailability(product, req.getSize(), updatedQuantity);

                existingCartItem.setQuantity(updatedQuantity);

                int updatedPrice = updatedQuantity * product.getDiscountedPrice();
                existingCartItem.setPrice(updatedPrice);

                // Call the existing updateCartItem method
                cartItemService.updateCartItem(userId, existingCartItem.getId(), existingCartItem);
            }

        } catch (CartException e) {
            throw new ProductException("Failed to process the cart: " + e.getMessage());
        } catch (CartItemException e) {
            throw new ProductException("Failed to process the cart item: " + e.getMessage());
        } catch (UserException e) {
            throw new ProductException("User not found or invalid: " + e.getMessage());
        }

    }

    private void checkSizeAvailability(Product product, String size, int quantity) throws ProductException {
        boolean sizeAvailable = product.getSizes().stream()
                .anyMatch(s -> s.getName().toString().equalsIgnoreCase(size) && s.getQuantity() >= quantity);

        if (!sizeAvailable) {
            throw new ProductException("The requested size '" + size + "' with quantity '"
                    + quantity + "' is out of stock or unavailable.");
        }
    }

    @Override
    public void clearCart(Long userId) throws CartException {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new CartException("Cart not found for user ID: " + userId);
        }

        cart.getCartItems().clear();
        cart.setTotalPrice(0);
        cart.setTotalItem(0);
        cart.setTotalDiscountedPrice(0);
        cart.setDiscount(0);
        cartRepository.save(cart);

    }

}
