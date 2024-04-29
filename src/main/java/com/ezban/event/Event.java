package com.ezban.event;

import com.ezban.eventComment.EventComment;
import com.ezban.host.Host;
import com.ezban.eventCategory.EventCategory;
import com.ezban.ticketType.TicketType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_no", nullable = false)
    private Integer eventNo;

    @Column(name = "event_img", columnDefinition = "longblob")
    private byte[] eventImg;

    @Size(max = 50)
    @NotNull
    @Column(name = "event_name", nullable = false, length = 50)
    private String eventName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_category_no")
    private EventCategory eventCategory;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_no", nullable = false)
    private Host host;

    @Column(name = "event_desc", columnDefinition="longtext")
    private String eventDesc;

    @Size(max = 15)
    @Column(name = "event_city", length = 15)
    private String eventCity;

    @Size(max = 250)
    @Column(name = "event_detailed_address", length = 250)
    private String eventDetailedAddress;

    @NotNull
    @Column(name = "event_add_time", nullable = false)
    private Timestamp eventAddTime;

    @NotNull
    @Column(name = "event_remove_time", nullable = false)
    private Timestamp eventRemoveTime;

    @Column(name = "registration_start_time")
    private Timestamp registrationStartTime;

    @Column(name = "registration_end_time")
    private Timestamp registrationEndTime;

    @NotNull
    @Column(name = "event_start_time", nullable = false)
    private Timestamp eventStartTime;

    @NotNull
    @Column(name = "event_end_time", nullable = false)
    private Timestamp eventEndTime;

    @Column(name = "registered_count")
    private Integer registeredCount;

    @NotNull
    @Column(name = "event_status", nullable = false)
    private Byte eventStatus;

    @Column(name = "total_rating")
    private Integer totalRating;

    @Column(name = "event_rating_count")
    private Integer eventRatingCount;

    @Column(name = "event_checkout_status")
    private Byte eventCheckoutStatus;

    @Column(name = "event_checkout_time")
    private Integer eventCheckoutTime;

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private Set<EventComment> eventComments = new LinkedHashSet<>();

    @OneToMany(mappedBy = "event",cascade = CascadeType.ALL)
    private Set<TicketType> ticketTypes = new LinkedHashSet<>();

    public Integer getEventNo() {
        return eventNo;
    }

    public void setEventNo(Integer eventNo) {
        this.eventNo = eventNo;
    }

    public byte[] getEventImg() {
        return eventImg;
    }

    public void setEventImg(byte[] eventImg) {
        this.eventImg = eventImg;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public String getEventCity() {
        return eventCity;
    }

    public void setEventCity(String eventCity) {
        this.eventCity = eventCity;
    }

    public String getEventDetailedAddress() {
        return eventDetailedAddress;
    }

    public void setEventDetailedAddress(String eventDetailedAddress) {
        this.eventDetailedAddress = eventDetailedAddress;
    }

    public Timestamp getEventAddTime() {
        return eventAddTime;
    }

    public void setEventAddTime(Timestamp eventAddTime) {
        this.eventAddTime = eventAddTime;
    }

    public Timestamp getEventRemoveTime() {
        return eventRemoveTime;
    }

    public void setEventRemoveTime(Timestamp eventRemoveTime) {
        this.eventRemoveTime = eventRemoveTime;
    }

    public Timestamp getRegistrationStartTime() {
        return registrationStartTime;
    }

    public void setRegistrationStartTime(Timestamp registrationStartTime) {
        this.registrationStartTime = registrationStartTime;
    }

    public Timestamp getRegistrationEndTime() {
        return registrationEndTime;
    }

    public void setRegistrationEndTime(Timestamp registrationEndTime) {
        this.registrationEndTime = registrationEndTime;
    }

    public Timestamp getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Timestamp eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public Timestamp getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(Timestamp eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Integer getRegisteredCount() {
        return registeredCount;
    }

    public void setRegisteredCount(Integer registeredCount) {
        this.registeredCount = registeredCount;
    }

    public Byte getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(Byte eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getEventRatingCount() {
        return eventRatingCount;
    }

    public void setEventRatingCount(Integer eventRatingCount) {
        this.eventRatingCount = eventRatingCount;
    }

    public Byte getEventCheckoutStatus() {
        return eventCheckoutStatus;
    }

    public void setEventCheckoutStatus(Byte eventCheckoutStatus) {
        this.eventCheckoutStatus = eventCheckoutStatus;
    }

    public Integer getEventCheckoutTime() {
        return eventCheckoutTime;
    }

    public void setEventCheckoutTime(Integer eventCheckoutTime) {
        this.eventCheckoutTime = eventCheckoutTime;
    }

    public Set<EventComment> getEventComments() {
        return eventComments;
    }

    public void setEventComments(Set<EventComment> eventComments) {
        this.eventComments = eventComments;
    }

    public Set<TicketType> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(Set<TicketType> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventNo=" + eventNo +
                ", eventName='" + eventName + '\'' +
                ", eventCategory=" + eventCategory +
                ", host=" + host +
                ", eventDesc='" + eventDesc + '\'' +
                ", eventCity='" + eventCity + '\'' +
                ", eventDetailedAddress='" + eventDetailedAddress + '\'' +
                ", eventAddTime=" + eventAddTime +
                ", eventRemoveTime=" + eventRemoveTime +
                ", registrationStartTime=" + registrationStartTime +
                ", registrationEndTime=" + registrationEndTime +
                ", eventStartTime=" + eventStartTime +
                ", eventEndTime=" + eventEndTime +
                ", registeredCount=" + registeredCount +
                ", eventStatus=" + eventStatus +
                ", totalRating=" + totalRating +
                ", eventRatingCount=" + eventRatingCount +
                ", eventCheckoutStatus=" + eventCheckoutStatus +
                ", eventCheckoutTime=" + eventCheckoutTime +
                ", eventComments=" + eventComments +
                ", ticketTypes=" + ticketTypes +
                '}';
    }
}