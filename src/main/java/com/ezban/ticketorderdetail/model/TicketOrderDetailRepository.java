package com.ezban.ticketorderdetail.model;

import com.ezban.ticketorder.model.TicketOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, Integer> {
}
