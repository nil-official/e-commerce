package com.ecommerce.controller;

import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    @PatchMapping("/promote/{userId}")
    public ResponseEntity<User> promoteUser(@PathVariable Long userId) throws UserException {
        User updatedUser = userService.updateUserRoleById(userId, true);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PatchMapping("/demote/{userId}")
    public ResponseEntity<User> demoteUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserException {
        User self = userService.findUserProfileByJwt(jwt);
        if(Objects.equals(self.getId(), userId)) {
            return new ResponseEntity<>(self, HttpStatus.OK);
        }
        User updatedUser = userService.updateUserRoleById(userId, false);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

}
