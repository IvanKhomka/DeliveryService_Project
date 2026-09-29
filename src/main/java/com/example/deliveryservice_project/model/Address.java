package com.example.deliveryservice_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 20)
    private String building;

    @Column(length = 20)
    private String apartment;

    private Double latitude;
    private Double longitude;

    protected Address() {
    }

    public Address(Long userId, String city, String street, String building,
                   String apartment, Double latitude, Double longitude) {
        this.userId = userId;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void update(String city, String street, String building,
                       String apartment, Double latitude, Double longitude) {
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding() {
        return building;
    }

    public String getApartment() {
        return apartment;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}