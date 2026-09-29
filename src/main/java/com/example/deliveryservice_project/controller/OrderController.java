package com.example.deliveryservice_project.controller;

import com.example.deliveryservice_project.dto.CourierAssignDto;
import com.example.deliveryservice_project.dto.OrderCreateDto;
import com.example.deliveryservice_project.dto.OrderResponseDto;
import com.example.deliveryservice_project.dto.OrderStatusUpdateDto;
import com.example.deliveryservice_project.service.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAnyRole('CLIENT','ADMIN')")
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderCreateDto dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<OrderResponseDto> getOrders(
            @RequestParam(required = false) Long clientId,
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long courierId) {
        return orderService.getOrders(clientId, restaurantId, courierId);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','COURIER','ADMIN')")
    @PatchMapping("/{id}/status")
    public OrderResponseDto updateStatus(@PathVariable Long id, @RequestBody OrderStatusUpdateDto dto) {
        return orderService.updateStatus(id, dto.status());
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER','ADMIN')")
    @PatchMapping("/{id}/courier")
    public OrderResponseDto assignCourier(@PathVariable Long id, @RequestBody CourierAssignDto dto) {
        return orderService.assignCourier(id, dto.courierId());
    }
}