package com.ezban.eventcoupon.model;

import com.ezban.event.model.Event;
import com.ezban.ticketorder.model.TicketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    /*************************** 獲取指定主辦方的所有活動優惠券 **************************/
    public List<EventCoupon> findByHostHostNo(Integer hostNo) {
        return eventCouponrepository.findByHostHostNo(hostNo);
    }

    /**
     * 根據優惠券代碼獲取活動優惠券
     */
    public Optional<EventCoupon> getEventCouponByCouponCode(String couponCode) {
        return eventCouponrepository.findByCouponCode(couponCode);
    }

    /**
     * 驗證訂單金額以及優惠券是否有效
     */
    public Map<String, Object> validateEventCoupon(Event event, EventCoupon eventCoupon, TicketOrder ticketOrder) {
        if (eventCoupon.getHost().getHostNo() != event.getHost().getHostNo()){
            return Map.of("available", false, "message", "折價券不適用該活動");
        }

        switch (eventCoupon.getEventCouponStatus()) {
            case 0:
                return Map.of("available", false, "message", "折價券活動尚未開始");
            case 2:
                return Map.of("available",false,"message","該優惠已結束");
        }
        if (eventCoupon.getRemainingTimes() <= 0) {
            return Map.of("available", false, "message", "折價券已折抵完畢，下次請早~");
        }
        if (ticketOrder.getTicketOrderAmount() < eventCoupon.getMinSpend()) {
            return Map.of("available", false, "message", "訂單金額需滿足低消" + eventCoupon.getMinSpend() + "元，無法使用折價券");
        }
        return Map.of("available", true, "discount", eventCoupon.getEventCouponDiscount(),"couponCode",eventCoupon.getCouponCode());
    }



}
