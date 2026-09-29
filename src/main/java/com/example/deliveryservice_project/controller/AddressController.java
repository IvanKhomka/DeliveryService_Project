package com.example.deliveryservice_project.controller;

import com.example.deliveryservice_project.dto.AddressCreateDto;
import com.example.deliveryservice_project.dto.AddressResponseDto;
import com.example.deliveryservice_project.dto.AddressUpdateDto;
import com.example.deliveryservice_project.service.AddressService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressResponseDto> getAddressesByUser(@RequestParam Long userId) {
        return addressService.getAddressesByUser(userId);
    }

    @GetMapping("/{id}")
    public AddressResponseDto getAddressById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public AddressResponseDto createAddress(@RequestBody AddressCreateDto dto) {
        return addressService.createAddress(dto);
    }

    @PutMapping("/{id}")
    public AddressResponseDto updateAddress(@PathVariable Long id, @RequestBody AddressUpdateDto dto) {
        return addressService.updateAddress(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}