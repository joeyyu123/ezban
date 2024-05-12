package com.ezban.eventcoupon.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EventCouponRepository extends JpaRepository<EventCoupon, Integer> {
    @Transactional
    @Modifying
    @Query(value = "delete from event_coupon where event_coupon_no =?1", nativeQuery = true)
    void deleteByEventCouponNo(int eventCouponNo);
    Optional<EventCoupon> findByCouponCode(String couponCode);

    List<EventCoupon> findByHostHostNo(Integer hostNo);
}
