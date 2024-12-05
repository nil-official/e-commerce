package com.ecommerce.mapper;

import com.ecommerce.model.Address;
import com.ecommerce.dto.AddressDto;
import com.ecommerce.model.OrderAddress;

import java.util.List;
import java.util.stream.Collectors;

public class AddressMapper {

    // Maps AddressDto to Address entity
    public static Address toAddress(AddressDto addressDto) {
        Address address = new Address();
        address.setId(addressDto.getId());
        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setStreetAddress(addressDto.getStreetAddress());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());
        address.setMobile(addressDto.getMobile());
        return address;
    }

    // Maps Address entity to AddressDto
    public static AddressDto toAddressDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setFirstName(address.getFirstName());
        addressDto.setLastName(address.getLastName());
        addressDto.setStreetAddress(address.getStreetAddress());
        addressDto.setCity(address.getCity());
        addressDto.setState(address.getState());
        addressDto.setZipCode(address.getZipCode());
        addressDto.setMobile(address.getMobile());
        return addressDto;
    }

    // Maps a list of Address entities to a list of AddressDto (get all addresses)
    public static List<AddressDto> toAddressDtoList(List<Address> addresses) {
        return addresses.stream()
                .map(AddressMapper::toAddressDto)
                .collect(Collectors.toList());
    }

    // Maps orderAddress to Address entity
    public static Address toAddress(OrderAddress orderAddress) {
        Address address = new Address();
        address.setFirstName(orderAddress.getFirstName());
        address.setLastName(orderAddress.getLastName());
        address.setStreetAddress(orderAddress.getStreetAddress());
        address.setCity(orderAddress.getCity());
        address.setState(orderAddress.getState());
        address.setZipCode(orderAddress.getZipCode());
        address.setMobile(orderAddress.getMobile());
        return address;
    }

}
