package com.ezban.birthdaycoupon.model;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BirthdayCouponRepository extends JpaRepository<BirthdayCoupon, Integer> {
    // 查找所有有效的優惠券 (birthday_coupon_status = 1)
    List<BirthdayCoupon> findByBirthdayCouponStatus(Byte status);

    // 輔助方法來專門查找有效的優惠券
    default List<BirthdayCoupon> findAllValidCoupons() {
        return findByBirthdayCouponStatus((byte) 1);
    }

    // 輔助方法來專門查找所有失效的優惠券 (birthday_coupon_status = 2)
    default List<BirthdayCoupon> findAllInvalidCoupons() {
        return findByBirthdayCouponStatus((byte) 2);
    }

    // 輔助方法來專門查找還不能使用但已存在於數據庫的優惠券 (birthday_coupon_status = 0)
    default List<BirthdayCoupon> findAllInactiveCoupons() {
        return findByBirthdayCouponStatus((byte) 0);
    }
}
