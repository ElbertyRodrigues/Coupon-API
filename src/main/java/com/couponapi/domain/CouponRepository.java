package com.couponapi.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {
    Coupon save(Coupon coupon);
    Optional<Coupon> findById(UUID id);
    List<Coupon> findAll();
}
