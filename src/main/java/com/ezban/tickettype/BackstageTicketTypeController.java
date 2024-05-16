package com.ezban.tickettype;

import com.ezban.event.BackstageEventController;
import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backstage/events/{eventNo}/ticketTypes")
public class BackstageTicketTypeController {

    @Autowired
    private TicketTypeService ticketTypeService;
    @Autowired
    private EventService eventService;

    @GetMapping("")
    public String ticketTypes(Model model, Principal principal, @PathVariable Integer eventNo) {
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)){
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }

        List<TicketType> ticketTypes = ticketTypeService.findByEventNo(eventNo);
        model.addAttribute("ticketTypes", ticketTypes);
        model.addAttribute("eventNo", eventNo);
        model.addAttribute("event", eventService.findById(eventNo));

        return "/backstage/event/ticketType";
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<String> addTicketType(Principal principal, @PathVariable Integer eventNo,Model model,@RequestBody Map<String, List<TicketType>> reqMap){
        List<TicketType> ticketTypes = reqMap.get("ticketTypes");
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to access this page.");
        }
        for (TicketType ticketType : ticketTypes) {
            ticketType.setRemainingTicketQty(ticketType.getTicketTypeQty());
            ticketType.setEvent(event);
        }

        ticketTypeService.saveAllAndFlush(ticketTypes);


        return ResponseEntity.ok().body("Ticket types added successfully.");
    }

    @DeleteMapping("/{ticketTypeNo}")
    @ResponseBody
    public ResponseEntity<String> deleteTicketType(Principal principal, @PathVariable Integer eventNo, @PathVariable Integer ticketTypeNo){
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to access this page.");
        }
        TicketType ticketType = ticketTypeService.findById(ticketTypeNo);
        if (ticketType.getEvent().getEventNo() == eventNo){
            ticketTypeService.delete(ticketType);
            return ResponseEntity.ok().body("Ticket type deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket type does not belong to this event.");
        }
    }



}
