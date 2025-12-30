package com.roadsidehelp.api.feature.payment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentVerifyRequest {

    @NotBlank
    private String gatewayOrderId;

    @NotBlank
    private String gatewayPaymentId;

    @NotBlank
    private String gatewaySignature;
}
