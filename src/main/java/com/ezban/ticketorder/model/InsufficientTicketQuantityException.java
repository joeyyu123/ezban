package com.ezban.ticketorder.model;

public class InsufficientTicketQuantityException extends RuntimeException {
    public InsufficientTicketQuantityException(String message) {
        super(message);
    }
}
