package com.ezban.ticketregistration;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.fieldExample.model.FieldExample;
import com.ezban.registrationform.model.RegistrationForm;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketregistration.model.TicketRegistration;
import com.ezban.ticketregistration.model.TicketRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.List;

@Controller
public class BackstageTicketRegistrationController {

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @Autowired
    private RegistrationFormService registrationFormService;

    @Autowired
    EventService eventService;

    @GetMapping("/backstage/events/{eventNo}/ticket-registrations")
    public String showTicketRegistrations(Principal principal, Model model, @PathVariable("eventNo") String eventNo) {

        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)){
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }

        List<TicketRegistration> ticketRegistrations = ticketRegistrationService.findAllByEventNo(eventNo);
        List<FieldExample> questions = registrationFormService.findByEventNo(eventNo).getQuestions();

        model.addAttribute("questions", questions);
        model.addAttribute("ticketRegistrations", ticketRegistrations);
        model.addAttribute("event", event);

        return "/backstage/event/ticket_registrations";
    }

}
