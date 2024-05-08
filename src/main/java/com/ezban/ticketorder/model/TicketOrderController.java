package com.ezban.ticketorder.model;

import com.ezban.member.model.Member;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events/order")
public class TicketOrderController {
    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @PostMapping("")
    @ResponseBody
    public String buyTicket(Model model, @RequestBody List<Map<String, Integer>> orderDetailList) {
        // 之後改成從session 取得 memberNo
        Member member = new Member();
        member.setMemberNo(1);

        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setMember(member);
        ticketOrder.setTicketOrderTime(new Timestamp(System.currentTimeMillis()));
        ticketOrder.setTicketOrderAmount(ticketOrderService.calculateTotalAmount(orderDetailList));
        ticketOrder.setTicketOrderStatus((byte) 0);
        ticketOrder.setTicketOrderPaymentStatus((byte) 0);
        ticketOrder.setTicketCheckoutAmount(0);


        ticketOrder = ticketOrderService.add(ticketOrder);

        List<TicketOrderDetail> ticketOrderDetails = new ArrayList<TicketOrderDetail>();
        for (Map<String, Integer> orderDetail : orderDetailList) {
            TicketType ticketType = ticketTypeService.findById(orderDetail.get("ticketTypeNo"));

            TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
            ticketOrderDetail.setTicketType(ticketType);
            ticketOrderDetail.setTicketOrder(ticketOrder);
            ticketOrderDetail.setTicketTypeQty(orderDetail.get("ticketTypeQty"));
            ticketOrderDetail.setTicketTypePrice(ticketType.getTicketTypePrice());
            ticketOrderDetail.setIncludedTicketQty(ticketType.getIncludedTickets());

            ticketOrderDetails.add(ticketOrderDetail);
        }

        ticketOrderDetails = ticketOrderDetailService.saveAll(ticketOrderDetails);
        model.addAttribute("ticketOrderDetails", ticketOrderDetails);
        return "success";
    }
}
