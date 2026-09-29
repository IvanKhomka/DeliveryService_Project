package com.example.deliveryservice_project.controller;

import com.example.deliveryservice_project.dto.RestaurantCreateDto;
import com.example.deliveryservice_project.dto.RestaurantResponseDto;
import com.example.deliveryservice_project.dto.RestaurantUpdateDto;
import com.example.deliveryservice_project.service.RestaurantService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public List<RestaurantResponseDto> getAllRestaurants(@RequestParam(required = false) Long ownerId) {
        return ownerId != null
                ? restaurantService.getRestaurantsByOwner(ownerId)
                : restaurantService.getAllRestaurants();
    }

    @GetMapping("/{id}")
    public RestaurantResponseDto getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PostMapping
    public RestaurantResponseDto createRestaurant(@RequestBody RestaurantCreateDto dto) {
        return restaurantService.createRestaurant(dto);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PutMapping("/{id}")
    public RestaurantResponseDto updateRestaurant(@PathVariable Long id, @RequestBody RestaurantUpdateDto dto) {
        return restaurantService.updateRestaurant(id, dto);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }
}