package com.roadsidehelp.api.feature.payment.dto;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class SettlementSummaryResponse {

    private BigDecimal totalCollected;

    private BigDecimal totalPaidToGarages;

    private BigDecimal totalPaidToMechanics;

    private BigDecimal platformEarnings;
}
