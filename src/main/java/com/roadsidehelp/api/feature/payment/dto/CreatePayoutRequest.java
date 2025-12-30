package com.roadsidehelp.api.feature.payment.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreatePayoutRequest {

    @NotNull
    private String bookingId;

    @NotNull
    private String targetId;

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private String currency;

    @NotNull
    private String sourceType;

    @NotNull
    private String targetType;
}
