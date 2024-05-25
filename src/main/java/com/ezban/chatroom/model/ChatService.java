package com.ezban.chatroom.model;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class ChatService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private HostChatRepository hostChatRepository;

    @Autowired
    private HostService hostService;

    @Autowired
    private MemberService memberService;

    private final Gson gson = new Gson();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;

    // 保存聊天訊息到 Redis
    public void saveChatMessage(ChatMessage message) {
        String key1 = "chat:" + message.getSender() + ":" + message.getReceiver() + ":" + message.getEventNo();
        String key2 = "chat:" + message.getReceiver() + ":" + message.getSender() + ":" + message.getEventNo();
        String messageJson = gson.toJson(message);
        redisTemplate.opsForList().rightPush(key1, messageJson);
        redisTemplate.opsForList().rightPush(key2, messageJson);
        System.out.println("保存消息到 Redis: " + messageJson + "，鍵：" + key1 + key2);
    }

    // 獲取聊天記錄
    public List<ChatMessage> getChatHistory(String memberName, String hostName, Integer eventNo) {
        String key1 = "chat:" + memberName + ":" + hostName + ":" + eventNo;
        String key2 = "chat:" + hostName + ":" + memberName + ":" + eventNo;
        List<String> messagesJson1 = redisTemplate.opsForList().range(key1, 0, -1);
        List<String> messagesJson2 = redisTemplate.opsForList().range(key2, 0, -1);

        List<ChatMessage> messages = new ArrayList<>();
        if (messagesJson1 != null) {
            for (String messageJson : messagesJson1) {
                ChatMessage message = gson.fromJson(messageJson, ChatMessage.class);
                messages.add(message);
            }
        }
        if (messagesJson2 != null) {
            for (String messageJson : messagesJson2) {
                ChatMessage message = gson.fromJson(messageJson, ChatMessage.class);
                messages.add(message);
            }
        }

        // 將消息按時間排序
        messages.sort(Comparator.comparing(ChatMessage::getTimestamp));

        return messages;
    }


    // 獲取與當前廠商有過聊天的所有會員
    public List<String> getMembersByHost(String hostNo) {

        Optional<Host> hostOptional = hostService.findHostByHostNo(hostNo);
        if (!hostOptional.isPresent()) {
//            System.out.println("找不到主持人編號: " + hostNo);
            return new ArrayList<>();
        }
        String hostName = hostOptional.get().getHostName();
//        System.out.println("找到主持人名稱: " + hostName);

        Set<String> keys = redisTemplate.keys("chat:*");
        if (keys == null || keys.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> members = new HashSet<>();

        for (String key : keys) {
            String[] parts = key.split(":");
            if (parts.length == 4) {
//                System.out.println("檢查鍵: " + key);
                if (parts[1].equals(hostName)) {
                    members.add(parts[2]);
//                    System.out.println("找到會員: " + parts[2]);
                } else if (parts[2].equals(hostName)) {
                    members.add(parts[1]);
//                    System.out.println("找到會員: " + parts[1]);
                }
            }
        }

//        System.out.println("獲取與主持人 " + hostName + " 有過聊天的會員: " + members);
        return new ArrayList<>(members);
    }

    // 獲取 host 的最後訊息
    public Map<String, ChatMessage> getHostLastMessages(String hostNo) {
        Optional<Host> hostOptional = hostService.findHostByHostNo(hostNo);
        if (!hostOptional.isPresent()) {
            System.out.println("找不到 host 編號: " + hostNo);
            return new HashMap<>();
        }
        String hostName = hostOptional.get().getHostName();
        System.out.println("找到 host 名稱: " + hostName);

        Set<String> keys = redisTemplate.keys("chat:*");
        System.out.println("所有鍵: " + keys);
        Map<String, ChatMessage> lastMessages = new HashMap<>();

        for (String key : keys) {
            String[] parts = key.split(":");
            if (parts.length == 4) {
                if (parts[1].equals(hostName) || parts[2].equals(hostName)) {
                    List<String> messages = redisTemplate.opsForList().range(key, 0, -1);
                    System.out.println("鍵: " + key + " 的訊息: " + messages);
                    if (messages != null && !messages.isEmpty()) {
                        String memberNo = parts[1].equals(hostName) ? parts[2] : parts[1];
                        for (String messageJson : messages) {
                            ChatMessage message = gson.fromJson(messageJson, ChatMessage.class);
                            ZonedDateTime messageTime = ZonedDateTime.parse(message.getTimestamp(), formatter);
                            if (!lastMessages.containsKey(memberNo) || ZonedDateTime.parse(lastMessages.get(memberNo).getTimestamp(), formatter).isBefore(messageTime)) {
                                lastMessages.put(memberNo, message);
                                System.out.println("添加最後訊息: " + message + " 給會員: " + memberNo);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("獲取 host " + hostName + " 的最後訊息: " + lastMessages);
        return lastMessages;
    }

    // 獲取 member 的最後訊息
    public Map<String, ChatMessage> getMemberLastMessages(String memberNo) {
        Optional<Member> memberOptional = memberService.findMemberByMemberNo(memberNo);
        if (!memberOptional.isPresent()) {
            System.out.println("找不到 member 編號: " + memberNo);
            return new HashMap<>();
        }
        String memberName = memberOptional.get().getMemberName();
        System.out.println("找到 member 名稱: " + memberName);

        Set<String> keys = redisTemplate.keys("chat:*");
        Map<String, ChatMessage> lastMessages = new HashMap<>();

        for (String key : keys) {
            String[] parts = key.split(":");
            if (parts.length == 3) {
                if (parts[1].equals(memberName) || parts[2].equals(memberName)) {
                    List<String> messages = redisTemplate.opsForList().range(key, 0, -1);
                    if (messages != null && !messages.isEmpty()) {
                        String hostName = parts[1].equals(memberName) ? parts[2] : parts[1];
                        for (String messageJson : messages) {
                            ChatMessage message = gson.fromJson(messageJson, ChatMessage.class);
                            ZonedDateTime messageTime = ZonedDateTime.parse(message.getTimestamp(), formatter);
                            if (!lastMessages.containsKey(hostName) || ZonedDateTime.parse(lastMessages.get(hostName).getTimestamp(), formatter).isBefore(messageTime)) {
                                lastMessages.put(hostName, message);
                                System.out.println("添加最後訊息: " + message);
                            }
                        }
                    }
                }
            }
        }

        System.out.println("獲取 member " + memberName + " 的最後訊息: " + lastMessages);
        return lastMessages;
    }
}