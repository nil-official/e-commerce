package com.ecommerce.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ecommerce.dto.UserDto;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.Role;
import com.ecommerce.repository.*;
import com.ecommerce.service.UserService;
import com.ecommerce.utility.DtoValidatorUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.ecommerce.config.JwtTokenProvider;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private RoleRepository roleRepository;
    private VerifyTokenRepository verifyTokenRepository;

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            return user.get();
        }
        throw new UserException("user not found with id " + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        System.out.println("user service");
        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);

        System.out.println("email" + email);

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserException("user not exist with email " + email);
        }
        System.out.println("email user" + user.getEmail());
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserById(Long userId, UserDto userDto) throws UserException {

//        if (userDto.getFirstName().isEmpty() ||
//                userDto.getLastName().isEmpty() ||
//                userDto.getEmail().isEmpty()) {
//            throw new UserException("Cannot update user with empty fields");
//        }

        DtoValidatorUtil.validate(userDto);
        // find existing user
        Optional<User> user = userRepository.findById(userId);
        // update user if exists and return
        if (user.isPresent()) {
            UserMapper.updateUser(user.get(), userDto);
            return userRepository.save(user.get());
        }
        // otherwise throw exception
        throw new UserException("user not found with id " + userId);
    }

    @Override
    @Transactional
    public String deleteUserById(Long userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            existingUser.get().getRoles().clear();
            existingUser.get().getAddresses().clear();
            existingUser.get().getPaymentInformation().clear();
            existingUser.get().getReviews().clear();
            existingUser.get().getRatings().clear();
            existingUser.get().getCart().getCartItems().clear();
            existingUser.get().getOrders().forEach(order -> order.getOrderItems().clear());
            existingUser.get().getWishlists().forEach(wishlist -> wishlist.getWishlistItems().clear());
            verifyTokenRepository.deleteByUserId(existingUser.get().getId());
            userRepository.delete(existingUser.get());
            return "User deleted successfully!";
        }
        return "No user found with the given id: " + userId;
    }

    @Override
    public User updateUserRoleById(Long userId, boolean promote) throws UserException {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            Set<Role> roles = new HashSet<>();
            Role userRole;
            if (promote) {
                userRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow(() -> new RuntimeException("Role not found"));
            } else {
                userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Role not found"));
            }
            roles.add(userRole);
            user.setRoles(roles);
            return userRepository.save(user);
        }
        throw new UserException("user not found with id " + userId);
    }

}
