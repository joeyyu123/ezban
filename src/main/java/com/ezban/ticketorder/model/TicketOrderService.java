package com.ezban.ticketorder.model;

import com.ezban.event.model.ServiceDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService implements ServiceDemo<TicketOrder> {

    @Autowired
    private TicketOrderRepository repository;

    @Override
    public TicketOrder add(TicketOrder vo) {
        return repository.save(vo);
    }

    @Override
    public TicketOrder update(TicketOrder vo) {
        return repository.save(vo);
    }

    @Override
    public TicketOrder findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TicketOrder> findAll() {
        return repository.findAll();
    }
}
