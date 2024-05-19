package com.ezban.ticketorderdetail.model;

import lombok.Data;

@Data
public class TicketOrderDetailDto {
    private Integer ticketOrderNo;
    private String ticketTypeName;
    private Integer ticketTypePrice;
    private Integer ticketTypeQty;

}
