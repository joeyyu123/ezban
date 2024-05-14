package com.ezban.birthdaycoupon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.birthdaycoupon.model.BirthdayCouponRepository;

@RestController
@RequestMapping("/api/birthdaycoupon")
public class BirthdayCouponController {

    @Autowired
    private BirthdayCouponRepository couponRepository;

    @GetMapping
    public List<BirthdayCoupon> getAllCoupons() {
        return couponRepository.findAll();
    }
}