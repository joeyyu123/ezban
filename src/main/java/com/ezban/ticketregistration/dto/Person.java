package com.ezban.ticketregistration.dto;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private String name;
    private String email;
    private String phone;
    private List<Response> responses;
}
