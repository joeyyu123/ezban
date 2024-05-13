package com.ezban.ticketorderdetail.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, Integer> {
    @EntityGraph(attributePaths = "ticketType.ticketTypeName")
    List<TicketOrderDetail> findByTicketOrderTicketOrderNo(Integer ticketOrderNo);
}
