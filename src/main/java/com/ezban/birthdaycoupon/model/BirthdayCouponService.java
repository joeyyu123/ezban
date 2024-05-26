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

	private void issueMonthlyCoupons(LocalDate today) {
		List<Member> eligibleMembers = memberRepository.findMembersByBirthdayMonthAndStatus(today.getMonthValue(),
				(byte) 1);
		eligibleMembers.forEach(member -> {
			BirthdayCoupon coupon = new BirthdayCoupon();
			coupon.setBirthdayCouponDiscount(50);
			coupon.setBirthdayCouponStatus((byte) 1);
			coupon.setValidDays(30);
			BirthdayCoupon savedCoupon = birthdayCouponRepository.save(coupon);
			createCouponHolder(savedCoupon, member);
		});
	}

	private void createCouponHolder(BirthdayCoupon coupon, Member member) {
		BirthdayCouponHolder holder = new BirthdayCouponHolder();
		holder.setBirthdayCoupon(coupon);
		holder.setMember(member);
		holder.setCouponUsageStatus((byte) 0);
		holder.setValidityDate(LocalDate.now().plusMonths(1));
		birthdayCouponHolderRepository.save(holder);
	}

	private void dailyUpdateCoupons() {
		LocalDate today = LocalDate.now();
		List<BirthdayCoupon> activeCoupons = birthdayCouponRepository.findByBirthdayCouponStatus((byte) 1);
		activeCoupons.forEach(coupon -> {
			if (coupon.getValidDays() > 1) {
				coupon.setValidDays(coupon.getValidDays() - 1);
			} else {
				coupon.setValidDays(0);
				coupon.setBirthdayCouponStatus((byte) 2);
			}
			birthdayCouponRepository.save(coupon);
		});
	}

	private void updateNonCurrentMonthCouponUsage() {
		int currentMonth = LocalDate.now().getMonthValue();
		List<BirthdayCouponHolder> nonCurrentMonthHolders = birthdayCouponHolderRepository
				.findByValidityDateMonthNot(currentMonth);
		nonCurrentMonthHolders.forEach(holder -> {
			holder.setCouponUsageStatus((byte) 0);
			birthdayCouponHolderRepository.save(holder);
		});
	}

	private void deleteOldCouponHolders() {
		LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
		birthdayCouponHolderRepository.deleteByValidityDateBefore(threeMonthsAgo);
	}

	public BirthdayCouponResponse getValidCoupon(Integer memberNo) {
		Object[] nestedResult = birthdayCouponHolderRepository.findValidCouponForMember(memberNo);
		if (nestedResult != null && nestedResult.length > 0 && nestedResult[0] instanceof Object[]) {
			Object[] result = (Object[]) nestedResult[0]; // 取得 inner Object[]
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
