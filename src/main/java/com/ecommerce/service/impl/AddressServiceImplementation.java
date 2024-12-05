package com.ecommerce.service.impl;

import com.ecommerce.dto.AddressDto;
import com.ecommerce.exception.AddressException;
import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AddressServiceImplementation implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAllAddresses(User user) {
        List<Address> addresses = addressRepository.findByUser(user);
        return AddressMapper.toAddressDtoList(addresses);
    }

    @Override
    public AddressDto getAddressById(Long id, User user) throws AddressException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AddressException("Unauthorized to access this address");
        }

        return AddressMapper.toAddressDto(address);
    }

    @Override
    public AddressDto addAddress(AddressDto addressDto, User user) {
        Address address = AddressMapper.toAddress(addressDto);
        address.setUser(user);
        Address savedAddress = addressRepository.save(address);
        return AddressMapper.toAddressDto(savedAddress);
    }

    @Override
    public AddressDto updateAddress(Long id, AddressDto addressDto, User user) throws AddressException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AddressException("Unauthorized to update this address");
        }

        address.setFirstName(addressDto.getFirstName());
        address.setLastName(addressDto.getLastName());
        address.setStreetAddress(addressDto.getStreetAddress());
        address.setCity(addressDto.getCity());
        address.setState(addressDto.getState());
        address.setZipCode(addressDto.getZipCode());
        address.setMobile(addressDto.getMobile());

        Address updatedAddress = addressRepository.save(address);
        return AddressMapper.toAddressDto(updatedAddress);
    }

    @Override
    public void deleteAddress(Long id, User user) throws AddressException {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new AddressException("Unauthorized to delete this address");
        }

        addressRepository.delete(address);
    }

}
