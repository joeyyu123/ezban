package com.ezban.ticketorder.model;

public enum TicketOrderStatus {
    PROCESSING((byte)0),
    FINISHED((byte)1),
    CANCELED((byte)2);

    private final byte value;

    private TicketOrderStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
