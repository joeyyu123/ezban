package com.ezban.birthdaycoupon.controller;

import java.security.Principal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.birthdaycouponholder.model.BirthdayCouponHolder;
import com.ezban.birthdaycouponholder.model.BirthdayCouponHolderRepository;
import com.ezban.member.model.Member;

@RestController
@RequestMapping("/api/birthdaycoupon")
public class BirthdayCouponController {

    @Autowired
    private BirthdayCouponHolderRepository couponHolderRepository;

    @GetMapping
    public List<BirthdayCouponHolderDTO> getAllCoupons() {
        List<BirthdayCouponHolder> holders = couponHolderRepository.findAll();
        List<BirthdayCouponHolderDTO> couponList = holders.stream()
            .map(BirthdayCouponHolderDTO::new)
            .sorted(Comparator.comparing(BirthdayCouponHolderDTO::isBirthdayMonth).reversed())
            .collect(Collectors.toList());
        return couponList;
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkMemberCoupons(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("請先登入");
        }

        Integer memberNo = Integer.parseInt(principal.getName());
        List<BirthdayCouponHolder> validCoupons = couponHolderRepository.findValidCouponsForMember(memberNo);
        if (validCoupons.isEmpty()) {
            return ResponseEntity.ok("您現在沒有優惠券但有超讚的商品跟活動在等您");
        } else {
            return ResponseEntity.ok("您現在有一張超讚的生日優惠券還不趕快去使用");
        }
    }
}

class BirthdayCouponHolderDTO {
    private Integer birthdayCouponHolderNo;
    private String memberName;
    private Integer birthdayMonth;
    private Integer birthdayCouponDiscount;
    private String birthdayCouponStatus;
    private String couponUsageStatus;
    private String validDays;

    public BirthdayCouponHolderDTO(BirthdayCouponHolder holder) {
        this.birthdayCouponHolderNo = holder.getBirthdayCouponHolderNo();
        Member member = holder.getMember();
        if (member != null) {
            this.memberName = member.getMemberName();
            LocalDate birthday = convertToLocalDate(member.getBirthday());
            if (birthday != null) {
                this.birthdayMonth = birthday.getMonthValue();
                this.couponUsageStatus = holder.getCouponUsageStatus() == 0 ? "未使用" : "已使用";
                this.birthdayCouponStatus = calculateBirthdayCouponStatus(birthday.getMonthValue(), holder.getCouponUsageStatus());
                this.validDays = calculateValidDays(birthday.getMonthValue(), holder.getCouponUsageStatus());
            }
        }
        this.birthdayCouponDiscount = holder.getBirthdayCoupon().getBirthdayCouponDiscount();
    }

    private LocalDate convertToLocalDate(java.util.Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private String calculateValidDays(int birthdayMonth, Byte usageStatus) {
        LocalDate now = LocalDate.now();
        if (now.getMonthValue() == birthdayMonth && usageStatus == 1) {
            return "請等待下次生日";
        }
        if (now.getMonthValue() == birthdayMonth && usageStatus == 0) {
            YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
            int daysInMonth = yearMonth.lengthOfMonth();
            return String.valueOf(daysInMonth - now.getDayOfMonth() + 1);
        }
        return "請等待下次生日";
    }

    private String calculateBirthdayCouponStatus(int birthdayMonth, Byte usageStatus) {
        if (usageStatus == 1) {
            return "已經使用過了";
        }
        LocalDate now = LocalDate.now();
        return now.getMonthValue() == birthdayMonth ? "可使用" : "已過期";
    }

    public boolean isBirthdayMonth() {
        LocalDate now = LocalDate.now();
        return now.getMonthValue() == this.birthdayMonth;
    }

    // Getters and Setters
    public Integer getBirthdayCouponHolderNo() {
        return birthdayCouponHolderNo;
    }

    public void setBirthdayCouponHolderNo(Integer birthdayCouponHolderNo) {
        this.birthdayCouponHolderNo = birthdayCouponHolderNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Integer getBirthdayMonth() {
        return birthdayMonth;
    }

    public void setBirthdayMonth(Integer birthdayMonth) {
        this.birthdayMonth = birthdayMonth;
    }

    public Integer getBirthdayCouponDiscount() {
        return birthdayCouponDiscount;
    }

    public void setBirthdayCouponDiscount(Integer birthdayCouponDiscount) {
        this.birthdayCouponDiscount = birthdayCouponDiscount;
    }

    public String getBirthdayCouponStatus() {
        return birthdayCouponStatus;
    }

    public void setBirthdayCouponStatus(String birthdayCouponStatus) {
        this.birthdayCouponStatus = birthdayCouponStatus;
    }

    public String getCouponUsageStatus() {
        return couponUsageStatus;
    }

    public void setCouponUsageStatus(String couponUsageStatus) {
        this.couponUsageStatus = couponUsageStatus;
    }

    public String getValidDays() {
        return validDays;
    }

    public void setValidDays(String validDays) {
        this.validDays = validDays;
    }
}
