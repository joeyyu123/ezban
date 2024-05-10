package com.ezban.ticketorderdetail.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketOrderDetailRepository extends JpaRepository<TicketOrderDetail, Integer> {
    List<TicketOrderDetail> findByTicketOrderTicketOrderNo(Integer ticketOrderNo);
}
