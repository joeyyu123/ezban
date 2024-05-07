package com.ezban.ticketorder.model;

import com.ezban.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {
}
