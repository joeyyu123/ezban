package com.ezban.ticketorder.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Question {
    private String component;
    private String label;
    private String description;
    private List<String> options;
    private boolean required;

}
