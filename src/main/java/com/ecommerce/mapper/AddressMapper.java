package com.ecommerce.mapper;

import com.ecommerce.model.Address;
import com.ecommerce.dto.AddressDto;

public class AddressMapper {
    public static Address toAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setStreetAddress(addressDto.getStreetAddress());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());
        address.setMobile(addressDto.getMobile());
        return address;
    }
}