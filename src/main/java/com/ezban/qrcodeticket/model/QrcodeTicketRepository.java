package com.ezban.qrcodeticket.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QrcodeTicketRepository extends JpaRepository<QrcodeTicket,Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from qrcode_ticket where ticket_no =?1", nativeQuery = true)
    void deleteByQrcodeTicketNo(long ticketNo);

    List<QrcodeTicket> findByTicketOrderDetailTicketOrderDetailNo(Integer ticketOrderDetailNo);

    @Query("SELECT COUNT(q) FROM QrcodeTicket q JOIN q.ticketOrderDetail t JOIN t.ticketType tt WHERE tt.event.eventNo = ?1")
    Integer countByEventNo(Integer eventNo);

    @Query("SELECT COUNT(q) FROM QrcodeTicket q JOIN q.ticketOrderDetail t JOIN t.ticketType tt WHERE tt.event.eventNo = ?1 AND q.ticketUsageStatus = ?2")
    Integer countByEventNoAndTicketUsageStatus(Integer eventNo, Byte ticketUsageStatus);
}
