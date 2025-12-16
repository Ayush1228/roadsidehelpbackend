package com.roadsidehelp.api.feature.vehicle.service;

import com.roadsidehelp.api.config.exception.ApiException;
import com.roadsidehelp.api.config.exception.ErrorCode;
import com.roadsidehelp.api.config.exception.NotFoundException;
import com.roadsidehelp.api.core.utils.CurrentUser;
import com.roadsidehelp.api.feature.auth.entity.UserAccount;
import com.roadsidehelp.api.feature.auth.repository.UserAccountRepository;
import com.roadsidehelp.api.feature.vehicle.dto.CreateVehicleRequest;
import com.roadsidehelp.api.feature.vehicle.dto.UpdateVehicleRequest;
import com.roadsidehelp.api.feature.vehicle.dto.VehicleResponse;
import com.roadsidehelp.api.feature.vehicle.entity.Vehicle;
import com.roadsidehelp.api.feature.vehicle.mapper.VehicleMapper;
import com.roadsidehelp.api.feature.vehicle.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleUserService {

    private final VehicleRepository vehicleRepository;
    private final UserAccountRepository userRepository;
    private final VehicleMapper vehicleMapper;

    @Transactional
    public VehicleResponse addVehicle(CreateVehicleRequest request) {

        String userId = CurrentUser.getUserId();
        if (userId == null) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "Unauthorized");
        }

        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, "User not found"));

        if (vehicleRepository.existsByVehicleNumber(request.getVehicleNumber())) {
            throw new ApiException(
                    ErrorCode.ENTITY_ALREADY_EXISTS,
                    "Vehicle with this number already exists"
            );
        }

        Vehicle vehicle = vehicleMapper.toEntity(request, user);

        try {
            Vehicle saved = vehicleRepository.save(vehicle);
            return vehicleMapper.toResponse(saved);
        } catch (DataIntegrityViolationException ex) {
            // ✅ 2. Safety net for concurrent requests
            throw new ApiException(
                    ErrorCode.ENTITY_ALREADY_EXISTS,
                    "Vehicle with this number already exists"
            );
        }
    }

    public List<VehicleResponse> getMyVehicles() {
        return vehicleRepository.findByUserId(CurrentUser.getUserId())
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }

    public VehicleResponse updateVehicle(String vehicleId, UpdateVehicleRequest request) {
        Vehicle vehicle = getUserVehicle(vehicleId);
        vehicleMapper.updateEntity(vehicle, request);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    public void deleteVehicle(String vehicleId) {
        vehicleRepository.delete(getUserVehicle(vehicleId));
    }

    private Vehicle getUserVehicle(String vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));

        if (!vehicle.getUser().getId().equals(CurrentUser.getUserId())) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "Unauthorized vehicle access");
        }
        return vehicle;
    }
}
