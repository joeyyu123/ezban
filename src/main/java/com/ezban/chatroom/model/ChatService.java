package com.ezban.chatroom.model;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HostChatRepository hostChatRepository;

    @Autowired
    private HostService hostService;

    private final Gson gson = new Gson();

    // 保存聊天訊息到 Redis
    public void saveChatMessage(ChatMessage message) {
        String key = "chat:" + message.getSender() + ":" + message.getReceiver();
        String messageJson = gson.toJson(message);
        redisTemplate.opsForList().rightPush(key, messageJson);
        System.out.println("保存消息到 Redis: " + messageJson + "，鍵：" + key);
    }

    // 獲取聊天記錄
    public List<ChatMessage> getChatHistory(String memberNo, String hostName) {
        String key1 = "chat:" + memberNo + ":" + hostName;
        String key2 = "chat:" + hostName + ":" + memberNo;
        List<String> messagesJson = redisTemplate.opsForList().range(key1, 0, -1);
        if (messagesJson == null || messagesJson.isEmpty()) {
            messagesJson = redisTemplate.opsForList().range(key2, 0, -1);
        }
        List<ChatMessage> messages = new ArrayList<>();
        if (messagesJson != null) {
            for (String messageJson : messagesJson) {
                messages.add(gson.fromJson(messageJson, ChatMessage.class));
            }
        }
        return messages;
    }


    // 獲取與當前廠商有過聊天的所有會員
    public List<String> getMembersByHost(String hostNo) {
        // 通过 hostNo 获取 hostName
        Optional<Host> hostOptional = hostService.findHostByHostNo(hostNo);
        if (!hostOptional.isPresent()) {
            System.out.println("找不到主持人編號: " + hostNo);
            return new ArrayList<>();
        }
        String hostName = hostOptional.get().getHostName();
        System.out.println("找到主持人名稱: " + hostName);

        Set<String> keys = redisTemplate.keys("chat:*");
        System.out.println("所有鍵: " + keys);
        Set<String> members = new HashSet<>();

        for (String key : keys) {
            String[] parts = key.split(":");
            if (parts.length == 3) {
                System.out.println("檢查鍵: " + key);
                if (parts[1].equals(hostName)) {
                    members.add(parts[2]);
                    System.out.println("找到會員: " + parts[2]);
                } else if (parts[2].equals(hostName)) {
                    members.add(parts[1]);
                    System.out.println("找到會員: " + parts[1]);
                }
            }
        }

        System.out.println("獲取與主持人 " + hostName + " 有過聊天的會員: " + members);
        return new ArrayList<>(members);
    }

    // 獲取 host 的最後訊息
    public Map<String, ChatMessage> getLastMessages(String hostNo) {
        // 通过 hostNo 获取 hostName
        Optional<Host> hostOptional = hostService.findHostByHostNo(hostNo);
        if (!hostOptional.isPresent()) {
            System.out.println("找不到主持人編號: " + hostNo);
            return new HashMap<>();
        }
        String hostName = hostOptional.get().getHostName();
        System.out.println("找到主持人名稱: " + hostName);

        Set<String> keys = redisTemplate.keys("chat:*");
        System.out.println("所有鍵: " + keys);
        Map<String, ChatMessage> lastMessages = new HashMap<>();

        for (String key : keys) {
            String[] parts = key.split(":");
            if (parts.length == 3) {
                System.out.println("檢查鍵: " + key);
                if (parts[1].equals(hostName) || parts[2].equals(hostName)) {
                    List<String> messages = redisTemplate.opsForList().range(key, -1, -1);
                    if (messages != null && !messages.isEmpty()) {
                        String memberNo = parts[1].equals(hostName) ? parts[2] : parts[1];
                        ChatMessage lastMessage = gson.fromJson(messages.get(0), ChatMessage.class);
                        lastMessages.put(memberNo, lastMessage);
                        System.out.println("添加最後訊息: " + lastMessage);
                    }
                }
            }
        }

        System.out.println("獲取主持人 " + hostName + " 的最後訊息: " + lastMessages);
        return lastMessages;
    }
}