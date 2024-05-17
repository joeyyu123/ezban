package com.ezban.tickettype.model;

import com.ezban.event.model.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketTypeService implements ServiceDemo<TicketType> {

    @Autowired
    private TicketTypeRepository repository;

    @Override
    public TicketType add(TicketType vo) {
        return repository.saveAndFlush(vo);
    }

    @Override
    public TicketType update(TicketType vo) {
        return repository.saveAndFlush(vo);
    }

    public void delete(TicketType vo) {
        repository.delete(vo);
    }

    @Override
    public TicketType findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TicketType> findAll() {
        return repository.findAll();
    }

    public List<TicketType> findByEventNo(Integer eventNo) {
        return repository.findByEventEventNo(eventNo);
    }

    public List<TicketType> saveAllAndFlush(List<TicketType> ticketTypes) {
        return repository.saveAllAndFlush(ticketTypes);
    }
}
