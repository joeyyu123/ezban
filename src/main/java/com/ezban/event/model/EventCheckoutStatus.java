package com.ezban.event.model;

public enum EventCheckoutStatus {
    NOT_CHECKED((byte)0),
    CHECKED((byte)0);

    private final Byte value;

    EventCheckoutStatus(Byte value) {
        this.value = value;
    }

    public Byte getValue() {
        return value;
    }
}
