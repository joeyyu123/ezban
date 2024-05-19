package com.ezban.eventcategory.model;

import com.ezban.event.model.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EventCategoryService implements ServiceDemo<EventCategory> {

    @Autowired
    EventCategoryRepository repository;

    @Override
    public EventCategory add(EventCategory vo) {
        return repository.saveAndFlush(vo);
    }

    @Override
    public EventCategory update(EventCategory vo) {
        return repository.saveAndFlush(vo);
    }

    @Override
    public EventCategory findById(Integer id) {
        return repository.getReferenceById(id);
    }

    @Override
    public List<EventCategory> findAll() {
        return repository.findAll();
    }

    public EventCategory findByEventCategoryName(String eventCategoryName) {
        return repository.findByEventCategoryName(eventCategoryName);
    }
}
