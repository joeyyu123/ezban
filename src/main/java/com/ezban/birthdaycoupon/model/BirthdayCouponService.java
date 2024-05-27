package com.ezban.birthdaycoupon.model;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.birthdaycouponholder.model.BirthdayCouponHolder;
import com.ezban.birthdaycouponholder.model.BirthdayCouponHolderRepository;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;

@Service
public class BirthdayCouponService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BirthdayCouponRepository birthdayCouponRepository;
    @Autowired
    private BirthdayCouponHolderRepository birthdayCouponHolderRepository;

    // 每月的第一天午夜執行，一系列優惠券相關處理
    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional
    public void handleMonthlyCouponTasks() {
        LocalDate today = LocalDate.now();

        // 發放優惠券
        issueMonthlyCoupons(today);

        // 更新優惠券的有效天數和狀態
        dailyUpdateCoupons();

        // 更新非當前月的優惠券使用狀態
        updateNonCurrentMonthCouponUsage();

        // 刪除三個月前過期的優惠券持有者記錄
        deleteOldCouponHolders();
    }

    // 發放每月優惠券
    private void issueMonthlyCoupons(LocalDate today) {
        // 查找本月生日並且狀態為1（有效）的會員
        List<Member> eligibleMembers = memberRepository.findMembersByBirthdayMonthAndStatus(today.getMonthValue(), (byte) 1);
        // 對於每一個符合條件的會員，生成並發放生日優惠券
        eligibleMembers.forEach(member -> {
            BirthdayCoupon coupon = new BirthdayCoupon();
            coupon.setBirthdayCouponDiscount(50); // 設置優惠券折扣
            coupon.setBirthdayCouponStatus((byte) 1); // 設置優惠券狀態為有效
            coupon.setValidDays(30); // 設置優惠券有效天數為30天
            BirthdayCoupon savedCoupon = birthdayCouponRepository.save(coupon);
            createCouponHolder(savedCoupon, member); // 創建優惠券持有者記錄
        });
    }

    // 創建優惠券持有者記錄
    private void createCouponHolder(BirthdayCoupon coupon, Member member) {
        BirthdayCouponHolder holder = new BirthdayCouponHolder();
        holder.setBirthdayCoupon(coupon); // 設置優惠券
        holder.setMember(member); // 設置會員
        holder.setCouponUsageStatus((byte) 0); // 設置優惠券使用狀態為未使用
        holder.setValidityDate(LocalDate.now().plusMonths(1)); // 設置有效期為一個月後
        birthdayCouponHolderRepository.save(holder); // 保存優惠券持有者記錄
    }

    // 每日更新優惠券狀態
    private void dailyUpdateCoupons() {
        LocalDate today = LocalDate.now();
        // 查找所有狀態為有效的優惠券
        List<BirthdayCoupon> activeCoupons = birthdayCouponRepository.findByBirthdayCouponStatus((byte) 1);
        // 對於每一個有效的優惠券，更新其有效天數和狀態
        activeCoupons.forEach(coupon -> {
            if (coupon.getValidDays() > 1) {
                coupon.setValidDays(coupon.getValidDays() - 1); // 減少一天有效期
            } else {
                coupon.setValidDays(0);
                coupon.setBirthdayCouponStatus((byte) 2); // 設置優惠券狀態為無效
            }
            birthdayCouponRepository.save(coupon); // 保存更新後的優惠券
        });
    }

    // 更新非當前月的優惠券使用狀態
    private void updateNonCurrentMonthCouponUsage() {
        int currentMonth = LocalDate.now().getMonthValue();
        // 查找所有非當前月份的優惠券持有者記錄
        List<BirthdayCouponHolder> nonCurrentMonthHolders = birthdayCouponHolderRepository.findByValidityDateMonthNot(currentMonth);
        // 對於每一個非當前月份的優惠券持有者記錄，更新其使用狀態為未使用
        nonCurrentMonthHolders.forEach(holder -> {
            holder.setCouponUsageStatus((byte) 0);
            birthdayCouponHolderRepository.save(holder); // 保存更新後的記錄
        });
    }

    // 刪除三個月前過期的優惠券持有者記錄
    private void deleteOldCouponHolders() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        // 刪除有效期在三個月前的優惠券持有者記錄
        birthdayCouponHolderRepository.deleteByValidityDateBefore(threeMonthsAgo);
    }

    // 獲取會員的有效優惠券
    public BirthdayCouponResponse getValidCoupon(Integer memberNo) {
        // 查找會員的有效優惠券
        Object[] nestedResult = birthdayCouponHolderRepository.findValidCouponForMember(memberNo);
        // 驗證查詢結果並創建響應對象
        if (nestedResult != null && nestedResult.length > 0 && nestedResult[0] instanceof Object[]) {
            Object[] result = (Object[]) nestedResult[0]; // 取得內部的 Object[]
            if (result.length >= 5) {
                Integer birthdayCouponNo = (Integer) result[0];
                Integer birthdayCouponDiscount = (Integer) result[1];
                Integer memberNoResult = (Integer) result[2];
                Byte birthdayCouponStatus = (Byte) result[3];
                Byte couponUsageStatus = (Byte) result[4];
                return new BirthdayCouponResponse(
                        birthdayCouponNo,
                        birthdayCouponDiscount,
                        memberNoResult,
                        birthdayCouponStatus,
                        couponUsageStatus
                );
            } else {
                System.out.println("Unexpected result array length: " + result.length);
            }
        } else {
            System.out.println("Query returned null or empty array");
        }
        return null;
    }
}
