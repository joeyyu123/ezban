package com.ezban.tickettype;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backstage/events/{eventNo}/ticketTypes")
public class TicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public String ticketTypes(Model model, @PathVariable Integer eventNo) {
        List<TicketType> ticketTypes = ticketTypeService.findByEventNo(eventNo);
        model.addAttribute("ticketTypes", ticketTypes);
        model.addAttribute("eventNo", eventNo);
        model.addAttribute("event", ticketTypeService.findByEventNo(eventNo).get(0).getEvent());

        return "/backstage/event/ticketType";
    }

    @PostMapping("")
    @ResponseBody
    public List<TicketType> addTicketType(@PathVariable Integer eventNo,Model model,@RequestBody Map<String, List<TicketType>> reqMap){
        List<TicketType> ticketTypes = reqMap.get("ticketTypes");
        Event event = eventService.findById(eventNo);
        for (TicketType ticketType : ticketTypes) {
            ticketType.setEvent(event);
        }

        ticketTypes = ticketTypeService.saveAllAndFlush(ticketTypes);


        return ticketTypes;
    }



}
