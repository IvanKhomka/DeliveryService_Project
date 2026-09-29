package com.example.deliveryservice_project.controller;

import com.example.deliveryservice_project.dto.DishCreateDto;
import com.example.deliveryservice_project.dto.DishResponseDto;
import com.example.deliveryservice_project.dto.DishUpdateDto;
import com.example.deliveryservice_project.service.DishService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public List<DishResponseDto> getDishesByRestaurant(@RequestParam Long restaurantId) {
        return dishService.getDishesByRestaurant(restaurantId);
    }

    @GetMapping("/{id}")
    public DishResponseDto getDishById(@PathVariable Long id) {
        return dishService.getDishById(id);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PostMapping
    public DishResponseDto createDish(@RequestBody DishCreateDto dto) {
        return dishService.createDish(dto);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PutMapping("/{id}")
    public DishResponseDto updateDish(@PathVariable Long id, @RequestBody DishUpdateDto dto) {
        return dishService.updateDish(id, dto);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
    }
}