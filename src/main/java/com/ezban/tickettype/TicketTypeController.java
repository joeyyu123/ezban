package com.ezban.tickettype;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events/{eventNo}/tickets")
public class TicketTypeController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @GetMapping("")
    public String getEventTickets(Model model, @PathVariable("eventNo") int eventNo) {
        Event event = eventService.findById(eventNo);
        List<TicketType> ticketTypes = ticketTypeService.findByEventNo(eventNo);
        model.addAttribute("event", event);
        model.addAttribute("ticketTypes", ticketTypes);
        model.addAttribute("now", new Timestamp(System.currentTimeMillis()));
        return "frontstage/event/ticket-type";
    }


    @PostMapping("/reserve")
    @ResponseBody
    public ResponseEntity<?> reserveTicket(Principal principal, @RequestBody Map<String, String>requestBody)  {
        String ticketTypeNo = requestBody.get("ticketTypeNo");
        redisTemplate.opsForSet().add("ticket.type:" +ticketTypeNo+":subscribers:", principal.getName()); // ticket.type:1:reserves: 會員編號

        return ResponseEntity.ok().build();
    }
}
