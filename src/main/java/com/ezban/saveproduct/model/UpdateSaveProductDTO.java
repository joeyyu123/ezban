package com.ezban.saveproduct.model;

public class UpdateSaveProductDTO {

    private Integer saveProductNo;

    private Byte saveStatus;

    public UpdateSaveProductDTO() {

    }

    public Integer getSaveProductNo() {
        return saveProductNo;
    }

    public void setSaveProductNo(Integer saveProductNo) {
        this.saveProductNo = saveProductNo;
    }

    public Byte getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Byte saveStatus) {
        this.saveStatus = saveStatus;
    }
}
