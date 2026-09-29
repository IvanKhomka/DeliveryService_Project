package com.example.deliveryservice_project.service;

import com.example.deliveryservice_project.dto.RestaurantCreateDto;
import com.example.deliveryservice_project.dto.RestaurantResponseDto;
import com.example.deliveryservice_project.dto.RestaurantUpdateDto;
import com.example.deliveryservice_project.exception.AddressNotFoundException;
import com.example.deliveryservice_project.exception.RestaurantNotFoundException;
import com.example.deliveryservice_project.exception.UserNotFoundException;
import com.example.deliveryservice_project.model.Restaurant;
import com.example.deliveryservice_project.repository.AddressRepository;
import com.example.deliveryservice_project.repository.RestaurantRepository;
import com.example.deliveryservice_project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository,
                                 UserRepository userRepository,
                                 AddressRepository addressRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDto> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponseDto> getRestaurantsByOwner(Long ownerId) {
        if (!userRepository.existsById(ownerId)) {
            throw new UserNotFoundException(ownerId);
        }
        return restaurantRepository.findByOwnerId(ownerId).stream()
                .map(this::toResponseDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseDto getRestaurantById(Long id) {
        return toResponseDto(findOrThrow(id));
    }

    @Override
    @Transactional
    public RestaurantResponseDto createRestaurant(RestaurantCreateDto dto) {
        if (!userRepository.existsById(dto.ownerId())) {
            throw new UserNotFoundException(dto.ownerId());
        }
        if (dto.addressId() != null && !addressRepository.existsById(dto.addressId())) {
            throw new AddressNotFoundException(dto.addressId());
        }
        Restaurant restaurant = new Restaurant(dto.ownerId(), dto.addressId(), dto.name());
        return toResponseDto(restaurantRepository.save(restaurant));
    }

    @Override
    @Transactional
    public RestaurantResponseDto updateRestaurant(Long id, RestaurantUpdateDto dto) {
        Restaurant restaurant = findOrThrow(id);
        if (dto.addressId() != null && !addressRepository.existsById(dto.addressId())) {
            throw new AddressNotFoundException(dto.addressId());
        }
        restaurant.update(dto.name(), dto.addressId());
        return toResponseDto(restaurant);
    }

    @Override
    @Transactional
    public void deleteRestaurant(Long id) {
        restaurantRepository.delete(findOrThrow(id));
    }

    private Restaurant findOrThrow(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id));
    }

    private RestaurantResponseDto toResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getId(), restaurant.getName(), restaurant.getOwnerId(), restaurant.getAddressId());
    }
}