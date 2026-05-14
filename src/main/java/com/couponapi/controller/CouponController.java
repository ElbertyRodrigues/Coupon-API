package com.couponapi.controller;

import com.couponapi.dto.request.CreateCouponRequest;
import com.couponapi.dto.response.CouponResponse;
import com.couponapi.exception.GlobalExceptionHandler.ErrorResponse;
import com.couponapi.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
@Tag(name = "Coupon", description = "Coupon management endpoints")
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    @Operation(summary = "Create a new coupon")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Coupon created successfully",
                content = @Content(schema = @Schema(implementation = CouponResponse.class))),
        @ApiResponse(responseCode = "400", description = "Validation error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "Business rule violation",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CouponResponse> create(@Valid @RequestBody CreateCouponRequest request) {
        CouponResponse response = couponService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find a coupon by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Coupon found",
                content = @Content(schema = @Schema(implementation = CouponResponse.class))),
        @ApiResponse(responseCode = "404", description = "Coupon not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CouponResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(couponService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Soft-delete a coupon by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Coupon deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Coupon not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422", description = "Coupon already deleted",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        couponService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
