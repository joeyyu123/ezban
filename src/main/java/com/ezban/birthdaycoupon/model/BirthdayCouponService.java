package com.ezban.birthdaycoupon.model;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.host.model.HostPassRandom;


@Service
public class BirthdayCouponService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BirthdayCouponRepository birthdayCouponRepository;

    // 每月的第一天午夜執行
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateAndAssignCoupons() {
        LocalDate now = LocalDate.now();
        handleCouponGeneration(now);
    }
    
    
    public int getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1; 
    }

    // 新會員註冊時調用
    public void checkAndGenerateCouponForNewMember(Member member) {
        LocalDate now = LocalDate.now();
        int memberBirthMonth = getMonthFromDate(member.getBirthday()); // 将 Date 转换为月份
        if (member.getMemberStatus() == 1 && memberBirthMonth == now.getMonthValue()) {
            handleCouponGenerationForMember(member, now);
        }
    }

    private void handleCouponGeneration(LocalDate date) {
        int month = date.getMonthValue();
        int lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();

        List<Member> eligibleMembers = memberRepository.findByMemberStatusAndBirthdayMonth((byte) 1, month);
        for (Member member : eligibleMembers) {
            handleCouponGenerationForMember(member, date);
        }
    }

    private void handleCouponGenerationForMember(Member member, LocalDate date) {
        BirthdayCoupon coupon = new BirthdayCoupon();
        coupon.setBirthdayCouponNo(HostPassRandom.generatePassword(16));
        coupon.setBirthdayCouponDiscount(50);
        coupon.setBirthdayCouponStatus((byte) 1);
        coupon.setValidDays(date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth());
        birthdayCouponRepository.save(coupon);
    }
}
