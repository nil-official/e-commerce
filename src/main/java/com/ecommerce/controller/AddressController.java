package com.ecommerce.controller;

import com.ecommerce.dto.AddressDto;
import com.ecommerce.exception.AddressException;
import com.ecommerce.exception.UserException;
import com.ecommerce.model.User;
import com.ecommerce.response.ApiResponse;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<AddressDto>> getAllAddressesHandler(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<AddressDto> addresses = addressService.getAllAddresses(user);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressByIdHandler(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String jwt)
            throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        AddressDto address = addressService.getAddressById(id, user);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddressDto> addAddressHandler(@RequestBody @Valid AddressDto addressDto,
                                                        @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        AddressDto createdAddress = addressService.addAddress(addressDto, user);
        return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AddressDto> updateAddressHandler(@PathVariable Long id,
                                                           @RequestBody @Valid AddressDto addressDto,
                                                           @RequestHeader("Authorization") String jwt)
            throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        AddressDto updatedAddress = addressService.updateAddress(id, addressDto, user);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteAddressHandler(@PathVariable Long id,
                                                            @RequestHeader("Authorization") String jwt)
            throws UserException, AddressException {
        User user = userService.findUserProfileByJwt(jwt);
        addressService.deleteAddress(id, user);
        return new ResponseEntity<>(new ApiResponse("Address deleted successfully.", true), HttpStatus.OK);
    }

}
