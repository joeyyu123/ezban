package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventDto;
import com.ezban.event.model.EventStatus;
import com.ezban.event.model.Service.EventService;
import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.host.model.HostService;
import com.ezban.tickettype.model.TicketTypeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    @Autowired
    private HostService hostService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventCategoryService eventCategoryService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ObjectMapper mapper;

    private final int PAGE_SIZE = 6; // 每頁顯示的活動數量


    @GetMapping("")
    public String getEvents(Model model,
                            @RequestParam(required = false) List<String> cities,
                            @RequestParam(required = false) List<Integer> categoryNos,
                            @RequestParam(required = false) String eventName,
                            @RequestParam(required = false) String startTime,
                            @RequestParam(required = false) String endTime) {
        List<Event> events;
        List<EventCategory> eventCategories = eventCategoryService.findAll();
        List<String> eventCities = eventService.findDistinctEventCity();
        List<EventDto> dtoList = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            if (startTime != null && !startTime.isEmpty()) {
                startDate = formatter.parse(startTime);
            }
            if (endTime != null && !endTime.isEmpty()) {
                endDate = formatter.parse(endTime);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if ((cities != null && !cities.isEmpty()) || (categoryNos != null && !categoryNos.isEmpty()) || (eventName != null && !eventName.isEmpty()) || (startDate != null) || (endDate != null)) {
            events = eventService.findByEventCityAndEventCategoryAndEventNameAndTime(cities, categoryNos, eventName, startDate, endDate, PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "eventStartTime")));
        } else {
            events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(0, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "eventStartTime")));
        }

        for (Event event : events) {
            dtoList.add(eventService.convertToDto(event));
        }

        model.addAttribute("events", dtoList);
        model.addAttribute("eventCategories", eventCategories);
        model.addAttribute("eventCities", eventCities);
        return "frontstage/event/events";
    }

    @GetMapping("/page/{page}")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getEventsByPage(@PathVariable("page") int page,
                                                          @RequestParam(required = false) List<String> cities,
                                                          @RequestParam(required = false) List<Integer> categoryNos,
                                                          @RequestParam(required = false) String eventName,
                                                          @RequestParam(required = false) String startTime,
                                                          @RequestParam(required = false) String endTime) {
        List<Event> events;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;
        Date endDate = null;
        try {
            if (startTime != null && !startTime.isEmpty()) {
                startDate = formatter.parse(startTime);
            }
            if (endTime != null && !endTime.isEmpty()) {
                endDate = formatter.parse(endTime);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        if ((cities != null && !cities.isEmpty()) || (categoryNos != null && !categoryNos.isEmpty()) || (eventName != null && !eventName.isEmpty()) || (startDate != null) || (endDate != null)) {
            events = eventService.findByEventCityAndEventCategoryAndEventNameAndTime(cities, categoryNos, eventName, startDate, endDate, PageRequest.of(page - 1, PAGE_SIZE,Sort.by(Sort.Direction.ASC, "eventStartTime")));
        } else {
            events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(page - 1, PAGE_SIZE,Sort.by(Sort.Direction.ASC, "eventStartTime")));
        }

        List<EventDto> dtoList = new ArrayList<>();
        for (Event event : events) {
            dtoList.add(eventService.convertToDto(event));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{eventNo}")
    public String getEvent(Model model, @PathVariable("eventNo") Integer eventNo) {
        EventDto dto = eventService.getEventDetails(eventNo);
        if (dto != null) {
            model.addAttribute("event", dto);
            return "frontstage/event/event";
        }
        return "redirect:/events";
    }


    /**
     * 取得熱門活動
     *
     * @return
     */
    @GetMapping("/trending")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getTrendingEvents() {
        List<EventDto> dtoList = eventService.findTrendingEvents();
        return ResponseEntity.ok(dtoList);
    }


}
