package com.ezban.productreport.model;

public class UpdateProductReportDTO {

    private Integer productReportNo;

    private Byte selectedValue;

    private  Integer adminNo;

    private String adminName;

    public UpdateProductReportDTO() {
    }

    public Integer getProductReportNo() {
        return productReportNo;
    }

    public void setProductReportNo(Integer productReportNo) {
        this.productReportNo = productReportNo;
    }

    public Byte getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(Byte selectedValue) {
        this.selectedValue = selectedValue;
    }

    public Integer getAdminNo() {
        return adminNo;
    }

    public void setAdminNo(Integer adminNo) {
        this.adminNo = adminNo;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }
}
