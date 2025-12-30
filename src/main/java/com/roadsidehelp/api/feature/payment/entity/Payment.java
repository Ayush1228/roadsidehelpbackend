package com.roadsidehelp.api.feature.payment.entity;

import com.roadsidehelp.api.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "payment",
        indexes = {
                @Index(name = "idx_payment_booking", columnList = "booking_id"),
                @Index(name = "idx_payment_user", columnList = "user_id"),
                @Index(name = "idx_payment_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    /** Booking reference (UUID) */
    @Column(name = "booking_id", nullable = false, length = 36)
    private String bookingId;

    /** User who paid (UUID) */
    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    /** Amount paid by user */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /** Currency (INR by default) */
    @Column(nullable = false, length = 10)
    @Builder.Default
    private String currency = "INR";

    /** CASH / ONLINE */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentMethod method;

    /** BOOKING / CANCELLATION_FEE / PENALTY / WALLET_TOPUP */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentPurpose purpose;

    /** PENDING / SUCCESS / FAILED */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    /** Platform commission snapshot */
    @Column(name = "platform_commission", precision = 12, scale = 2)
    private BigDecimal platformCommission;

    /** Amount payable to garage */
    @Column(name = "garage_amount", precision = 12, scale = 2)
    private BigDecimal garageAmount;

    /** Razorpay / Stripe order id */
    @Column(name = "gateway_order_id", length = 100)
    private String gatewayOrderId;

    /** Razorpay / Stripe payment id */
    @Column(name = "gateway_payment_id", length = 100)
    private String gatewayPaymentId;

    /** Signature / verification payload */
    @Column(name = "gateway_signature", length = 255)
    private String gatewaySignature;

    /** Failure reason (if any) */
    @Column(name = "failure_reason", length = 255)
    private String failureReason;

    /** Payment completed timestamp */
    @Column(name = "paid_at")
    private OffsetDateTime paidAt;
}
