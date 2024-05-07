package com.ezban.eventcategory.model;

import com.ezban.event.model.Event;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "event_category")
public class EventCategory implements java.io.Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_category_no", nullable = false)
    private Integer eventCategoryNo;

    @Size(max = 20)
    @NotNull
    @Column(name = "event_category_name", nullable = false, length = 20)
    private String eventCategoryName;

    @OneToMany(mappedBy = "eventCategory",cascade = CascadeType.ALL)
    private Set<Event> events = new LinkedHashSet<>();

    public Integer getEventCategoryNo() {
        return eventCategoryNo;
    }

    public void setEventCategoryNo(Integer eventCategoryNo) {
        this.eventCategoryNo = eventCategoryNo;
    }

    public String getEventCategoryName() {
        return eventCategoryName;
    }

    public void setEventCategoryName(String eventCategoryName) {
        this.eventCategoryName = eventCategoryName;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

}