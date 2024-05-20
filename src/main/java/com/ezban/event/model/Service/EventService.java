package com.ezban.event.model.Service;

import com.ezban.event.model.*;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.host.model.HostService;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.tickettype.model.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EventService implements ServiceDemo<Event> {
    @Autowired
    HostService hostService;

    @Autowired
    EventCategoryService eventCategoryService;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    QrcodeTicketService qrcodeTicketService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public Event add(Event event) {
        return eventRepository.saveAndFlush(event);
    }

    @Override
    public Event update(Event event) {

        return eventRepository.saveAndFlush(event);
    }

    @Override
    public Event findById(Integer pk) {
        return eventRepository.findById(pk).get();
    }

    @Override
    public List<Event> findAll() {

        return eventRepository.findAll();
    }

    public List<Event> findByHostNo(Integer hostNo) {
        return eventRepository.findByHostHostNo(hostNo);
    }

    public List<Event> findByEventStatus(EventStatus status) {
        return eventRepository.findByEventStatus(status);
    }

    public List<Event> findByEventCity(String eventCity,Pageable pageable) {
        return eventRepository.findByEventCityAndEventStatus(eventCity, EventStatus.PUBLISHED,pageable);
    }

    // 查詢該類別上架中的活動
    public List<Event> findByEventCategoryNo(Integer categoryNo, Pageable pageable) {
        return eventRepository.findByEventCategoryEventCategoryNoAndEventStatus(categoryNo, EventStatus.PUBLISHED, pageable);
    }

    /**
     * 複合查詢
     * @param cities
     * @param categoryNos
     * @param eventName
     * @param pageable
     * @return
     */
    public List<Event> findByEventCityAndEventCategoryAndEventName(List<String> cities, List<Integer> categoryNos, String eventName, Pageable pageable) {
        Specification<Event> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 查詢狀態為 PUBLISHED 的活動
            predicates.add(criteriaBuilder.equal(root.get("eventStatus"), EventStatus.PUBLISHED));

            // 查詢多個城市
            if (cities != null && !cities.isEmpty()) {
                predicates.add(root.get("eventCity").in(cities));
            }

            // 查詢多個類別
            if (categoryNos != null && !categoryNos.isEmpty()) {
                predicates.add(root.get("eventCategory").get("eventCategoryNo").in(categoryNos));
            }

            // 查詢活動名稱
            if (eventName != null && !eventName.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("eventName"), "%" + eventName + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return eventRepository.findAll(spec, pageable).getContent();
    }

    public boolean isAuthenticated(Principal principal, Event event) {
        return principal.getName().equals(event.getHost().getHostNo().toString());
    }

    /**
     * 增加活動瀏覽次數，並且寫入Redis
     *
     * @param eventNo
     */
    public void incrementEventVisitCount(Integer eventNo) {
        String key = "event:" + eventNo + ":visit.count:";
        redisTemplate.opsForValue().increment(key, 1);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
    }

    @Scheduled(fixedRate = 10, timeUnit = TimeUnit.SECONDS)
    public void syncEventVisitCount() {
        List<Event> events = this.findByEventStatus(EventStatus.PUBLISHED);
        List<Event> eventsToSync = new ArrayList<>();
        for (Event event : events) {
            String key = "event:" + event.getEventNo() + ":visit.count:";
            Integer visitCount = (Integer) redisTemplate.opsForValue().get(key);
            if (visitCount != null) {
                event.setVisitCount(event.getVisitCount() + visitCount);
                redisTemplate.delete(key);
                eventsToSync.add(event);
            }
        }
        eventRepository.saveAll(eventsToSync);
    }

    public List<Event> findByEventStatus(EventStatus status, Pageable pageable) {
        return eventRepository.findByEventStatus(status, pageable);
    }

    public List<Event> findByHostNoAndStatus(Integer hostNo, Integer eventStatus) {
        EventStatus status = EventStatus.values()[eventStatus];

        return eventRepository.findByHostHostNoAndEventStatus(hostNo, status);
    }

    public Map<String, Integer> getTicketInfo(Event event) {
        Set<TicketType> ticketTypes = event.getTicketTypes();

        int totalTickets = ticketTypes.stream().mapToInt(TicketType::getTicketTypeQty).sum();
        int unsoldTickets = ticketTypes.stream().mapToInt(TicketType::getRemainingTicketQty).sum();
        int soldTickets = totalTickets - unsoldTickets;

        Map<String, Integer> ticketInfo = new HashMap<>();
        ticketInfo.put("totalTickets", totalTickets);
        ticketInfo.put("soldTickets", soldTickets);
        ticketInfo.put("unsoldTickets", unsoldTickets);

        return ticketInfo;
    }

    public Map<String, Integer> getRegistrationInfo(Event event) {
        Map<String, Integer> registrationInfo = new HashMap<>();

        int totalCount = qrcodeTicketService.countByEventNo(event.getEventNo());
        int checkedCount = qrcodeTicketService.countByEventNoAndTicketUsageStatus(event.getEventNo(), QrcodeTicket.TicketUsageStatus.USED);
        int unCheckedCount = totalCount - checkedCount;

        registrationInfo.put("totalCount", totalCount);
        registrationInfo.put("checkedCount", checkedCount);
        registrationInfo.put("unCheckedCount", unCheckedCount);

        return registrationInfo;
    }

    public List<String> findDistinctEventCity() {

        return eventRepository.findDistinctEventCity();
    }

    public List<Event> findByEventCityAndEventCategory(String city, Integer categoryNo, Pageable pageable) {
        return eventRepository.findByEventCityAndEventCategoryEventCategoryNoAndEventStatus(city, categoryNo, EventStatus.PUBLISHED, pageable);
    }

    public List<Event> findTrendingEvents() {

        return eventRepository.findTop6ByOrderByVisitCountDesc();
    }

    public EventDto convertToDto(Event event) {
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

    public Event convertFromDto(EventDto dto) {
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
