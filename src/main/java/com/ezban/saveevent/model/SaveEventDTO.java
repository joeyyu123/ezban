package com.ezban.saveevent.model;

import java.time.LocalDateTime;

public class SaveEventDTO {
    private Integer eventNo;
    private String eventName;
    private String eventCity;
    private String eventDetailedAddress;
    private LocalDateTime eventStartTime;
    private LocalDateTime eventEndTime;
    private Byte saveStatus;
    private String formattedStartTime;
    private String formattedEndTime;

    // Constructors, Getters, and Setters
    public SaveEventDTO() {}

    public SaveEventDTO(Integer eventNo, String eventName, String eventCity, String eventDetailedAddress,
                        LocalDateTime eventStartTime, LocalDateTime eventEndTime, Byte saveStatus,
                        String formattedStartTime, String formattedEndTime) {
        this.eventNo = eventNo;
        this.eventName = eventName;
        this.eventCity = eventCity;
        this.eventDetailedAddress = eventDetailedAddress;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.saveStatus = saveStatus;
        this.formattedStartTime = formattedStartTime;
        this.formattedEndTime = formattedEndTime;
    }

    public Integer getEventNo() {
        return eventNo;
    }

    public void setEventNo(Integer eventNo) {
        this.eventNo = eventNo;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public LocalDateTime getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(LocalDateTime eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public LocalDateTime getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(LocalDateTime eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public Byte getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Byte saveStatus) {
        this.saveStatus = saveStatus;
    }

    public String getFormattedStartTime() {
        return formattedStartTime;
    }

    public void setFormattedStartTime(String formattedStartTime) {
        this.formattedStartTime = formattedStartTime;
    }

    public String getFormattedEndTime() {
        return formattedEndTime;
    }

    public void setFormattedEndTime(String formattedEndTime) {
        this.formattedEndTime = formattedEndTime;
    }

}
