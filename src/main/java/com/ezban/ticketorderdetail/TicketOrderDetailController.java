package com.ezban.ticketorderdetail;

import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TicketOrderDetailController {

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @GetMapping("/events/orders/{orderNo}")
    @ResponseBody
    public ResponseEntity<List<TicketOrderDetail>> getTicketOrderDetail(@PathVariable Integer orderNo) {
        return ResponseEntity.ok(ticketOrderDetailService.findByOrderNo(orderNo));
    }
}
