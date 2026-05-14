package com.couponapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Payload to create a new coupon")
public record CreateCouponRequest(

        @NotBlank(message = "Code is required")
        @Schema(description = "Alphanumeric code (6 chars after removing special characters)", example = "ABC-123")
        String code,

        @NotBlank(message = "Description is required")
        @Schema(description = "Human-readable description of the coupon", example = "10% off on all products")
        String description,

        @NotNull(message = "Discount value is required")
        @Schema(description = "Discount amount (minimum 0.5)", example = "0.8")
        BigDecimal discountValue,

        @NotNull(message = "Expiration date is required")
        @Schema(description = "Expiration date-time in ISO-8601 format", example = "2025-11-04T17:14:45.180Z")
        LocalDateTime expirationDate,

        @Schema(description = "Whether the coupon is immediately published", example = "false")
        boolean published
) {}
