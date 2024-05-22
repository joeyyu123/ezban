package com.ezban.ticketorderdetail.model;

import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.tickettype.model.TicketType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "ticket_order_detail")
public class TicketOrderDetail implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_order_detail_no", nullable = false)
    private Integer ticketOrderDetailNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_type_no", nullable = false)
    private TicketType ticketType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_order_no", nullable = false)
    private TicketOrder ticketOrder;

    @NotNull
    @Column(name = "ticket_type_price", nullable = false)
    private Integer ticketTypePrice;

    @NotNull
    @Column(name = "ticket_type_qty", nullable = false)
    private Integer ticketTypeQty;

    @NotNull
    @Column(name = "included_ticket_qty", nullable = false)
    @JsonIgnore
    private Integer includedTicketQty;

    @OneToMany(mappedBy = "ticketOrderDetail")
    @JsonIgnore
    private Set<QrcodeTicket> qrcodeTickets = new LinkedHashSet<>();

    public Integer getTicketOrderDetailNo() {
        return ticketOrderDetailNo;
    }

    public void setTicketOrderDetailNo(Integer ticketOrderDetailNo) {
        this.ticketOrderDetailNo = ticketOrderDetailNo;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketOrder getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(TicketOrder ticketOrder) {
        this.ticketOrder = ticketOrder;
    }

    public Integer getTicketTypePrice() {
        return ticketTypePrice;
    }

    public void setTicketTypePrice(Integer ticketTypePrice) {
        this.ticketTypePrice = ticketTypePrice;
    }

    public Integer getTicketTypeQty() {
        return ticketTypeQty;
    }

    public void setTicketTypeQty(Integer ticketTypeQty) {
        this.ticketTypeQty = ticketTypeQty;
    }

    public Integer getIncludedTicketQty() {
        return includedTicketQty;
    }

    public void setIncludedTicketQty(Integer includedTicketQty) {
        this.includedTicketQty = includedTicketQty;
    }

    public Set<QrcodeTicket> getQrcodeTickets() {
        return qrcodeTickets;
    }

    @Override
    public String toString() {
        return "TicketOrderDetail{" +
                "ticketOrderDetailNo=" + ticketOrderDetailNo +
                ", ticketType=" + ticketType +
                ", ticketOrder=" + ticketOrder +
                ", ticketTypePrice=" + ticketTypePrice +
                ", ticketTypeQty=" + ticketTypeQty +
                ", includedTicketQty=" + includedTicketQty +
                ", qrcodeTickets=" + qrcodeTickets +
                '}';
    }

    public void setQrcodeTickets(Set<QrcodeTicket> qrcodeTickets) {
        this.qrcodeTickets = qrcodeTickets;
    }

}