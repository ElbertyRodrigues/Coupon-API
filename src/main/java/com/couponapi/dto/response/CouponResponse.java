package com.couponapi.dto.response;

import com.couponapi.domain.Coupon;
import com.couponapi.domain.CouponStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Coupon data returned by the API")
public record CouponResponse(

        @Schema(description = "Unique identifier", example = "cef9d1e3-aae5-4ab6-a297-358c6032b1e7")
        UUID id,

        @Schema(description = "Sanitized 6-character alphanumeric code", example = "ABC123")
        String code,

        @Schema(description = "Coupon description")
        String description,

        @Schema(description = "Discount value", example = "0.8")
        BigDecimal discountValue,

        @Schema(description = "Expiration date-time")
        LocalDateTime expirationDate,

        @Schema(description = "Coupon status", example = "ACTIVE")
        CouponStatus status,

        @Schema(description = "Whether the coupon is published", example = "false")
        boolean published,

        @Schema(description = "Whether the coupon has been redeemed", example = "false")
        boolean redeemed
) {
    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getCode(),
                coupon.getDescription(),
                coupon.getDiscountValue(),
                coupon.getExpirationDate(),
                coupon.getStatus(),
                coupon.isPublished(),
                coupon.isRedeemed()
        );
    }
}
