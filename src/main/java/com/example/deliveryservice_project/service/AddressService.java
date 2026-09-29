package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.AddressCreateDto;
import com.example.deliveryservice_project.dto.AddressResponseDto;
import com.example.deliveryservice_project.dto.AddressUpdateDto;

import java.util.List;

public interface AddressService {
    List<AddressResponseDto> getAddressesByUser(Long userId);

    AddressResponseDto getAddressById(Long id);

    AddressResponseDto createAddress(AddressCreateDto dto);

    AddressResponseDto updateAddress(Long id, AddressUpdateDto dto);

    void deleteAddress(Long id);
}