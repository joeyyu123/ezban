package com.ezban.ticketregistration;

import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.ticketregistration.model.TicketRegistration;
import com.ezban.ticketregistration.model.TicketRegistrationService;
import com.ezban.tickettype.model.Views;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class TicketRegistrationController {

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @PostMapping("/events/registrations")
    @JsonView(Views.PublicWithTicketType.class)
    public ResponseEntity<?> registerEvent(@RequestBody String request) {

        TicketRegistration registration = ticketRegistrationService.saveAll(request);

        Integer OrderNo = Integer.valueOf(registration.getTicketOrderNo());
        List<TicketOrderDetail> ticketOrderDetails = ticketOrderDetailService.findByOrderNo(OrderNo);

        return ResponseEntity.ok(ticketOrderDetails);
    }

}
