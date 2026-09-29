package com.example.deliveryservice_project.repository;

import com.example.deliveryservice_project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByClientId(Long clientId);

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByCourierId(Long courierId);
}