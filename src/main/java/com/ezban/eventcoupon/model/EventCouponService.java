package com.ezban.eventcoupon.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("eventCouponService")
public class EventCouponService {

    @Autowired
    EventCouponRepository eventCouponrepository;

    /*************************** 新增活動優惠券 **************************/
    public void addEventCoupon(EventCoupon eventCoupon) {
        eventCouponrepository.save(eventCoupon);
    }

    /*************************** 更新活動優惠券 **************************/
    public void updateEventCoupon(EventCoupon eventCouponData) {
        Optional<EventCoupon> optional = eventCouponrepository.findById(eventCouponData.getEventCouponNo());
        if (optional.isPresent()) {
            EventCoupon existingCoupon = optional.get();
            // 確保 hostNo 不被覆蓋
            eventCouponData.setHost(existingCoupon.getHost());

            /*************************** 更新活動優惠券的其他欄位 **************************/
            eventCouponData.setEventCouponName(eventCouponData.getEventCouponName());
            eventCouponData.setCouponCode(eventCouponData.getCouponCode());
            eventCouponData.setUsageLimit(eventCouponData.getUsageLimit());
            eventCouponData.setRemainingTimes(eventCouponData.getRemainingTimes());
            eventCouponData.setMinSpend(eventCouponData.getMinSpend());
            eventCouponData.setEventCouponDiscount(eventCouponData.getEventCouponDiscount());
            eventCouponData.setStartTime(eventCouponData.getStartTime());
            eventCouponData.setEndTime(eventCouponData.getEndTime());
            eventCouponData.setEventCouponStatus(eventCouponData.getEventCouponStatus());
            eventCouponData.setCouponDesc(eventCouponData.getCouponDesc());

            /*************************** 執行更新操作 **************************/
            eventCouponrepository.save(eventCouponData);
        } else {
            throw new RuntimeException("EventCoupon not found with ID: " + eventCouponData.getEventCouponNo());
        }
    }

    /*************************** 刪除活動優惠券 **************************/
//    public void deleteEventCoupon(Integer eventCouponNo) {
//        if (eventCouponrepository.existsById(eventCouponNo))
//            eventCouponrepository.deleteByEventCouponNo(eventCouponNo);
//        // repository.deleteById(eventCouponNo);
//    }

    /*************************** 獲取單一活動優惠券 **************************/
    public EventCoupon getOneEventCoupon(Integer eventCouponNo) {
        Optional<EventCoupon> optional = eventCouponrepository.findById(eventCouponNo);
        return optional.orElse(null);  // 如果值存在就回傳其值，否則回傳other的值
    }

    /*************************** 獲取所有活動優惠券 **************************/
    public List<EventCoupon> getAll() {
        return eventCouponrepository.findAll();
    }

    public List<EventCoupon>getEventCouponsByHostNo(Integer hostNo){
        return eventCouponrepository.findByHostHostNo(hostNo);
    }


}
