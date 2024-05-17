package com.ezban.saveproduct.model;

public class AddSaveProductDTO {
    private Integer saveProductNo;

    private Integer productNo;

    private Byte saveStatus;

    private Integer memberNo;

    public AddSaveProductDTO() {

    }

    public Integer getSaveProductNo() {
        return saveProductNo;
    }

    public void setSaveProductNo(Integer saveProductNo) {
        this.saveProductNo = saveProductNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
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
