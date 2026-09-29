package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.AddressCreateDto;
import com.example.deliveryservice_project.dto.AddressResponseDto;
import com.example.deliveryservice_project.dto.AddressUpdateDto;
import com.example.deliveryservice_project.exception.AddressNotFoundException;
import com.example.deliveryservice_project.exception.UserNotFoundException;
import com.example.deliveryservice_project.model.Address;
import com.example.deliveryservice_project.repository.AddressRepository;
import com.example.deliveryservice_project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceImpl(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAddressesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return addressRepository.findByUserId(userId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AddressResponseDto getAddressById(Long id) {
        return toResponseDto(findOrThrow(id));
    }

    @Override
    @Transactional
    public AddressResponseDto createAddress(AddressCreateDto dto) {
        if (dto.userId() != null && !userRepository.existsById(dto.userId())) {
            throw new UserNotFoundException(dto.userId());
        }
        Address address = new Address(
                dto.userId(), dto.city(), dto.street(), dto.building(),
                dto.apartment(), dto.latitude(), dto.longitude());
        return toResponseDto(addressRepository.save(address));
    }

    @Override
    @Transactional
    public AddressResponseDto updateAddress(Long id, AddressUpdateDto dto) {
        Address address = findOrThrow(id);
        address.update(dto.city(), dto.street(), dto.building(),
                dto.apartment(), dto.latitude(), dto.longitude());
        return toResponseDto(address);
    }

    @Override
    @Transactional
    public void deleteAddress(Long id) {
        addressRepository.delete(findOrThrow(id));
    }

    private Address findOrThrow(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    private AddressResponseDto toResponseDto(Address address) {
        return new AddressResponseDto(
                address.getId(), address.getUserId(), address.getCity(), address.getStreet(),
                address.getBuilding(), address.getApartment(), address.getLatitude(), address.getLongitude());
    }
}