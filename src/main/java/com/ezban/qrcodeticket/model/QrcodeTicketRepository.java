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

    @Query(value = "SELECT e.host_no " +
            "FROM qrcode_ticket qt " +
            "JOIN ticket_order_detail tod ON qt.ticket_order_detail_no = tod.ticket_order_detail_no " +
            "JOIN ticket_type tt ON tod.ticket_type_no = tt.ticket_type_no " +
            "JOIN event e ON tt.event_no = e.event_no " +
            "WHERE qt.ticket_no = ?1", nativeQuery = true)
    Integer findHostNoByTicketNo(long ticketNo);

    // 根據票券號碼查詢票券
    QrcodeTicket findByTicketNo(Long ticketNo);

    @Query("SELECT qt FROM QrcodeTicket qt WHERE qt.ticketOrderDetail.ticketOrderDetailNo = ?1")
    QrcodeTicket findByTicketOrderDetailNo(Integer ticketOrderDetailNo);

    @Transactional
    @Query("SELECT qt FROM QrcodeTicket qt WHERE qt.member.memberNo = ?1")
    List<QrcodeTicket> findTicketsByMemberNo(Integer memberNo);

    @Query("SELECT COUNT(q) FROM QrcodeTicket q JOIN q.ticketOrderDetail t JOIN t.ticketType tt WHERE tt.event.eventNo = ?1")
    Integer countByEventNo(Integer eventNo);

    @Query("SELECT COUNT(q) FROM QrcodeTicket q JOIN q.ticketOrderDetail t JOIN t.ticketType tt WHERE tt.event.eventNo = ?1 AND q.ticketUsageStatus = ?2")
    Integer countByEventNoAndTicketUsageStatus(Integer eventNo, Byte ticketUsageStatus);
}
