package com.ezban.chatroom.controller;

import com.ezban.chatroom.model.ChatMessage;
import com.ezban.chatroom.model.ChatService;
import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class ChatController {

    private final Gson gson = new Gson();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private HostService hostService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ChatService chatService;

    // host聊天介面
    @GetMapping("/backstage/hostchat/hostchat")
    public String hostChat(Model model, Principal principal) {
        String hostNo = principal.getName();
        Host host = hostService.findHostByHostNo(hostNo).orElseThrow();
        model.addAttribute("hostName", host.getHostName());
        return "backstage/hostchat/hostchat";
    }

    //member聊天介面
    @GetMapping("/frontstage/memberchat")
    public String memberChat(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/loginPage";
        } else {
            // 已登入member
            String memberNo = principal.getName();
            Member member = memberService.getMemberById(Integer.parseInt(memberNo));
            model.addAttribute("memberName", member.getMemberName());

            model.addAttribute("hostName", "someHostName");
            model.addAttribute("eventNo", "someEventNo");
        }
        return "frontstage/memberchat/memberchat";
    }



    // 顯示活動聊天頁面
    @GetMapping("/events/chat/{eventNo}")
    public String showEventChat(@PathVariable("eventNo") Integer eventNo, Principal principal, Model model) {
        if (principal == null) {
            // 未登入的訪客，導到登入頁面
            return "redirect:/loginPage";
        }

        // 已登入的會員
        Event event = eventService.findEventByEventNo(eventNo)
                .orElseThrow(() -> new RuntimeException("活動未找到"));
        model.addAttribute("event", event);

        String memberNo = principal.getName();
        String memberName = memberService.getMemberNameByMemberNoChat(Integer.parseInt(memberNo));
        model.addAttribute("memberName", memberName);

        String hostName = hostService.getHostNameByEventNo(eventNo);
        model.addAttribute("hostName", hostName);

        return "event";
    }

    // 處理 member 發送訊息的 WebSocket 請求
    @MessageMapping("/sendMessage/{eventNo}")
    public void receiveMessageFromMember(@Payload ChatMessage chatMessage, @DestinationVariable String eventNo) {
        System.out.println("Received message from member: " + chatMessage);
        chatService.saveChatMessage(chatMessage);
        messagingTemplate.convertAndSend("/message/event/" + eventNo, chatMessage);
    }

    // 處理 host 發送訊息的 WebSocket 請求
    @MessageMapping("/hostchat/sendMessage/{eventNo}")
    public void receiveMessageFromHost(@Payload ChatMessage chatMessage, @DestinationVariable String eventNo) {
        System.out.println("Received message from host: " + chatMessage);
        chatService.saveChatMessage(chatMessage);
        messagingTemplate.convertAndSend("/message/event/" + eventNo, chatMessage);
    }

    // 獲取 member 的歷史聊天紀錄
    @GetMapping("/frontstage/memberchat/history/{memberNo}/{hostAccount}")
    @ResponseBody
    public List<ChatMessage> getMemberChatHistory(@PathVariable String memberNo, @PathVariable String hostAccount) {
        List<ChatMessage> chatHistory = chatService.getChatHistory(memberNo, hostAccount);
        System.out.println("Fetching chat history for member: " + memberNo + " and host: " + hostAccount);
        System.out.println("Chat history: " + chatHistory);
        return chatHistory;
    }

    // 獲取 host 的歷史聊天紀錄
    @GetMapping("/backstage/hostchat/history/{memberName}/{hostName}")
    @ResponseBody
    public List<ChatMessage> getHostChatHistory(@PathVariable String memberName, @PathVariable String hostName) {
        List<ChatMessage> chatHistory = chatService.getChatHistory(memberName, hostName);
        System.out.println("Fetching chat history for member: " + memberName + " and host: " + hostName);
        System.out.println("Chat history: " + chatHistory);
        return chatHistory;
    }

    // 獲取 host 的最後訊息
    @GetMapping("/backstage/hostchat/lastMessages")
    @ResponseBody
    public Map<String, ChatMessage> getLastMessages(Principal principal) {
        String hostNo = principal.getName();
        return chatService.getLastMessages(hostNo);
    }

    // 取得與當前廠商有過聯繫的所有會員
    @GetMapping("/backstage/hostchat/members")
    @ResponseBody
    public List<String> getMembers(Principal principal) {
        String hostNo = principal.getName();
        return chatService.getMembersByHost(hostNo);
    }

    @GetMapping("/frontstage/memberchat/checkLoginStatus")
    public ResponseEntity<Map<String, Boolean>> checkLoginStatus(Authentication authentication) {
        Map<String, Boolean> response = new HashMap<>();
        boolean loggedIn = authentication != null && authentication.isAuthenticated();
        response.put("loggedIn", loggedIn);
        if (authentication != null) {
            System.out.println("member已經過驗證: " + authentication.getName());
        } else {
            System.out.println("member未經過驗證");
        }
        return ResponseEntity.ok(response);
    }


    @GetMapping("/frontstage/memberchat/loadMemberChat/{eventNo}")
    @ResponseBody
    public String loadMemberChat(@PathVariable("eventNo") Integer eventNo, Principal principal) {
        if (principal == null) {
            // 未登入的訪客，導到登入頁面
            return "redirect:/loginPage";
        } else {
            // 已登入的會員
            String memberNo = principal.getName();
            Member member = memberService.findMemberByIdChat(Integer.parseInt(memberNo));
            String memberName = member.getMemberName();

            System.out.println("MemberNo: " + memberNo);
            System.out.println("MemberName: " + memberName);

            // 根據 eventNo 查找對應的 hostName
            String hostName = hostService.getHostNameByEventNo(eventNo);

            System.out.println("EventNo: " + eventNo);
            System.out.println("HostName: " + hostName);

            // 返回聊天室的HTML內容
            return "<div id=\"chatContainerInner\">" +
                    "<h1>會員聊天室</h1>" +
                    "<div>活動廠商客服</div>" +
                    "<div id=\"status\" style=\"color:red;\">未連線</div>" +
                    "<div id=\"chatHistory\"></div>" +
                    "<div id=\"inputContainer\">" +
                    "<input id=\"messageInput\" placeholder=\"輸入訊息...\" onkeypress=\"handleKeyPress(event)\">" +
                    "<button id=\"sendButton\" onclick=\"sendMessage()\">發送</button>" +
                    "<input type=\"hidden\" name=\"memberName\" value=\"" + memberName + "\">" +
                    "<input type=\"hidden\" name=\"hostName\" value=\"" + hostName + "\">" +
                    "<input type=\"hidden\" name=\"eventNo\" value=\"" + eventNo + "\">" +
                    "</div></div>";
        }
    }

}