package com.ezban.qrcodeticket.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QrcodeTicketRepository extends JpaRepository<QrcodeTicket,Long> {
    @Transactional
    @Modifying
    @Query(value = "delete from qrcode_ticket where ticket_no =?1", nativeQuery = true)
    void deleteByQrcodeTicketNo(long ticketNo);
}
