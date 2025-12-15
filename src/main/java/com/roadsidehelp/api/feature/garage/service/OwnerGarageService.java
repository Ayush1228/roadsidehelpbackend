package com.roadsidehelp.api.feature.garage.service;

import com.roadsidehelp.api.config.exception.ApiException;
import com.roadsidehelp.api.config.exception.ErrorCode;
import com.roadsidehelp.api.core.utils.CurrentUser;
import com.roadsidehelp.api.feature.auth.entity.UserAccount;
import com.roadsidehelp.api.feature.auth.repository.UserAccountRepository;
import com.roadsidehelp.api.feature.garage.dto.CreateGarageRequest;
import com.roadsidehelp.api.feature.garage.dto.GarageDocumentRequest;
import com.roadsidehelp.api.feature.garage.dto.GarageResponse;
import com.roadsidehelp.api.feature.garage.dto.UpdateGarageRequest;
import com.roadsidehelp.api.feature.garage.entity.Garage;
import com.roadsidehelp.api.feature.garage.entity.GarageStatus;
import com.roadsidehelp.api.feature.garage.entity.KycStatus;
import com.roadsidehelp.api.feature.garage.mapper.GarageMapper;
import com.roadsidehelp.api.feature.garage.repository.GarageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerGarageService {

    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;
    private final UserAccountRepository userAccountRepository;

    // GET MY GARAGE
    public GarageResponse getMyGarage() {
        String ownerId = CurrentUser.getUserId();

        Garage garage = garageRepository.findByOwnerId(ownerId);
        if (garage == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND, "Garage not found for logged-in user");
        }

        return garageMapper.toResponse(garage);
    }

    // CREATE GARAGE
    @Transactional
    public GarageResponse createGarage(CreateGarageRequest req) {

        String ownerId = CurrentUser.getUserId();

        if (ownerId == null) {
            throw new ApiException(ErrorCode.UNAUTHORIZED, "Unauthorized");
        }

        if (garageRepository.findByOwnerId(ownerId) != null) {
            throw new ApiException(
                    ErrorCode.ENTITY_ALREADY_EXISTS,
                    "Garage already exists"
            );
        }

        UserAccount owner = userAccountRepository.findById(ownerId)
                .orElseThrow(() -> new ApiException(
                        ErrorCode.USER_NOT_FOUND,
                        "User not found"
                ));

        Garage garage = garageMapper.toEntity(req, owner);
        garage.setGarageStatus(GarageStatus.CLOSED);
        garage.setKycStatus(KycStatus.PENDING);
        garage.setVerified(false);

        garageRepository.save(garage);

        return garageMapper.toResponse(garage);
    }

    // UPDATE GARAGE INFO
    @Transactional
    public GarageResponse updateGarage(UpdateGarageRequest req) {
        String ownerId = CurrentUser.getUserId();

        Garage garage = garageRepository.findByOwnerId(ownerId);
        if (garage == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND, "Garage not found");
        }

        garage.setVerified(false);
        garage.setKycStatus(KycStatus.PENDING);

        garageMapper.updateEntity(garage, req);
        garageRepository.save(garage);

        return garageMapper.toResponse(garage);
    }

    // UPDATE DOCUMENTS
    @Transactional
    public GarageResponse updateDocuments(GarageDocumentRequest req) {

        String ownerId = CurrentUser.getUserId();
        Garage garage = garageRepository.findByOwnerId(ownerId);

        if (garage == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND, "Garage not found");
        }

        garage.setLicenseDocumentUrl(req.getLicenseDocumentUrl());
        garage.setGstCertificateUrl(req.getGstCertificateUrl());
        garage.setOwnerIdProofUrl(req.getOwnerIdProofUrl());
        garage.setGaragePhotoUrl(req.getGaragePhotoUrl());

        garage.setVerified(false);
        garage.setKycStatus(KycStatus.PENDING);

        garageRepository.save(garage);

        return garageMapper.toResponse(garage);
    }

    // UPDATE OPEN/CLOSED STATUS
    @Transactional
    public GarageResponse updateOpenStatus(boolean open) {

        String ownerId = CurrentUser.getUserId();
        Garage garage = garageRepository.findByOwnerId(ownerId);

        if (garage == null) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND, "Garage not found");
        }

        if (open && !garage.isVerified()) {
            throw new ApiException(
                    ErrorCode.ACCESS_DENIED,
                    "Garage must be verified before opening"
            );
        }

        garage.setGarageStatus(open ? GarageStatus.OPEN : GarageStatus.CLOSED);
        garageRepository.save(garage);

        return garageMapper.toResponse(garage);
    }
}
