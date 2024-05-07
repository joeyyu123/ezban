package com.ezban.event.model;

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

    public List<Event> findByEventStatus(Byte status) {
        return repository.findByEventStatus(status);
    }

}
