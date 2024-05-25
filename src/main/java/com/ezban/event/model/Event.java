package com.ezban.event.model;

import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcomment.model.EventComment;
import com.ezban.host.model.Host;
import com.ezban.tickettype.model.TicketType;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event")
public class Event implements java.io.Serializable {
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_category_no")
    private EventCategory eventCategory;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "host_no", nullable = false)
    private Host host;

    @Column(name = "event_desc", columnDefinition = "longtext")
    private String eventDesc;

    @NotNull
    @Size(max = 15)
    @Column(name = "event_city", length = 15)
    private String eventCity;

    @NotNull
    @Size(max = 250)
    @Column(name = "event_detailed_address", length = 250)
    private String eventDetailedAddress;

    @NotNull
    @Column(name = "event_add_time", nullable = false)
    private Timestamp eventAddTime;

    @NotNull
    @Column(name = "event_remove_time", nullable = false)
    private Timestamp eventRemoveTime;

    @NotNull
    @Column(name = "event_start_time", nullable = false)
    private Timestamp eventStartTime;

    @NotNull
    @Column(name = "event_end_time", nullable = false)
    private Timestamp eventEndTime;

    @Column(name = "visit_count")
    private Integer visitCount;

    @Column(name = "registered_count")
    private Integer registeredCount;

    /*
     * 0表示未上架
     * 1表示上架中
     * 2表示已下架
     * 3表示已結束
     */
    @NotNull
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    @Column(name = "total_rating")
    private Integer totalRating;

    @Column(name = "event_rating_count")
    private Integer eventRatingCount;

    @Column(name = "event_checkout_status")
    private EventCheckoutStatus eventCheckoutStatus;

    @Column(name = "event_checkout_time")
    private Timestamp eventCheckoutTime;

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<EventComment> eventComments = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private Set<TicketType> ticketTypes = new LinkedHashSet<>();


    public Event(Integer eventNo) {
        this.eventNo = eventNo;
    }


    public String getEventImgBase64() {
        if (eventImg == null || eventImg.length == 0) {
            return null;
        }
        return Base64.getEncoder().encodeToString(eventImg);
    }

    public void setEventImgBase64(String eventImgBase64) {
        if (eventImgBase64 == null || eventImgBase64.isEmpty()) {
            return;
        }
        this.eventImg = Base64.getDecoder().decode(eventImgBase64);
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