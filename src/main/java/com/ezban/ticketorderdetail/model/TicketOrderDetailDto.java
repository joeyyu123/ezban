package com.ezban.ticketorderdetail.model;

import lombok.Data;

@Data
public class TicketOrderDetailDto {
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private Integer ticketOrderNo;
    private String ticketTypeName;
    private Integer ticketTypePrice;
    private Integer ticketTypeQty;

}
