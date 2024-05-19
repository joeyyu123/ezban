package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventDto;
import com.ezban.event.model.Service.EventService;
import com.ezban.event.model.EventStatus;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.host.model.HostService;
import com.ezban.tickettype.model.TicketTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String getEvents(Model model) {
        List<Event> events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(0, PAGE_SIZE));
        List<EventDto> dtoList = new ArrayList<>();
        for (Event event : events) {
            dtoList.add(convertToDto(event));
        }

        model.addAttribute("events", dtoList);
        return "/frontstage/event/events";
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
            var mapper = new ObjectMapper();
            dto = convertToDto(event);
            eventService.incrementEventVisitCount(eventNo);
            redisTemplate.opsForValue().set("event:" + eventNo, dto); // 快取一小時
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

    @GetMapping("/page/{page}")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getEventsByPage(@PathVariable("page") int page) {
        List<Event> events = eventService.findByEventStatus(EventStatus.PUBLISHED, PageRequest.of(page - 1, PAGE_SIZE));
        List<EventDto> dtoList = new ArrayList<>();
        for (Event event : events) {
            dtoList.add(convertToDto(event));
        }
        return ResponseEntity.ok(dtoList);
    }

    /**
     * 取得熱門瀏覽次數活動
     * @return
     */
    @GetMapping("/trending")
    @ResponseBody
    public ResponseEntity<List<EventDto>> getTrendingEvents() {
        List<EventDto> dtoList = null;
//        List<Event> events = eventService;

        return ResponseEntity.ok(dtoList);
    }

    private EventDto convertToDto(Event event) {
        EventDto dto = new EventDto();
        dto.setEventNo(event.getEventNo());
        dto.setEventImg(event.getEventImgBase64());
        dto.setEventName(event.getEventName());
        dto.setEventCategoryName(event.getEventCategory().getEventCategoryName());
        dto.setHostName(event.getHost().getHostName());
        dto.setEventCity(event.getEventCity());
        dto.setEventDesc(event.getEventDesc());
        dto.setEventDetailedAddress(event.getEventDetailedAddress());
        dto.setEventAddTime(event.getEventAddTime());
        dto.setEventRemoveTime(event.getEventRemoveTime());
        dto.setEventStartTime(event.getEventStartTime());
        dto.setEventEndTime(event.getEventEndTime());
        dto.setRegisteredCount(event.getRegisteredCount());
        dto.setEventStatus(event.getEventStatus());
        dto.setTotalRating(event.getTotalRating());
        dto.setEventRatingCount(event.getEventRatingCount());
        dto.setVisitCount(event.getVisitCount());
        return dto;
    }
    private Event convertFromDto(EventDto dto) {
        Event event = new Event();
        event.setEventNo(dto.getEventNo());
        event.setEventImgBase64(dto.getEventImg());
        event.setEventName(dto.getEventName());
        event.setEventCategory(eventCategoryService.findByEventCategoryName(dto.getEventCategoryName()));
        event.setHost(hostService.findByHostName(dto.getHostName()));
        event.setEventCity(dto.getEventCity());
        event.setEventDesc(dto.getEventDesc());
        event.setEventDetailedAddress(dto.getEventDetailedAddress());
        event.setEventAddTime(dto.getEventAddTime());
        event.setEventRemoveTime(dto.getEventRemoveTime());
        event.setEventStartTime(dto.getEventStartTime());
        event.setEventEndTime(dto.getEventEndTime());
        event.setRegisteredCount(dto.getRegisteredCount());
        event.setEventStatus(dto.getEventStatus());
        event.setTotalRating(dto.getTotalRating());
        event.setEventRatingCount(dto.getEventRatingCount());
        event.setVisitCount(dto.getVisitCount());
        return event;
    }
}
