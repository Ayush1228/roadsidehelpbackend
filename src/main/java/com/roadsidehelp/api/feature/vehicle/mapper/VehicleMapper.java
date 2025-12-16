package com.roadsidehelp.api.feature.vehicle.mapper;

import com.roadsidehelp.api.feature.auth.entity.UserAccount;
import com.roadsidehelp.api.feature.vehicle.dto.CreateVehicleRequest;
import com.roadsidehelp.api.feature.vehicle.dto.UpdateVehicleRequest;
import com.roadsidehelp.api.feature.vehicle.dto.VehicleResponse;
import com.roadsidehelp.api.feature.vehicle.entity.Vehicle;
import org.springframework.stereotype.Component;

@Component
public class VehicleMapper {

    /* ================= CREATE ================= */

    public Vehicle toEntity(CreateVehicleRequest req, UserAccount user) {
        return Vehicle.builder()
                .user(user)
                .type(req.getType())
                .vehicleNumber(req.getVehicleNumber())
                .brand(req.getBrand())
                .model(req.getModel())
                .manufacturingYear(req.getManufacturingYear())
                .build();
    }

    /* ================= UPDATE ================= */

    public void updateEntity(Vehicle vehicle, UpdateVehicleRequest req) {

        if (req.getBrand() != null) {
            vehicle.setBrand(req.getBrand());
        }

        if (req.getVehicleNumber() != null) {
            vehicle.setVehicleNumber(req.getVehicleNumber());
        }

        if (req.getModel() != null) {
            vehicle.setModel(req.getModel());
        }

        if (req.getManufacturingYear() != null) {
            vehicle.setManufacturingYear(req.getManufacturingYear());
        }
    }

    /* ================= RESPONSE ================= */

    public VehicleResponse toResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .type(vehicle.getType())
                .vehicleNumber(vehicle.getVehicleNumber())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .manufacturingYear(vehicle.getManufacturingYear())
                .build();
    }
}
