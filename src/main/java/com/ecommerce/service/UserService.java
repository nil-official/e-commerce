package com.ecommerce.service;

import com.ecommerce.dto.UserDto;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;

import java.util.List;

public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;

    User updateUserById(Long userId, UserDto userDto) throws UserException;

    String deleteUserById(Long userId);

    List<User> findAllUsers();

    User updateUserRoleById(Long userId, boolean promote) throws UserException;

}
