package com.ezban.chatroom.controller;

import com.ezban.chatroom.model.ChatMessage;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/MemberChatController/{memberName}")
public class MemberChatController {
    private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
    private Gson gson = new Gson();

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        MemberChatController.redisTemplate = redisTemplate;
    }

    @OnOpen
    public void onOpen(@PathParam("memberName") String memberName, Session memberSession) throws IOException {
        sessionsMap.put(memberName, memberSession);
        System.out.println("Member connected: " + memberName);
    }

    @OnMessage
    public void onMessage(Session memberSession, String message) {
        ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
        handleChatMessage(memberSession, chatMessage);
    }

    private void handleChatMessage(Session memberSession, ChatMessage chatMessage) {
        String receiver = chatMessage.getReceiver();
        Session receiverSession = sessionsMap.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.getAsyncRemote().sendText(gson.toJson(chatMessage));
        }
        saveMessageToRedis(chatMessage);
    }

    private void saveMessageToRedis(ChatMessage message) {
        String key = "chat:" + message.getSender() + ":" + message.getReceiver();
        String messageJson = gson.toJson(message);
        redisTemplate.opsForList().rightPush(key, messageJson);
    }

    @OnError
    public void onError(Session memberSession, Throwable e) {
        System.out.println("Error: " + e.toString());
    }

    @OnClose
    public void onClose(Session memberSession, CloseReason reason) {
        String memberName = null;
        for (Map.Entry<String, Session> entry : sessionsMap.entrySet()) {
            if (entry.getValue().equals(memberSession)) {
                memberName = entry.getKey();
                break;
            }
        }
        if (memberName != null) {
            sessionsMap.remove(memberName);
            System.out.println("Member disconnected: " + memberName + "; Reason: " + reason.getReasonPhrase());
        }
    }
}
