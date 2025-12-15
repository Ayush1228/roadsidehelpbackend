package com.roadsidehelp.api.feature.garage.service;

import com.roadsidehelp.api.feature.garage.dto.GarageResponse;
import com.roadsidehelp.api.feature.garage.entity.GarageType;

import java.util.List;

public interface GaragePublicService {

    List<GarageResponse> getPublicGarages();

    GarageResponse getPublicGarageById(String garageId);

    List<GarageResponse> getNearbyPublicGarages(double lat, double lng, Double radiusKm);

    List<GarageResponse> searchPublicGarages(String city, GarageType type, String name);

    Object getPublicGarageReviews(String garageId);
}
