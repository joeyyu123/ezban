package com.ezban.ticketorder.model;

public enum TicketOrderAllocationStatus {
    NOT_ALLOCATED((byte) 0),
    ALLOCATED((byte) 1);

    private final byte value;

    TicketOrderAllocationStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
