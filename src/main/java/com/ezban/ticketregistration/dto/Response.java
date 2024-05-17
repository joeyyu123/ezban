package com.ezban.ticketregistration.dto;

import lombok.Data;

import java.util.List;

@Data
public class Response {
    private String question;
    private Object response;
    private String component;


    public String parseResponse() {
        if (response instanceof List) {
            List<String> responseList = (List<String>) response;
            // remove[]

            return String.join(", ", responseList);
        }
        return response.toString();
    }
}
