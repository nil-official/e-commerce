package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<>(UserMapper.toUserDto(user), HttpStatus.OK);

    }

    @PatchMapping("/update")
    public ResponseEntity<UserDto> updateUserProfileHandler(@RequestBody UserDto userDto,
                                                            @RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        User updatedUser = userService.updateUserById(user.getId(), userDto);
        return new ResponseEntity<>(UserMapper.toUserDto(updatedUser), HttpStatus.OK);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteUserHandler(@RequestHeader("Authorization") String jwt) throws UserException {

        User user = userService.findUserProfileByJwt(jwt);
        String res = userService.deleteUserById(user.getId());
        return new ResponseEntity<>(new ApiResponse(res, true), HttpStatus.OK);

    }

}
