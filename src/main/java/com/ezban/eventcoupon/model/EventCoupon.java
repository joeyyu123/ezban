package com.ezban.eventcoupon.model;


import com.ezban.host.model.Host;
import com.ezban.ticketorder.model.TicketOrder;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "event_coupon", schema = "ezban")
public class EventCoupon implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_coupon_no", nullable = false)
    private Integer eventCouponNo;
    
    @ManyToOne
    @JoinColumn(name = "host_no")
    private Host host;

    @Size(max = 100)
    @Column(name = "event_coupon_name", length = 100)
    private String eventCouponName;

    @Size(max = 10)
    @NotNull
    @Column(name = "coupon_code", nullable = false, length = 10)
    private String couponCode;

    @NotNull
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;

    @Column(name = "remaining_times")
    private Integer remainingTimes;

    @Column(name = "min_spend")
    private Integer minSpend;

    @NotNull
    @Column(name = "event_coupon_discount", nullable = false)
    private Integer eventCouponDiscount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "start_time")
    private Timestamp startTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
    @Column(name = "end_time")
    private Timestamp endTime;

    @NotNull
    @Column(name = "event_coupon_status", nullable = false)
    private Byte eventCouponStatus;

    @Size(max = 150)
    @Column(name = "coupon_desc", length = 150)
    private String couponDesc;

    @OneToMany(mappedBy = "eventCoupon")
    private Set<TicketOrder> ticketOrders = new LinkedHashSet<>();

//--------------------------------------------getters and setters--------------------------------------------

    public EventCoupon(){ //必需有一個不傳參數建構子(JavaBean基本知識)

    }

    public Integer getEventCouponNo() {
        return this.eventCouponNo;
    }

    public void setEventCouponNo(Integer eventCouponNo) {
        this.eventCouponNo = eventCouponNo;
    }

    public Host getHost() {
        return this.host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getEventCouponName() {
        return this.eventCouponName;
    }

    public void setEventCouponName(String eventCouponName) {
        this.eventCouponName = eventCouponName;
    }

    public String getCouponCode() {
        return this.couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getUsageLimit() {
        return this.usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getRemainingTimes() {
        return this.remainingTimes;
    }

    public void setRemainingTimes(Integer remainingTimes) {
        this.remainingTimes = remainingTimes;
    }

    public Integer getMinSpend() {
        return this.minSpend;
    }

    public void setMinSpend(Integer minSpend) {
        this.minSpend = minSpend;
    }

    public Integer getEventCouponDiscount() {
        return this.eventCouponDiscount;
    }

    public void setEventCouponDiscount(Integer eventCouponDiscount) {
        this.eventCouponDiscount = eventCouponDiscount;
    }

    public Timestamp getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Byte getEventCouponStatus() {
        return this.eventCouponStatus;
    }

    public void setEventCouponStatus(Byte eventCouponStatus) {
        this.eventCouponStatus = eventCouponStatus;
    }

    public String getCouponDesc() {
        return this.couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public Set<TicketOrder> getTicketOrders() {
        return this.ticketOrders;
    }

    public void setTicketOrders(Set<TicketOrder> ticketOrders) {
        this.ticketOrders = ticketOrders;
    }

}