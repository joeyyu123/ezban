package com.ezban.controller;

import com.ezban.event.model.EventDto;
import com.ezban.event.model.Service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FrontstageController {
    @Autowired
    EventService eventService;

    @GetMapping("")
    public String index(Model model) {
        List<EventDto> trendingEvents = eventService.findTrendingEvents();
        model.addAttribute("events", trendingEvents);
        return "frontstage/index2";
    }

    @GetMapping("/template")
    public String template() {
        return "frontstage/template2";
    }

}
