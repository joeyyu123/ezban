package com.ezban.chatroom.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatMessage {
    private String type;
    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
    private Integer eventNo;

    public ChatMessage() {
    }
    public ChatMessage(String type, String sender, String receiver, String message) {
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getEventNo() {
        return eventNo;
    }

    public void setEventNo(Integer eventNo) {
        this.eventNo = eventNo;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", eventNo='" + eventNo + '\'' +
                '}';
    }
}
