package com.roadsidehelp.api.feature.payment.dto;

import com.roadsidehelp.api.feature.payment.entity.PaymentMethod;
import com.roadsidehelp.api.feature.payment.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Builder
public class PaymentResponse {

    private String paymentId;
    private String bookingId;

    private BigDecimal amount;
    private PaymentMethod method;
    private PaymentStatus status;

    private String gatewayOrderId;

    private OffsetDateTime createdAt;
}
