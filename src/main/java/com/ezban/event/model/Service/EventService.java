package com.ezban.event.model.Service;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;
import com.ezban.event.model.EventStatus;
import com.ezban.event.model.ServiceDemo;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.tickettype.model.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class EventService implements ServiceDemo<Event> {

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

    public List<Event> findByEventCityAndEventCategory(List<String> cities, List<Integer> categoryNos, Pageable pageable) {
        return eventRepository.findByEventCityAndEventCategory(EventStatus.PUBLISHED, cities, categoryNos, pageable);
    }

    public boolean isAuthenticated(Principal principal, Event event) {
        return principal.getName().equals(event.getHost().getHostNo().toString());
    }

    public Optional<Event> findEventByEventNo(Integer eventNo) {
        return eventRepository.findById(eventNo);
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
}
