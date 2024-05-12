package com.ezban.ticketorder.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {
}
