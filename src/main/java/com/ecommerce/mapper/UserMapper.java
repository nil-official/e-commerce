package com.ecommerce.mapper;

import com.ecommerce.model.User;
import com.ecommerce.dto.UserDto;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setMobile(user.getMobile());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }

    public static void updateUser(User existingUserData, UserDto updatedUserData) {
        if (updatedUserData.getFirstName() != null) {
            existingUserData.setFirstName(updatedUserData.getFirstName());
        }
        if (updatedUserData.getLastName() != null) {
            existingUserData.setLastName(updatedUserData.getLastName());
        }
        if (updatedUserData.getEmail() != null) {
            existingUserData.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getMobile() != null) {
            existingUserData.setMobile(updatedUserData.getMobile());
        }
    }
}