package com.roadsidehelp.api.feature.vehicle.dto;

import com.roadsidehelp.api.feature.vehicle.entity.VehicleType;
import lombok.*;

@Getter
@Setter
@Builder
public class VehicleResponse {

    private String id;
    private VehicleType type;
    private String brand;
    private String model;
    private String registrationNumber;
    private Integer manufacturingYear;
}
