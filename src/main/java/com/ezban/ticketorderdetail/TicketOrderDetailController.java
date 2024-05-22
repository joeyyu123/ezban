package com.ezban.ticketorderdetail;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.member.model.Member;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailDto;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
public class TicketOrderDetailController {
    @Autowired
    private HostService hostService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;


    @GetMapping("/events/orders/{orderNo}")
    @ResponseBody
    public ResponseEntity<List<TicketOrderDetailDto>> getTicketOrderDetail(@PathVariable Integer orderNo, Principal principal) {
        TicketOrder ticketOrder = ticketOrderService.findById(orderNo);
        if(ticketOrder == null){
            return ResponseEntity.notFound().build();
        }

        try {
            Member member = ticketOrder.getMember();
            if(!member.getMemberNo().equals(Integer.parseInt(principal.getName()))){
                return ResponseEntity.status(401).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }

        List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.findByOrderNo(orderNo);
        List<TicketOrderDetailDto> ticketOrderDetailDtos = ticketOrderDetailService.convertTicketOrderDetailsToDtos(ticketOrderDetails);
        return ResponseEntity.ok(ticketOrderDetailDtos);
    }


    @GetMapping("/backstage/events/{eventNo}/orders/{orderNo}")
    @ResponseBody
    public ResponseEntity<List<TicketOrderDetailDto>> getTicketOrderDetailForBackstage(@PathVariable Integer orderNo, Principal principal) {
        List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.findByOrderNo(orderNo);
        try {
            Host host = hostService.findHostByHostNo(principal.getName()).orElseThrow();
            if(ticketOrderDetails.stream().noneMatch(t -> t.getTicketType().getEvent().getHost().getHostNo().equals(host.getHostNo()))){
                return ResponseEntity.status(401).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
        List<TicketOrderDetailDto> ticketOrderDetailDtos = ticketOrderDetailService.convertTicketOrderDetailsToDtos(ticketOrderDetails);
        return ResponseEntity.ok(ticketOrderDetailDtos);
    }
}
