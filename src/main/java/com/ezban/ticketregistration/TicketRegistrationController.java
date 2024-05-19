package com.ezban.ticketregistration;

import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailDto;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.ticketregistration.model.TicketRegistration;
import com.ezban.ticketregistration.model.TicketRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TicketRegistrationController {

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @PostMapping("/events/registrations")
    public ResponseEntity<?> registerEvent(@RequestBody String request) {

        TicketRegistration registration = ticketRegistrationService.saveAll(request);

        Integer OrderNo = Integer.valueOf(registration.getTicketOrderNo());
        List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.findByOrderNo(OrderNo);
        List<TicketOrderDetailDto> ticketOrderDetailDtos = new ArrayList<>();

        for (TicketOrderDetail ticketOrderDetail : ticketOrderDetails) {
            TicketOrderDetailDto ticketOrderDetailDto = convertTicketOrderDetailToDto(ticketOrderDetail);
            ticketOrderDetailDtos.add(ticketOrderDetailDto);
        }


        return ResponseEntity.ok(ticketOrderDetailDtos);
    }


    private TicketOrderDetailDto convertTicketOrderDetailToDto(TicketOrderDetail ticketOrderDetail) {

        TicketOrderDetailDto ticketOrderDetailDto = new TicketOrderDetailDto();

        ticketOrderDetailDto.setTicketOrderNo(ticketOrderDetail.getTicketOrder().getTicketOrderNo());
        ticketOrderDetailDto.setTicketTypeName(ticketOrderDetail.getTicketType().getTicketTypeName());
        ticketOrderDetailDto.setTicketTypePrice(ticketOrderDetail.getTicketType().getTicketTypePrice());
        ticketOrderDetailDto.setTicketTypeQty(ticketOrderDetail.getTicketTypeQty());

        return ticketOrderDetailDto;
    }
}
