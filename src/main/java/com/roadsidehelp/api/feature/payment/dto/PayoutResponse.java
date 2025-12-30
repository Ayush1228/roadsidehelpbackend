package com.roadsidehelp.api.feature.payment.dto;

import com.roadsidehelp.api.feature.payment.entity.PayoutSource;
import com.roadsidehelp.api.feature.payment.entity.PayoutStatus;
import com.roadsidehelp.api.feature.payment.entity.PayoutTarget;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Builder
public class PayoutResponse {

    private String payoutId;

    private String bookingId;

    private PayoutSource sourceType;
    private PayoutTarget targetType;

    private String targetId;
    private BigDecimal amount;

    private String currency;

    private PayoutStatus status;

    private OffsetDateTime createdAt;
}
