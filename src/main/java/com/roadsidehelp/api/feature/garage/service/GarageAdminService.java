package com.roadsidehelp.api.feature.garage.service;

import com.roadsidehelp.api.feature.garage.dto.GarageResponse;

import java.util.List;

public interface GarageAdminService {

    List<GarageResponse> getPendingGarages();

    GarageResponse approveGarage(String garageId);

    GarageResponse rejectGarage(String garageId, String reason);

    List<GarageResponse> getAllGarages();

    void deleteGarage(String garageId);
}
