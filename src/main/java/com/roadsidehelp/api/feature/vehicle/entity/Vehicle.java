package com.roadsidehelp.api.feature.vehicle.entity;

import com.roadsidehelp.api.core.domain.BaseEntity;
import com.roadsidehelp.api.feature.auth.entity.UserAccount;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type; // BIKE, CAR

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String registrationNumber;

    private Integer manufacturingYear;
}

