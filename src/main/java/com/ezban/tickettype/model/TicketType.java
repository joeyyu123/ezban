package com.ezban.tickettype.model;

import com.ezban.event.model.Event;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.annotations.Expose;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "ticket_type")
public class TicketType implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_type_no", nullable = false)
    private Integer ticketTypeNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_no", nullable = false)
    @JsonIgnore
    private Event event;

    @Size(max = 50)
    @NotNull
    @Column(name = "ticket_type_name", nullable = false, length = 50)
    private String ticketTypeName;

    @NotNull
    @Column(name = "included_tickets", nullable = false)
    private Integer includedTickets;

    @NotNull
    @Column(name = "purchase_start_time", nullable = false)
    private Timestamp purchaseStartTime;

    @NotNull
    @Column(name = "purchase_end_time", nullable = false)
    private Timestamp purchaseEndTime;

    @Size(max = 200)
    @Column(name = "ticket_type_info", length = 200)
    private String ticketTypeInfo;

    @NotNull
    @Column(name = "ticket_type_price", nullable = false)
    private Integer ticketTypePrice;

    @NotNull
    @Column(name = "ticket_type_qty", nullable = false)
    private Integer ticketTypeQty;

    @Column(name = "remaining_ticket_qty")
    private Integer remainingTicketQty;

    @Column(name="purchase_quantity_limit")
    private Integer purchaseQuantityLimit;

    public Integer getTicketTypeNo() {
        return ticketTypeNo;
    }

    public void setTicketTypeNo(Integer ticketTypeNo) {
        this.ticketTypeNo = ticketTypeNo;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public Integer getIncludedTickets() {
        return includedTickets;
    }

    public void setIncludedTickets(Integer includedTickets) {
        this.includedTickets = includedTickets;
    }

    public Timestamp getPurchaseStartTime() {
        return purchaseStartTime;
    }

    public void setPurchaseStartTime(Timestamp purchaseStartTime) {
        this.purchaseStartTime = purchaseStartTime;
    }

    public Timestamp getPurchaseEndTime() {
        return purchaseEndTime;
    }

    public void setPurchaseEndTime(Timestamp purchaseEndTime) {
        this.purchaseEndTime = purchaseEndTime;
    }

    public String getTicketTypeInfo() {
        return ticketTypeInfo;
    }

    public void setTicketTypeInfo(String ticketTypeInfo) {
        this.ticketTypeInfo = ticketTypeInfo;
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

    public Integer getRemainingTicketQty() {
        return remainingTicketQty;
    }

    public void setRemainingTicketQty(Integer remainingTicketQty) {
        this.remainingTicketQty = remainingTicketQty;
    }

    public Integer getPurchaseQuantityLimit() {
        return purchaseQuantityLimit;
    }

    public void setPurchaseQuantityLimit(Integer purchaseQuantityLimit) {
        this.purchaseQuantityLimit = purchaseQuantityLimit;
    }
}