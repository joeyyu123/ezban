package com.ezban.saveevent.model;

import com.ezban.event.model.Event;
import com.ezban.member.model.Member;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "save_event")
public class SaveEvent implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "save_event_no", nullable = false)
    private Integer saveEventNo;

    @NotNull(message = "* 會員編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @NotNull(message = "* 活動編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "event_no", nullable = false)
    private Event event;

    @NotNull(message = "* 活動收藏狀態: 請勿空白 !")
    @Column(name = "save_status", nullable = false)
    @Min(value = 0, message = "* 活動收藏狀態不正確 !")
    @Max(value = 1, message = "* 活動收藏狀態不正確 !")
    private Byte saveStatus;

    //--------------------------------------------getters and setters--------------------------------------------
    public SaveEvent(){   //必需有一個不傳參數建構子(JavaBean基本知識)

    }
    public Integer getSaveEventNo() {
        return saveEventNo;
    }

    public void setSaveEventNo(Integer saveEventNo) {
        this.saveEventNo = saveEventNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member memberNo) {
        this.member = memberNo;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event eventNo) {
        this.event = eventNo;
    }

    public Byte getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Byte saveStatus) {
        this.saveStatus = saveStatus;
    }
}