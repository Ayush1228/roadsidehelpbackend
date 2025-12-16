package com.roadsidehelp.api.feature.vehicle.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UpdateVehicleRequest {

    private String vehicleNumber;
    private String brand;
    private String model;
    private Integer manufacturingYear;
}

