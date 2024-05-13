package com.ezban.ticketorder.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

    List<TicketOrder> findByticketOrderStatusAndTicketOrderTimeBefore(TicketOrderStatus status, Timestamp time);
}
