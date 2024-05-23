package com.ezban.ticketorder.model;

import com.ezban.member.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.sql.Timestamp;
import java.util.List;

public interface TicketOrderRepository extends JpaRepository<TicketOrder, Integer> {

    List<TicketOrder> findByMember(Member memberNo);
    List<TicketOrder> findByMember(Member member, Sort sort);
    List<TicketOrder> findByTicketOrderStatusAndTicketOrderTimeBefore(TicketOrderStatus status, Timestamp time);

    List<TicketOrder> findByMemberAndTicketOrderStatus(Member member, TicketOrderStatus status,Sort sort);



    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo")
    List<TicketOrder> findByEventNo(@Param("eventNo") Integer eventNo, Sort sort);
    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo")
    Page<TicketOrder> findByEventNo(@Param("eventNo") Integer eventNo, Pageable pageable);

    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo AND to.ticketOrderStatus = :status")
    List<TicketOrder> findByEventNoAndStatus(@Param("eventNo") Integer eventNo, @Param("status") TicketOrderStatus status, Sort sort);
    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo AND to.ticketOrderStatus = :status")
    Page<TicketOrder> findByEventNoAndStatus(@Param("eventNo") Integer eventNo, @Param("status") TicketOrderStatus status, Pageable pageable);

    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo AND to.ticketOrderNo = :ticketOrderNo")
    List<TicketOrder> findByEventNoAndTicketOrderNo(@Param("eventNo") Integer eventNo, @Param("ticketOrderNo") Integer ticketOrderNo, Sort sort);
    @Query("SELECT DISTINCT to FROM TicketOrder to JOIN to.ticketOrderDetails tod JOIN tod.ticketType tt JOIN tt.event e WHERE e.eventNo = :eventNo AND to.ticketOrderNo = :ticketOrderNo")
    Page<TicketOrder> findByEventNoAndTicketOrderNo(@Param("eventNo") Integer eventNo, @Param("ticketOrderNo") Integer ticketOrderNo, Pageable pageable);


}
