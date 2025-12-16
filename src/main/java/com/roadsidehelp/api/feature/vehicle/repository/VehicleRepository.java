package com.roadsidehelp.api.feature.vehicle.repository;

import com.roadsidehelp.api.feature.vehicle.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByUserId(String userId);
    boolean existsByVehicleNumber(String vehicleNumber);
}
