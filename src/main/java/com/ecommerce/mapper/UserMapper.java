package com.ecommerce.mapper;

import com.ecommerce.modal.User;
import com.ecommerce.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
//        userDto.setRole(user.getRole());
        userDto.setMobile(user.getMobile());
//        userDto.setAddresses(user.getAddresses());
//        userDto.setPaymentInformation(user.getPaymentInformation());
//        userDto.setRatings(user.getRatings());
//        userDto.setReviews(user.getReviews());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}