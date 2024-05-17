package com.ezban.productreport.model;

import javax.validation.constraints.NotNull;

public class AddProductReportDTO {

    private Integer productNo;

    @NotNull(message = "* 檢舉原因: 請勿空白 !")
    private String reportReason;

    @NotNull(message = "* 會員編號: 請勿空白 !")
    private Integer memberNo;

    public AddProductReportDTO() {

    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }
}
