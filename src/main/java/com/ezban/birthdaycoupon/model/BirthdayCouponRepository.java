package com.ezban.birthdaycoupon.model;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BirthdayCouponRepository extends JpaRepository<BirthdayCoupon, Integer> {

    List<BirthdayCoupon> findByBirthdayCouponStatus(Byte status);

    // 查找有效优惠券
    default List<BirthdayCoupon> findAllValidCoupons() {
        return findByBirthdayCouponStatus((byte) 1);
    }
    
}
