package com.ezban.eventcoupon.model;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorder.model.TicketOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class EventCouponRestController {
    @Autowired
    private EventCouponService eventCouponService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private EventService eventService;

    @GetMapping("/events/{eventNo}/tickets/event-coupon")
    public ResponseEntity<?> getEventCouponDiscount(@RequestParam String eventCouponCode, @RequestParam Integer ticketOrderNo, @PathVariable Integer eventNo) {
        Optional<EventCoupon> optionalEventCoupon = eventCouponService.getEventCouponByCouponCode(eventCouponCode);
        if (optionalEventCoupon.isEmpty()) {
            return ResponseEntity.ok(Map.of("available", false, "message", "折價券不存在"));
        }

        Event event = eventService.findById(eventNo);
        TicketOrder ticketOrder = ticketOrderService.findById(ticketOrderNo);
        EventCoupon eventCoupon = optionalEventCoupon.get();

        Map<String, Object> validationResponse = eventCouponService.validateEventCoupon(event,eventCoupon, ticketOrder);
        return ResponseEntity.ok(validationResponse);
    }
}