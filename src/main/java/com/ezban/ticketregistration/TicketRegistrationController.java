package com.ezban.ticketregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class TicketRegistrationController {

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @PostMapping("/events/registrations")
    public ResponseEntity<String> registerEvent(@RequestBody String request) {
        ticketRegistrationService.saveAll(request);
        return ResponseEntity.ok("Registration successful");
    }

}
