package com.ezban.birthdaycouponholder.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BirthdayCouponHolderRepository extends JpaRepository<BirthdayCouponHolder, Integer> {

	@Modifying
	@Query("DELETE FROM BirthdayCouponHolder bch WHERE bch.validityDate < :cutoffDate")
	void deleteByValidityDateBefore(LocalDate cutoffDate);

    @Modifying
    @Query("UPDATE BirthdayCouponHolder bch SET bch.couponUsageStatus = 2 WHERE bch.validityDate < :date AND bch.couponUsageStatus = 1")
    void markCouponHoldersAsExpired(LocalDate date);

 // 使用@Query注解自定义查询，以确保正确地处理基本类型字段的日期操作
    @Query("SELECT bch FROM BirthdayCouponHolder bch WHERE FUNCTION('MONTH', bch.validityDate) <> :month")
    List<BirthdayCouponHolder> findByValidityDateMonthNot(@Param("month") int month);

    // 找會員適用的生日優惠券
    @Query(value = "SELECT bc.birthday_coupon_no as birthdayCouponNo, " +
                   "bc.birthday_coupon_discount as birthdayCouponDiscount, " +
                   "bch.member_no as memberNo, " +
                   "bch.coupon_usage_status as couponUsageStatus, " +
                   "bc.birthday_coupon_status as birthdayCouponStatus " +
                   "FROM birthday_coupon_holder bch " +
                   "JOIN birthday_coupon bc ON bch.birthday_coupon_no = bc.birthday_coupon_no " +
                   "WHERE bc.birthday_coupon_status = 1 AND bch.coupon_usage_status = 0 " +
                   "AND bch.member_no = :memberNo",
            nativeQuery = true)
    Object[] findValidCouponForMember(@Param("memberNo") Integer memberNo);

    // 產生訂單要更改狀態使用
    Optional<BirthdayCouponHolder> findByBirthdayCoupon(BirthdayCoupon birthdayCoupon);
    
    @Query("SELECT bch FROM BirthdayCouponHolder bch WHERE bch.member.memberNo = :memberNo AND bch.couponUsageStatus = 0 AND bch.validityDate <> 0")
    List<BirthdayCouponHolder> findValidCouponsForMember(@Param("memberNo") Integer memberNo);
}