package com.ezban.qrcodeticket.model;

import com.ezban.member.model.Member;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "qrcode_ticket", schema = "ezban")
public class QrcodeTicket implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_no", nullable = false)
    private Long ticketNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_order_detail_no", nullable = false)
    private TicketOrderDetail ticketOrderDetail;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Column(name = "ticket_usage_status")
    private Byte ticketUsageStatus;

    @NotNull
    @Column(name = "ticket_valid_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "Asia/Taipei")
    private Timestamp ticketValidTime;

    public class TicketUsageStatus {
        public static final byte UNUSED = 0;   // 未使用
        public static final byte USED = 1;     // 已使用
        public static final byte EXPIRED = 2;  // 已失效
    }

    //--------------------------------------------getters and setters--------------------------------------------
    public QrcodeTicket() {   //必需有一個不傳參數建構子(JavaBean基本知識)
    }

    public Long getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(Long ticketNo) {
        this.ticketNo = ticketNo;
    }

    public TicketOrderDetail getTicketOrderDetail() {
        return ticketOrderDetail;
    }

    public void setTicketOrderDetail(TicketOrderDetail ticketOrderDetail) {
        this.ticketOrderDetail = ticketOrderDetail;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Byte getTicketUsageStatus() {
        return ticketUsageStatus;
    }

    public void setTicketUsageStatus(Byte ticketUsageStatus) {
        this.ticketUsageStatus = ticketUsageStatus;
    }

    public Timestamp getTicketValidTime() {
        return ticketValidTime;
    }

    public void setTicketValidTime(Timestamp ticketValidTime) {
        this.ticketValidTime = ticketValidTime;
    }
}