package com.ecommerce.service;

import com.ecommerce.dto.AddressDto;
import com.ecommerce.exception.AddressException;
import com.ecommerce.model.User;

import java.util.List;

public interface AddressService {

    List<AddressDto> getAllAddresses(User user);

    AddressDto getAddressById(Long id, User user) throws AddressException;

    AddressDto addAddress(AddressDto addressDto, User user);

    AddressDto updateAddress(Long id, AddressDto addressDto, User user) throws AddressException;

    void deleteAddress(Long id, User user) throws AddressException;

}
