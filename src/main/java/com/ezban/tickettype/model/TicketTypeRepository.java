package com.ezban.tickettype.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTypeRepository extends JpaRepository<TicketType, Integer> {
    List<TicketType> findByEventEventNo(Integer eventNo);
}
