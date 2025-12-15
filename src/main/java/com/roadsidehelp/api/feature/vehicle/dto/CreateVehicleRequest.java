package com.roadsidehelp.api.feature.vehicle.dto;

import com.roadsidehelp.api.feature.vehicle.entity.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateVehicleRequest {

    @NotNull
    private VehicleType type;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    private String registrationNumber;
    private Integer manufacturingYear;
}

