package com.roadsidehelp.api.feature.garage.service;

import com.roadsidehelp.api.config.exception.ApiException;
import com.roadsidehelp.api.config.exception.ErrorCode;
import com.roadsidehelp.api.feature.garage.dto.GarageResponse;
import com.roadsidehelp.api.feature.garage.entity.Garage;
import com.roadsidehelp.api.feature.garage.entity.GarageStatus;
import com.roadsidehelp.api.feature.garage.entity.GarageType;
import com.roadsidehelp.api.feature.garage.entity.KycStatus;
import com.roadsidehelp.api.feature.garage.mapper.GarageMapper;
import com.roadsidehelp.api.feature.garage.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GaragePublicServiceImpl implements GaragePublicService {

    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    // Get all public garages
    @Override
    public List<GarageResponse> getPublicGarages() {

        return garageRepository
                .findPublicGarages(KycStatus.APPROVED)
                .stream()
                // OPEN garages first
                .sorted(Comparator.comparing(
                        g -> g.getGarageStatus() == GarageStatus.OPEN ? 0 : 1
                ))
                .map(garageMapper::toResponse)
                .toList();
    }

    // Get public garage by ID
    @Override
    public GarageResponse getPublicGarageById(String garageId) {

        Garage garage = garageRepository
                .findPublicGarageById(garageId, KycStatus.APPROVED)
                .orElseThrow(() ->
                        new ApiException(
                                ErrorCode.USER_NOT_FOUND,
                                "Garage not found or not public"
                        )
                );

        return garageMapper.toResponse(garage);
    }

    // Nearby public garages
    @Override
    public List<GarageResponse> getNearbyPublicGarages(double lat, double lng, Double radiusKm) {

        double radius = (radiusKm == null) ? 10 : Math.min(radiusKm, 4000);

        return garageRepository
                .findNearbyPublicGarages(lat,  lng,  radius,  KycStatus.APPROVED)
                .stream()
                .map(garageMapper::toResponse)
                .toList();
    }

    // Search public garages
    @Override
    public List<GarageResponse> searchPublicGarages(
            String city,
            GarageType type,
            String name
    ) {

        return garageRepository
                .searchPublicGarages(
                        city,
                        type,
                        name,
                        KycStatus.APPROVED
                )
                .stream()
                .map(garageMapper::toResponse)
                .toList();
    }

    // Get public garage reviews
    @Override
    public Object getPublicGarageReviews(String garageId) {

        Garage garage = garageRepository
                .findPublicGarageById(garageId, KycStatus.APPROVED)
                .orElseThrow(() ->
                        new ApiException(
                                ErrorCode.USER_NOT_FOUND,
                                "Garage not found or not public"
                        )
                );

        // TODO: Replace with ReviewService later
        return List.of(); // placeholder
    }
}
