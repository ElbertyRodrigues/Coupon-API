package com.couponapi.service;

import com.couponapi.domain.Coupon;
import com.couponapi.domain.CouponRepository;
import com.couponapi.domain.CouponStatus;
import com.couponapi.dto.request.CreateCouponRequest;
import com.couponapi.dto.response.CouponResponse;
import com.couponapi.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponResponse create(CreateCouponRequest request) {
        Coupon coupon = Coupon.create(
                request.code(), request.description(),
                request.discountValue(), request.expirationDate(), request.published()
        );
        return CouponResponse.from(couponRepository.save(coupon));
    }

    public List<CouponResponse> findAll() {
        return couponRepository.findAll().stream()
                .filter(c -> c.getStatus() == CouponStatus.ACTIVE)
                .map(CouponResponse::from)
                .toList();
    }

    public CouponResponse findById(UUID id) {
        return CouponResponse.from(couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id)));
    }

    public void delete(UUID id) {
        Coupon coupon = couponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Coupon not found with id: " + id));
        coupon.delete();
        couponRepository.save(coupon);
    }
}
