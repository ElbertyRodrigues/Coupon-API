package com.couponapi.infrastructure.mapper;

import com.couponapi.domain.Coupon;
import com.couponapi.infrastructure.persistence.CouponEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CouponEntityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "deletedAt", source = "deletedAt")
    CouponEntity toEntity(Coupon coupon);

    default Coupon toDomain(CouponEntity entity) {
        return Coupon.reconstitute(
                entity.getId(),
                entity.getCode(),
                entity.getDescription(),
                entity.getDiscountValue(),
                entity.getExpirationDate(),
                entity.getStatus(),
                entity.isPublished(),
                entity.isRedeemed(),
                entity.getDeletedAt()
        );
    }
}
