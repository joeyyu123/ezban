package com.ezban.ticketorder.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Dto {
    private String eventNo;
    private String ticketOrderNo;
    private List<TicketOrderRegistrationForm> ticketOrderRegistrationForms;
}
