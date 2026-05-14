package com.couponapi.domain;

import com.couponapi.exception.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Coupon {

    private static final int CODE_LENGTH = 6;
    private static final BigDecimal MIN_DISCOUNT_VALUE = new BigDecimal("0.5");

    private UUID id;
    private String code;
    private String description;
    private BigDecimal discountValue;
    private LocalDateTime expirationDate;
    private CouponStatus status;
    private boolean published;
    private boolean redeemed;
    private LocalDateTime deletedAt;

    public static Coupon create(
            String rawCode,
            String description,
            BigDecimal discountValue,
            LocalDateTime expirationDate,
            boolean published
    ) {
        String sanitizedCode = sanitizeCode(rawCode);
        validateCode(sanitizedCode);
        validateDiscountValue(discountValue);
        validateExpirationDate(expirationDate);

        Coupon coupon = new Coupon();
        coupon.id = UUID.randomUUID();
        coupon.code = sanitizedCode;
        coupon.description = description;
        coupon.discountValue = discountValue;
        coupon.expirationDate = expirationDate;
        coupon.published = published;
        coupon.redeemed = false;
        coupon.status = CouponStatus.ACTIVE;
        coupon.deletedAt = null;
        return coupon;
    }

    public static Coupon reconstitute(
            UUID id,
            String code,
            String description,
            BigDecimal discountValue,
            LocalDateTime expirationDate,
            CouponStatus status,
            boolean published,
            boolean redeemed,
            LocalDateTime deletedAt
    ) {
        Coupon coupon = new Coupon();
        coupon.id = id;
        coupon.code = code;
        coupon.description = description;
        coupon.discountValue = discountValue;
        coupon.expirationDate = expirationDate;
        coupon.status = status;
        coupon.published = published;
        coupon.redeemed = redeemed;
        coupon.deletedAt = deletedAt;
        return coupon;
    }

    public void delete() {
        if (isDeleted()) {
            throw new BusinessException("Coupon is already deleted.");
        }
        this.deletedAt = LocalDateTime.now();
        this.status = CouponStatus.DELETED;
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }

    private static String sanitizeCode(String rawCode) {
        if (rawCode == null) return "";
        return rawCode.replaceAll("[^a-zA-Z0-9]", "");
    }

    private static void validateCode(String code) {
        if (code == null || code.length() != CODE_LENGTH) {
            throw new BusinessException(
                "Coupon code must be exactly " + CODE_LENGTH +
                " alphanumeric characters after removing special characters."
            );
        }
    }

    private static void validateDiscountValue(BigDecimal value) {
        if (value == null || value.compareTo(MIN_DISCOUNT_VALUE) < 0) {
            throw new BusinessException(
                "Discount value must be at least " + MIN_DISCOUNT_VALUE + "."
            );
        }
    }

    private static void validateExpirationDate(LocalDateTime expirationDate) {
        if (expirationDate == null || !expirationDate.isAfter(LocalDateTime.now())) {
            throw new BusinessException("Expiration date must be in the future.");
        }
    }

    public UUID getId() { return id; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public LocalDateTime getExpirationDate() { return expirationDate; }
    public CouponStatus getStatus() { return status; }
    public boolean isPublished() { return published; }
    public boolean isRedeemed() { return redeemed; }
    public LocalDateTime getDeletedAt() { return deletedAt; }
}
