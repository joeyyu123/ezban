package com.ezban.ticketorder;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.host.model.HostService;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/backstage/events/{eventNo}")
public class BackstageTicketOrderController {

    @Autowired
    private HostService hostService;

    @Autowired
    private EventService eventService;

    @Autowired
    TicketOrderService ticketOrderService;

    @GetMapping("/orders")
    public String showOrders(Model model, Principal principal,
                             @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                             @RequestParam(value = "ticketOrderNo", required = false) Integer ticketOrderNo,
                             @PathVariable Integer eventNo) {
        // 檢查是否有權限
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }

        List<TicketOrder> ticketOrders = new ArrayList<>();

        // 搜尋特定狀態的訂單
        if (orderStatus!= null) {
            try {
                TicketOrderStatus status = TicketOrderStatus.values()[orderStatus];
                ticketOrders = ticketOrderService.findByEventAndStatus(event, status);
            } catch (ArrayIndexOutOfBoundsException e) {
                model.addAttribute("message", "Invalid order status.");
                return "/backstage/event/warning";
            }
        } else{
            ticketOrders = ticketOrderService.findByEvent(event);
        }

        if(ticketOrderNo != null){
            ticketOrders = ticketOrderService.findByEventNoAndTicketOrderNo(eventNo, ticketOrderNo);
            if(ticketOrders.isEmpty()){
                model.addAttribute("message", "找不到該筆訂單");
                ticketOrders = ticketOrderService.findByEvent(event);
            }
        }


        model.addAttribute("ticketOrders", ticketOrders);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("event", event);
        return "/backstage/event/ticket-orders";
    }
}