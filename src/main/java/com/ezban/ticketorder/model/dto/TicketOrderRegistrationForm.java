package com.ezban.ticketorder.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TicketOrderRegistrationForm {
    private String eventNo;
    private String ticketTypeNo;
    private String ticketTypeName;
    private String ticketTypeQty;
    private String includedTickets;
    private List<Question> questions;
}
