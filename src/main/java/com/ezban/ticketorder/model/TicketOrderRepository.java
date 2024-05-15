package com.ezban.ticketorder.model;

import com.ezban.member.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

    List<TicketOrder> findByMember(Member memberNo);
    List<TicketOrder> findByMember(Member member, Sort sort);
    List<TicketOrder> findByTicketOrderStatusAndTicketOrderTimeBefore(TicketOrderStatus status, Timestamp time);

    List<TicketOrder> findByMemberAndTicketOrderStatus(Member member, TicketOrderStatus status);
    List<TicketOrder> findByMemberAndTicketOrderStatus(Member member, TicketOrderStatus status,Sort sort);
}
