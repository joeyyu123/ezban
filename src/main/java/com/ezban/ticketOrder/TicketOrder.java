package com.ezban.ticketOrder;

import com.ezban.eventCoupon.EventCoupon;
import com.ezban.member.Member;
import com.ezban.ticketOrderDetail.TicketOrderDetail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ticket_order")
public class TicketOrder implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_order_no", nullable = false)
    private Integer ticketOrderNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_coupon_no")
    private EventCoupon eventCoupon;

    @NotNull
    @Column(name = "ticket_order_amount", nullable = false)
    private Integer ticketOrderAmount;

    @Column(name = "event_coupon_discount")
    private Integer eventCouponDiscount;

    @NotNull
    @Column(name = "ticket_checkout_amount", nullable = false)
    private Integer ticketCheckoutAmount;

    @NotNull
    @Column(name = "ticket_order_time", nullable = false)
    private Timestamp ticketOrderTime;

    @Column(name = "ticket_order_pay_time")
    private Timestamp ticketOrderPayTime;

    @NotNull
    @Column(name = "ticket_order_payment_status", nullable = false)
    private Byte ticketOrderPaymentStatus;

    @Column(name = "ticket_order_status")
    private Byte ticketOrderStatus;

    @Column(name = "ticket_order_allocation_amount")
    private Integer ticketOrderAllocationAmount;

    @Column(name = "ticket_order_allocation_status")
    private Byte ticketOrderAllocationStatus;

    @OneToMany(mappedBy = "ticketOrder")
    private Set<TicketOrderDetail> ticketOrderDetails = new LinkedHashSet<>();

    public Integer getTicketOrderNo() {
        return ticketOrderNo;
    }

    public void setTicketOrderNo(Integer ticketOrderNo) {
        this.ticketOrderNo = ticketOrderNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public EventCoupon getEventCoupon() {
        return eventCoupon;
    }

    public void setEventCoupon(EventCoupon eventCoupon) {
        this.eventCoupon = eventCoupon;
    }

    public Integer getTicketOrderAmount() {
        return ticketOrderAmount;
    }

    public void setTicketOrderAmount(Integer ticketOrderAmount) {
        this.ticketOrderAmount = ticketOrderAmount;
    }

    public Integer getEventCouponDiscount() {
        return eventCouponDiscount;
    }

    public void setEventCouponDiscount(Integer eventCouponDiscount) {
        this.eventCouponDiscount = eventCouponDiscount;
    }

    public Integer getTicketCheckoutAmount() {
        return ticketCheckoutAmount;
    }

    public void setTicketCheckoutAmount(Integer ticketCheckoutAmount) {
        this.ticketCheckoutAmount = ticketCheckoutAmount;
    }

    public Timestamp getTicketOrderTime() {
        return ticketOrderTime;
    }

    public void setTicketOrderTime(Timestamp ticketOrderTime) {
        this.ticketOrderTime = ticketOrderTime;
    }

    public Timestamp getTicketOrderPayTime() {
        return ticketOrderPayTime;
    }

    public void setTicketOrderPayTime(Timestamp ticketOrderPayTime) {
        this.ticketOrderPayTime = ticketOrderPayTime;
    }

    public Byte getTicketOrderPaymentStatus() {
        return ticketOrderPaymentStatus;
    }

    public void setTicketOrderPaymentStatus(Byte ticketOrderPaymentStatus) {
        this.ticketOrderPaymentStatus = ticketOrderPaymentStatus;
    }

    public Byte getTicketOrderStatus() {
        return ticketOrderStatus;
    }

    public void setTicketOrderStatus(Byte ticketOrderStatus) {
        this.ticketOrderStatus = ticketOrderStatus;
    }

    public Integer getTicketOrderAllocationAmount() {
        return ticketOrderAllocationAmount;
    }

    public void setTicketOrderAllocationAmount(Integer ticketOrderAllocationAmount) {
        this.ticketOrderAllocationAmount = ticketOrderAllocationAmount;
    }

    public Byte getTicketOrderAllocationStatus() {
        return ticketOrderAllocationStatus;
    }

    public void setTicketOrderAllocationStatus(Byte ticketOrderAllocationStatus) {
        this.ticketOrderAllocationStatus = ticketOrderAllocationStatus;
    }

    public Set<TicketOrderDetail> getTicketOrderDetails() {
        return ticketOrderDetails;
    }

    public void setTicketOrderDetails(Set<TicketOrderDetail> ticketOrderDetails) {
        this.ticketOrderDetails = ticketOrderDetails;
    }

}