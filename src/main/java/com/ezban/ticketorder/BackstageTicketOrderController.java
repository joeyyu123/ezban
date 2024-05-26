package com.ezban.ticketorder;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorder.model.Service.TicketOrderStatusService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequestMapping("/backstage/events/{eventNo}")
public class BackstageTicketOrderController {

    @Autowired
    private HostService hostService;

    @Autowired
    private EventService eventService;

    @Autowired
    TicketOrderService ticketOrderService;

    @Autowired
    private TicketOrderStatusService ticketOrderStatusService;

    @GetMapping("/orders")
    public String showOrders(Model model, Principal principal,
                             @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                             @RequestParam(value = "ticketOrderNo", required = false) Integer ticketOrderNo,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size,
                             @PathVariable Integer eventNo) {
        // 檢查是否有權限
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "backstage/event/warning";
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("ticketOrderTime").descending());
        Page<TicketOrder> ticketOrdersPage;

        if (ticketOrderNo != null) {
            ticketOrdersPage = ticketOrderService.findByEventNoAndTicketOrderNo(eventNo, ticketOrderNo, pageable);
            if (ticketOrdersPage.isEmpty()) {
                model.addAttribute("message", "找不到該筆訂單");
                ticketOrdersPage = ticketOrderService.findByEvent(event, pageable);
            }
        } else if (orderStatus != null) {
            try {
                TicketOrderStatus status = TicketOrderStatus.values()[orderStatus];
                ticketOrdersPage = ticketOrderService.findByEventAndStatus(event, status, pageable);
            } catch (ArrayIndexOutOfBoundsException e) {
                model.addAttribute("message", "Invalid order status.");
                return "backstage/event/warning";
            }
        } else {
            ticketOrdersPage = ticketOrderService.findByEvent(event, pageable);
        }

        model.addAttribute("ticketOrdersPage", ticketOrdersPage);
        model.addAttribute("orderStatus", orderStatus);
        model.addAttribute("event", event);
        return "backstage/event/ticket-orders";
    }

    @DeleteMapping("/orders/{ticketOrderNo}")
    @ResponseBody
    public ResponseEntity<String> cancelOrder(@PathVariable("ticketOrderNo") Integer ticketOrderNo, Principal principal,@PathVariable Integer eventNo) {
        Host host = hostService.findHostByHostNo(principal.getName()).orElseThrow();
        TicketOrder ticketOrder = ticketOrderService.findById(ticketOrderNo);


        if (Objects.equals(eventService.findById(eventNo).getHost(), host)) { // 檢查是否為訂單擁有者
            ticketOrderStatusService.cancelTicketOrder(ticketOrder);
            return ResponseEntity.ok("取消訂單成功");
        } else {
            return ResponseEntity.badRequest().body("Access denied, you are not the owner of this order");
        }
    }
}