package com.roadsidehelp.api.feature.vehicle.dto;

import lombok.*;

@Getter
@Setter
public class UpdateVehicleRequest {

    private String brand;
    private String model;
    private String registrationNumber;
    private Integer manufacturingYear;
}

