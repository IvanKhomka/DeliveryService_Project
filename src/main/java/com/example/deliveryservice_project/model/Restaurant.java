package com.example.deliveryservice_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(nullable = false, length = 150)
    private String name;

    protected Restaurant() {
    }

    public Restaurant(Long ownerId, Long addressId, String name) {
        this.ownerId = ownerId;
        this.addressId = addressId;
        this.name = name;
    }

    public void update(String name, Long addressId) {
        this.name = name;
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getName() {
        return name;
    }
}