package com.ezban.saveevent.model;

public class AddSaveEventDTO {
    private Integer saveEventNo;
    private Integer eventNo;
    private Byte saveStatus;
    private Integer memberNo;

    public AddSaveEventDTO() {}

    public Integer getSaveEventNo() {
        return saveEventNo;
    }

    public void setSaveEventNo(Integer saveEventNo) {
        this.saveEventNo = saveEventNo;
    }

    public Integer getEventNo() {
        return eventNo;
    }

    public void setEventNo(Integer eventNo) {
        this.eventNo = eventNo;
    }

    public Byte getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Byte saveStatus) {
        this.saveStatus = saveStatus;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }
}
