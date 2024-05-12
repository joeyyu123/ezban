package com.ezban.event.model;

public enum EventStatus {
    DRAFT((byte) 0),
    PUBLISHED((byte) 1),
    ARCHIVED((byte) 2),
    FINISHED((byte) 3);

    private final byte value;

    private EventStatus(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
