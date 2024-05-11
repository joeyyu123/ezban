package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventService;
import com.ezban.event.model.EventStatus;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private TicketTypeService ticketTypeService;

    @GetMapping("")
    public String getEvents(Model model) {
        List<Event> events = eventService.findByEventStatus(EventStatus.PUBLISHED);
        model.addAttribute("events", events);
        return "/frontstage/event/events";
    }

    @GetMapping("{eventNo}")
    public String getEvent(Model model,@PathVariable("eventNo") int eventNo) {
        Event event = eventService.findById(eventNo);
        Integer eventCategoryNo = event.getEventCategory().getEventCategoryNo();

        // 取得同類別的上架活動
        List<Event> events = eventService.findByEventCategoryNo(eventCategoryNo);
        model.addAttribute("event", event);
        model.addAttribute("events", events);
        return "/frontstage/event/event";
    }

}
