package com.ezban.birthdaycouponholder.model;

import java.time.LocalDate;
import java.util.List;

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
	
}
