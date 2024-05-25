package com.ezban.ticketorder.model.Service;

import com.ezban.event.model.Event;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderPaymentStatus;
import com.ezban.ticketorder.model.TicketOrderRepository;
import com.ezban.ticketorder.model.TicketOrderStatus;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketregistration.model.TicketRegistrationService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TicketOrderStatusService {

    @Autowired
    TicketOrderRepository ticketOrderRepository;

    @Autowired
    TicketOrderService ticketOrderService;

    @Autowired
    TicketTypeRepository ticketTypeRepository;

    @Autowired
    TicketOrderEmailService ticketOrderEmailService;

    @Autowired
    TicketRegistrationService ticketRegistrationService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    /**
     * 取消訂單
     *
     * @Return 取消訂單的結果 1代表取消成功且退款成功 0代表取消成功但無法退款 -1代表無法取消
     */
    @Transactional
    public int cancelTicketOrder(TicketOrder ticketOrder) {
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Timestamp startTime = ticketOrder.getTicketOrderDetails().iterator().next().getTicketType().getEvent().getEventStartTime();
        //活動已開始無法取消，活動開始前三天內取消不退款
        if (currentTime.after(new Timestamp(startTime.getTime() - 3 * 24 * 60 * 60 * 1000))) { //活動開始前三天內取消訂單不退款
            ticketOrder.setTicketOrderStatus(TicketOrderStatus.CANCELED);
            addReamingTicketTypeBack(ticketOrder);
            // 刪除mongoDb中的報名資料
            ticketRegistrationService.deleteByTicketOrderNo(String.valueOf(ticketOrder.getTicketOrderNo()));

            try {
                ticketOrderEmailService.sendEmailToSubscribers(ticketOrder);
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
            }

            ticketOrderRepository.save(ticketOrder);
            return 0;

        } else if (currentTime.before(new Timestamp(startTime.getTime() - 3 * 24 * 60 * 60 * 1000))) {
            ticketOrder.setTicketOrderStatus(TicketOrderStatus.CANCELED);
            ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.REFUNDED);
            addReamingTicketTypeBack(ticketOrder);
            ticketRegistrationService.deleteByTicketOrderNo(String.valueOf(ticketOrder.getTicketOrderNo()));


            try {
                ticketOrderEmailService.sendEmailToSubscribers(ticketOrder);
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
            }

            ticketOrderRepository.save(ticketOrder);
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 取消該活動下的所有訂單
     */
    @Transactional
    public void cancelAllTicketOrder(Event event) {
        List<TicketOrder> ticketOrders = ticketOrderService.findByEvent(event);
        for (TicketOrder ticketOrder : ticketOrders) {
            ticketOrder.setTicketOrderStatus(TicketOrderStatus.CANCELED);
            ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.REFUNDED);
            addReamingTicketTypeBack(ticketOrder);
            try {
                ticketOrderEmailService.sendCancelEmail(ticketOrder, event);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 每分鐘檢查一次是否有訂單超過一小時未付款，並將其訂單狀態改為取消、訂單付款狀態改為逾時，且將所購買的票種數量加回票種剩餘數量中
     */
    @Scheduled(fixedRate = 1, timeUnit = java.util.concurrent.TimeUnit.MINUTES) // 每分鐘檢查一次
    @Transactional
    public void cancelTicketOrderWithSchedule() {
        // 將一小時內未付款的訂單狀態改為取消
        List<TicketOrder> ticketOrders = ticketOrderRepository.findByTicketOrderStatusAndTicketOrderTimeBefore(TicketOrderStatus.PROCESSING, new Timestamp(System.currentTimeMillis() - 60 * 60 * 1000)); // 60 * 60 * 1000 = 一小時

        if (ticketOrders.isEmpty()) {
            return;
        }

        List<TicketOrder> ticketOrdersToCancel = new ArrayList<>();
        for (TicketOrder ticketOrder : ticketOrders) {
            ticketOrder.setTicketOrderStatus(TicketOrderStatus.CANCELED);
            ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.OVERTIME);
            ticketOrdersToCancel.add(ticketOrder);

            // 將取消的訂單中所購買的票種數量加回票種剩餘數量中
            this.addReamingTicketTypeBack(ticketOrder);
        }
        ticketOrderRepository.saveAll(ticketOrdersToCancel);
    }

    /**
     * 將取消的訂單中所購買的票種數量加回票種剩餘數量中
     */
    public void addReamingTicketTypeBack(TicketOrder ticketOrder) {
        // 取得所有訂單明細
        Set<TicketOrderDetail> ticketOrderDetails = ticketOrder.getTicketOrderDetails();

        if (ticketOrderDetails == null) {
            throw new IllegalArgumentException("TicketOrderDetails cannot be null");
        }

        List<TicketType> ticketTypesToAddBack = new ArrayList<>();

        // 將取消的訂單中所購買的票種數量加回票種剩餘數量中
        for (TicketOrderDetail ticketOrderDetail : ticketOrderDetails) {
            TicketType ticketType = ticketOrderDetail.getTicketType();

            if (ticketType == null) {
                throw new IllegalArgumentException("TicketType cannot be null");
            }

            ticketType.setRemainingTicketQty(ticketType.getRemainingTicketQty() + ticketOrderDetail.getTicketTypeQty());
            ticketTypesToAddBack.add(ticketType);
        }
        ticketTypeRepository.saveAll(ticketTypesToAddBack);
    }
}
