package com.ezban.event.model.Service;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;
import com.ezban.event.model.EventStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventStatusService {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    /**
     * 手動上架活動
     * @param event 要上架的活動
     */
    public void publishEvent(Event event) {
        event.setEventStatus(EventStatus.PUBLISHED);
        eventRepository.save(event);
    }

    /**
     * 手動下架活動
     * @param event 要下架的活動
     */
    public void removeEvent(Event event) {
        event.setEventStatus(EventStatus.ARCHIVED);
        eventRepository.save(event);
    }

    /**
     * 每分鐘執行一次，檢查有沒有要設為上架狀態的活動
     */
    @Scheduled(fixedRate = 1,timeUnit = java.util.concurrent.TimeUnit.MINUTES) //每分鐘執行一次
    @Transactional
    public void launchEvents() {
        // 取得所有草稿狀態且上架時間小於等於現在時間的活動
        List<Event> events = eventRepository.findByEventStatusAndEventAddTimeBefore(EventStatus.DRAFT, new Timestamp(System.currentTimeMillis()));
        List<Event> updatedEvents = new ArrayList<>();
        for (Event event : events) {
            // 將活動狀態改為上架
            if(eventService.isPublishable(event)) {
                event.setEventStatus(EventStatus.PUBLISHED);
                updatedEvents.add(event);
            }
        }
        eventRepository.saveAll(updatedEvents);
    }

    /**
     * 每分鐘執行一次，檢查有沒有要設為結束狀態的活動
     */
    @Scheduled(fixedRate = 1,timeUnit = java.util.concurrent.TimeUnit.MINUTES)//每分鐘執行一次
    public void closeEvents() {
        // 取得所有上架狀態且結束時間小於等於現在時間的活動
        List<Event> events = eventRepository.findByEventStatusAndEventEndTimeBefore(EventStatus.PUBLISHED, new Timestamp(System.currentTimeMillis()));
        List<Event> updatedEvents = new ArrayList<>();
        for (Event event : events) {
            // 將活動狀態改為結束
            event.setEventStatus(EventStatus.FINISHED);
            updatedEvents.add(event);
        }
        eventRepository.saveAll(updatedEvents);
    }
}
