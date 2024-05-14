package com.ezban.event.model.Service;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventRepository;
import com.ezban.event.model.EventStatus;
import com.ezban.event.model.ServiceDemo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class EventService implements ServiceDemo<Event> {

    @Autowired
    EventRepository repository;

    @Override
    public Event add(Event event) {
        return repository.saveAndFlush(event);
    }

    @Override
    public Event update(Event event) {

        return repository.saveAndFlush(event);
    }

    @Override
    public Event findById(Integer pk) {
        return repository.getReferenceById(pk);
    }

    @Override
    public List<Event> findAll() {

        return repository.findAll();
    }

    public List<Event> findByHostNo(Integer hostNo) {
        return repository.findByHostHostNo(hostNo);
    }

    public List<Event> findByEventStatus(EventStatus status) {
        return repository.findByEventStatus(status);
    }

    // 查詢該類別上架中的活動
    public List<Event> findByEventCategoryNo(Integer categoryNo){
        return repository.findByEventCategoryEventCategoryNoAndEventStatus(categoryNo, EventStatus.PUBLISHED);
    }

}