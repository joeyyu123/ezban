package com.ezban.event.model.Service;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;
import com.ezban.event.model.EventStatus;
import com.ezban.event.model.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class EventService implements ServiceDemo<Event> {

    @Autowired
    EventRepository eventRepository;

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

    // 查詢該類別上架中的活動
    public List<Event> findByEventCategoryNo(Integer categoryNo) {
        return eventRepository.findByEventCategoryEventCategoryNoAndEventStatus(categoryNo, EventStatus.PUBLISHED);
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
            String key = "event:" + event.getEventNo() + "visit.count:";
            Integer visitCount = (Integer) redisTemplate.opsForValue().get(key);
            if (visitCount != null) {
                event.setVisitCount(visitCount);
                eventsToSync.add(event);
            }
        }
        eventRepository.saveAll(eventsToSync);
        System.out.println(eventsToSync.size() + " events are updated.");
    }

    public List<Event> findByEventStatus(EventStatus status, Pageable pageable) {
        return eventRepository.findByEventStatus(status, pageable);
    }
}
