package com.ecommerce.controller;

import com.ecommerce.dto.UserDto;
import com.ecommerce.exception.UserException;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() throws UserException {

        List<User> users = userService.findAllUsers();
        List<UserDto> userDtos = users.stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtos, HttpStatus.OK);

    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) throws UserException {

        User user = userService.findUserById(userId);
        UserDto userDto = UserMapper.toUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> updateUserById(@PathVariable Long userId,
                                                  @RequestBody UserDto userDto) throws UserException {

        User updatedUser = userService.updateUserById(userId, userDto);
        return new ResponseEntity<>(UserMapper.toUserDto(updatedUser), HttpStatus.OK);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long userId) {

        String message = userService.deleteUserById(userId);
        ApiResponse apiResponse = new ApiResponse(message, true);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

}
