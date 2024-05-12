package com.ezban.ticketorder.model;

public enum TicketOrderPaymentStatus {
    UNPAID((byte)0),
    FAILED((byte)1),
    OVERTIME((byte)2),
    PAID((byte)3),
    REFUNDING((byte)4),
    REFUNDED((byte)5);

    private final byte value;

    private TicketOrderPaymentStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
