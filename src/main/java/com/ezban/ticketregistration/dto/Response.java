package com.ezban.ticketregistration.dto;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private String question;
    private Object response;
    private String component;
}
