package com.couponapi.infrastructure.persistence;

import com.couponapi.domain.Coupon;
import com.couponapi.domain.CouponRepository;
import com.couponapi.infrastructure.mapper.CouponEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryAdapter implements CouponRepository {

    private final CouponJpaRepository jpaRepository;
    private final CouponEntityMapper mapper;

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity entity = mapper.toEntity(coupon);
        CouponEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Coupon> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}
