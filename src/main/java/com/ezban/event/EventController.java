package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventDto;
import com.ezban.event.model.Service.EventService;
import com.ezban.event.model.EventStatus;
import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.host.model.HostService;
import com.ezban.tickettype.model.TicketTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
                            @RequestParam(required = false) String city,
                            @RequestParam(required = false) Integer categoryNo) {
        List<Event> events;
        List<EventCategory> eventCategories = eventCategoryService.findAll();
        List<String> eventCities = eventService.findDistinctEventCity();
        List<EventDto> dtoList = new ArrayList<>();

        if (city != null && categoryNo != null) {
            events = eventService.findByEventCityAndEventCategory(city, categoryNo, PageRequest.of(0, PAGE_SIZE));
        }
        else if (city != null) {
            events = eventService.findByEventCity(city, PageRequest.of(0, PAGE_SIZE));
        }
        else if (categoryNo != null) {
            events = eventService.findByEventCategoryNo(categoryNo, PageRequest.of(0, PAGE_SIZE));
        } else {
            events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(0, PAGE_SIZE));
        }

        for (Event event : events) {
            dtoList.add(eventService.convertToDto(event));
        }

        model.addAttribute("events", dtoList);
        model.addAttribute("eventCategories", eventCategories);
        model.addAttribute("eventCities", eventCities);
        return "/frontstage/event/events";
    }

    /**
     * 所有活動的分頁功能
     *
     * @param page
     * @return
     */
    @GetMapping("/page/{page}")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getEventsByPage(@PathVariable("page") int page,
                                                          @RequestParam(required = false) String city,
                                                          @RequestParam(required = false) Integer categoryNo) {
        List<Event> events;

        if (city != null && categoryNo != null) {
            events = eventService.findByEventCityAndEventCategory(city, categoryNo, PageRequest.of(page - 1, PAGE_SIZE));
        }
        else if (city != null) {
            events = eventService.findByEventCity(city, PageRequest.of(page - 1, PAGE_SIZE));
        }
        else if (categoryNo != null) {
            events = eventService.findByEventCategoryNo(categoryNo, PageRequest.of(page - 1, PAGE_SIZE));
        } else {
            events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(page - 1, PAGE_SIZE));
        }

        List<EventDto> dtoList = new ArrayList<>();
        for (Event event : events) {
            dtoList.add(eventService.convertToDto(event));
        }
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("{eventNo}")
    public String getEvent(Model model, @PathVariable("eventNo") Integer eventNo) {
        EventDto dto = null;
        dto = (EventDto) redisTemplate.opsForValue().get("event:" + String.valueOf(eventNo));

        if (dto != null) {
            eventService.incrementEventVisitCount(eventNo);
            model.addAttribute("event", dto);
            return "/frontstage/event/event";
        }
        // 如果 Redis 中沒有，則從資料庫中查找
        Event event = eventService.findById(eventNo);

        if (event != null) {
            // 將查找到的活動詳情快取到 Redis
            dto = eventService.convertToDto(event);
            eventService.incrementEventVisitCount(eventNo);
            redisTemplate.opsForValue().set("event:" + eventNo, dto, 1, TimeUnit.HOURS); // 快取一小時
            //            Integer eventCategoryNo = event.getEventCategory().getEventCategoryNo();
            model.addAttribute("event", dto);
//            model.addAttribute("events", eventService.findByEventCategoryNo(eventCategoryNo));
            return "/frontstage/event/event";
        }

        return "redirect:/events";


//            event = eventService.findById(eventNo);
//            Integer eventCategoryNo = event.getEventCategory().getEventCategoryNo();
//
//            // 取得同類別的上架活動
//            List<Event> events = eventService.findByEventCategoryNo(eventCategoryNo);
//            model.addAttribute("event", event);
//            model.addAttribute("events", events);
//            return "/frontstage/event/event";

    }


    /**
     * 取得熱門活動
     *
     * @return
     */
    @GetMapping("/trending")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getTrendingEvents() {
        List<EventDto> dtoList = null;
        List<Event> events = eventService.findTrendingEvents();

        for (Event event : events) {
            dtoList.add(eventService.convertToDto(event));
        }

        return ResponseEntity.ok(dtoList);
    }


}
