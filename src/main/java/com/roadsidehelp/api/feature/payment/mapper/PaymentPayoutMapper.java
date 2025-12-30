package com.roadsidehelp.api.feature.payment.mapper;

import com.roadsidehelp.api.feature.payment.dto.*;
import com.roadsidehelp.api.feature.payment.entity.Payment;
import com.roadsidehelp.api.feature.payment.entity.Payout;
import com.roadsidehelp.api.feature.payment.entity.PayoutSource;
import com.roadsidehelp.api.feature.payment.entity.PayoutTarget;

public class PaymentPayoutMapper {

    private PaymentPayoutMapper() {}

    // Convert Payment entity to PaymentResponse DTO
    public static PaymentResponse toPaymentResponse(Payment payment) {
        if (payment == null) return null;

        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBookingId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .gatewayOrderId(payment.getGatewayOrderId())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    // Convert Payout entity to PayoutResponse DTO
    public static PayoutResponse toPayoutResponse(Payout payout) {
        if (payout == null) return null;

        return PayoutResponse.builder()
                .payoutId(payout.getId())
                .bookingId(payout.getBookingId())
                .sourceType(payout.getSourceType())
                .targetType(payout.getTargetType())
                .targetId(payout.getTargetId())
                .amount(payout.getAmount())
                .currency(payout.getCurrency())
                .status(payout.getStatus())
                .createdAt(payout.getCreatedAt())
                .build();
    }

    // Convert CreatePaymentRequest DTO to Payment entity
    public static Payment toPaymentEntity(CreatePaymentRequest request) {
        if (request == null) return null;

        Payment payment = new Payment();
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setCurrency(request.getCurrency());
        payment.setMethod(request.getMethod());
        return payment;
    }

    // Convert CreatePayoutRequest DTO to Payout entity
    public static Payout toPayoutEntity(CreatePayoutRequest request) {
        if (request == null) return null;

        Payout payout = new Payout();
        payout.setBookingId(request.getBookingId());
        payout.setTargetId(request.getTargetId());
        payout.setAmount(request.getAmount());
        payout.setCurrency(request.getCurrency());
        payout.setSourceType(Enum.valueOf(PayoutSource.class, request.getSourceType()));
        payout.setTargetType(Enum.valueOf(PayoutTarget.class, request.getTargetType()));
        return payout;
    }
}
