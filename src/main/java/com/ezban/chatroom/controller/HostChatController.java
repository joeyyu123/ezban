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
@ServerEndpoint("/HostChatController/{hostName}")
public class HostChatController {
    private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
    private Gson gson = new Gson();

    private static RedisTemplate<String, String> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        HostChatController.redisTemplate = redisTemplate;
    }

    @OnOpen
    public void onOpen(@PathParam("hostName") String hostName, Session hostSession) throws IOException {
        sessionsMap.put(hostName, hostSession);
        System.out.println("Host connected: " + hostName);
    }

    @OnMessage
    public void onMessage(Session hostSession, String message) {
        ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
        handleNormalMessage(hostSession, chatMessage);
    }

    private void handleNormalMessage(Session hostSession, ChatMessage chatMessage) {
        String receiver = chatMessage.getReceiver();
        Session receiverSession = sessionsMap.get(receiver);
        if (receiverSession != null && receiverSession.isOpen()) {
            receiverSession.getAsyncRemote().sendText(gson.toJson(chatMessage));
        }
        saveMessageToRedis(chatMessage);
    }

    private void saveMessageToRedis(ChatMessage message) {
        String key1 = "chat:" + message.getSender() + ":" + message.getReceiver();
        String key2 = "chat:" + message.getReceiver() + ":" + message.getSender();
        String messageJson = gson.toJson(message);
        redisTemplate.opsForList().rightPush(key1, messageJson);
        redisTemplate.opsForList().rightPush(key2, messageJson);
    }


    @OnError
    public void onError(Session hostSession, Throwable e) {
        System.out.println("Host error: " + e.toString());
    }

    @OnClose
    public void onClose(Session hostSession, CloseReason reason) {
        String hostName = null;
        for (Map.Entry<String, Session> entry : sessionsMap.entrySet()) {
            if (entry.getValue().equals(hostSession)) {
                hostName = entry.getKey();
                break;
            }
        }
        if (hostName != null) {
            sessionsMap.remove(hostName);
            System.out.println("Host disconnected: " + hostName + "; Reason: " + reason.getReasonPhrase());
        }
    }
}
