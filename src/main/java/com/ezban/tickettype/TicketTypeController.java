package com.ezban.tickettype;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events/{eventNo}/tickets")
public class TicketTypeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketTypeService ticketTypeService;




    @GetMapping("")
    public String getEventTickets(Model model, @PathVariable("eventNo") int eventNo) {
        Event event = eventService.findById(eventNo);
        List<TicketType> ticketTypes = ticketTypeService.findByEventNo(eventNo);
        model.addAttribute("event", event);
        model.addAttribute("ticketTypes", ticketTypes);
        return "/frontstage/event/ticketType";
    }


}
