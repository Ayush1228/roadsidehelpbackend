package com.roadsidehelp.api.feature.payment.entity;

import com.roadsidehelp.api.core.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
        name = "payout",
        indexes = {
                @Index(name = "idx_payout_booking", columnList = "booking_id"),
                @Index(name = "idx_payout_payment", columnList = "payment_id"),
                @Index(name = "idx_payout_source_type", columnList = "source_type"),
                @Index(name = "idx_payout_target_type", columnList = "target_type"),
                @Index(name = "idx_payout_target_id", columnList = "target_id"),
                @Index(name = "idx_payout_status", columnList = "status")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payout extends BaseEntity {

    /** Booking reference (UUID) */
    @Column(name = "booking_id", nullable = false, length = 36)
    private String bookingId;

    //** Payment reference (USER → PLATFORM)
    @Column(name = "payment_id", nullable = false, length = 36)
    private String paymentId;

    /** Who is paying (PLATFORM / GARAGE) */
    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 20)
    private PayoutSource sourceType;

    /** Who is receiving (GARAGE / MECHANIC) */
    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", nullable = false, length = 20)
    private PayoutTarget targetType;

    /** Receiver id (garageId / mechanicId) */
    @Column(name = "target_id", nullable = false, length = 36)
    private String targetId;

    /** Amount released */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    /** Currency */
    @Column(nullable = false, length = 10)
    @Builder.Default
    private String currency = "INR";

    /** PENDING / RELEASED / FAILED */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PayoutStatus status;

    /** Razorpay / Bank transfer id */
    @Column(name = "gateway_transfer_id", length = 100)
    private String gatewayTransferId;

    /** Failure reason (if any) */
    @Column(name = "failure_reason", length = 255)
    private String failureReason;

    /** When payout was released */
    @Column(name = "released_at")
    private OffsetDateTime releasedAt;

    /** Business validation */
    public boolean isValidFlow() {
        return (sourceType == PayoutSource.PLATFORM && targetType == PayoutTarget.GARAGE)
                || (sourceType == PayoutSource.GARAGE && targetType == PayoutTarget.MECHANIC);
    }
}
