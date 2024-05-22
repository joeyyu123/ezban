package com.ezban.event.model;

import com.ezban.eventcategory.model.EventCategory;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Base64;

@Data
public class EventDto {
    private Integer eventNo;
    private String eventImg;
    private String eventName;
    private String eventCategoryName;
    private String hostName;
    private String eventDesc;
    private String eventCity;
    private String eventDetailedAddress;
    private Timestamp eventAddTime;
    private Timestamp eventRemoveTime;
    private Timestamp eventStartTime;
    private Timestamp eventEndTime;
    private Integer registeredCount;
    private Integer visitCount;
    private EventStatus eventStatus;
    private Integer totalRating;
    private Integer eventRatingCount;



}
