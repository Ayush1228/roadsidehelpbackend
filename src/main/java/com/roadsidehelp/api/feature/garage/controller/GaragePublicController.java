package com.roadsidehelp.api.feature.garage.controller;

import com.roadsidehelp.api.config.exception.ApiException;
import com.roadsidehelp.api.config.exception.ErrorCode;
import com.roadsidehelp.api.feature.garage.dto.GarageResponse;
import com.roadsidehelp.api.feature.garage.entity.GarageType;
import com.roadsidehelp.api.feature.garage.service.GaragePublicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/garages/public")
@Tag(
        name = "Public Garages",
        description = "Public APIs to browse, search, and discover verified garages"
)
public class GaragePublicController {

    private final GaragePublicService garageService;

    // Get all public garages
    @Operation(
            summary = "Get all public garages",
            description = "Fetch all VERIFIED and ACTIVE garages visible to public users"
    )
    @ApiResponse(responseCode = "200", description = "Public garages fetched successfully")
    @GetMapping("/get-all")
    public ResponseEntity<List<GarageResponse>> getAllGarages() {
        return ResponseEntity.ok(garageService.getPublicGarages());
    }

    // Get garage by ID
    @Operation(summary = "Get garage details", description = "Fetch a VERIFIED garage by its ID")
    @ApiResponse(responseCode = "200", description = "Garage details fetched successfully")
    @ApiResponse(responseCode = "404",description = "Garage not found or not public")
    @GetMapping("/{garageId}")
    public ResponseEntity<GarageResponse> getGarageById(
            @Parameter(
                    description = "Garage unique identifier",
                    example = "8f9c9e6a-7d3a-4b5b-b9f6-3e4d8c9a0b12",
                    required = true
            )
            @PathVariable String garageId
    ) {
        return ResponseEntity.ok(garageService.getPublicGarageById(garageId));
    }

    // Get nearby public garages
    @Operation(
            summary = "Get nearby garages",
            description = "Fetch VERIFIED garages within a specified radius (in kilometers)"
    )
    @ApiResponse(responseCode = "200", description = "Nearby garages fetched successfully")
    @GetMapping("/nearby")
    public ResponseEntity<List<GarageResponse>> getNearbyGarages(
            @Parameter(
                    description = "Latitude of user location",
                    example = "28.6139",
                    required = true
            )
            @RequestParam double lat,

            @Parameter(
                    description = "Longitude of user location",
                    example = "77.2090",
                    required = true
            )
            @RequestParam double lng,

            @Parameter(
                    description = "Search radius in kilometers",
                    example = "5"
            )
            @RequestParam(required = false) Double radiusKm
    ) {
        return ResponseEntity.ok(
                garageService.getNearbyPublicGarages(lat, lng, radiusKm)
        );
    }

    // Search public garages
    @Operation(summary = "Search public garages",description = "Search VERIFIED garages by city, garage type, or name")
    @ApiResponse(responseCode = "200", description = "Garages fetched successfully")
    @GetMapping("/search")
    public ResponseEntity<List<GarageResponse>> searchGarages(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name
    ) {

        GarageType garageType = null;

        if (type != null) {
            try {
                garageType = GarageType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new ApiException(
                        ErrorCode.INVALID_REQUEST,
                        "Invalid garage type. Allowed values: BIKE, CAR, BOTH"
                );
            }
        }

        return ResponseEntity.ok(
                garageService.searchPublicGarages(city, garageType, name)
        );
    }


    // Get garage reviews (public)
    @Operation(summary = "Get garage reviews", description = "Fetch all public reviews for a VERIFIED garage")
    @ApiResponse(responseCode = "200", description = "Reviews fetched successfully")
    @ApiResponse(responseCode = "404", description = "Garage not found or not public")
    @GetMapping("/{garageId}/reviews")
    public ResponseEntity<?> getGarageReviews(
            @Parameter(
                    description = "Garage unique identifier",
                    example = "8f9c9e6a-7d3a-4b5b-b9f6-3e4d8c9a0b12",
                    required = true
            )
            @PathVariable String garageId
    ) {
        return ResponseEntity.ok(
                garageService.getPublicGarageReviews(garageId)
        );
    }
}
