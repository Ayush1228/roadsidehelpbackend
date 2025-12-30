-- ========================================
-- PAYMENT
-- ========================================
CREATE TABLE IF NOT EXISTS payment (
    id CHAR(36) NOT NULL PRIMARY KEY,
    booking_id CHAR(36) NOT NULL,
    user_id CHAR(36) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    method VARCHAR(20) NOT NULL,
    purpose VARCHAR(30) NOT NULL,
    status VARCHAR(20) NOT NULL,
    platform_commission DECIMAL(12,2),
    garage_amount DECIMAL(12,2),
    gateway_order_id VARCHAR(100),
    gateway_payment_id VARCHAR(100),
    gateway_signature VARCHAR(255),
    failure_reason VARCHAR(255),
    paid_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_payment_booking FOREIGN KEY (booking_id)
        REFERENCES booking(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id)
        REFERENCES user_account(id)
        ON DELETE CASCADE
    );

CREATE INDEX idx_payment_booking ON payment(booking_id);
CREATE INDEX idx_payment_user ON payment(user_id);
CREATE INDEX idx_payment_status ON payment(status);

-- ========================================
-- PAYOUT
-- ========================================
CREATE TABLE IF NOT EXISTS payout (
    id CHAR(36) NOT NULL PRIMARY KEY,
    booking_id CHAR(36) NOT NULL,
    payment_id CHAR(36) NOT NULL,
    source_type VARCHAR(20) NOT NULL,
    target_type VARCHAR(20) NOT NULL,
    target_id CHAR(36) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    currency VARCHAR(10) NOT NULL DEFAULT 'INR',
    status VARCHAR(20) NOT NULL,
    gateway_transfer_id VARCHAR(100),
    failure_reason VARCHAR(255),
    released_at TIMESTAMP NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_payout_booking FOREIGN KEY (booking_id)
        REFERENCES booking(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_payout_payment FOREIGN KEY (payment_id)
        REFERENCES payment(id)
        ON DELETE CASCADE
    );

CREATE INDEX idx_payout_booking ON payout(booking_id);
CREATE INDEX idx_payout_payment ON payout(payment_id);
CREATE INDEX idx_payout_source_type ON payout(source_type);
CREATE INDEX idx_payout_target_type ON payout(target_type);
CREATE INDEX idx_payout_target_id ON payout(target_id);
CREATE INDEX idx_payout_status ON payout(status);
