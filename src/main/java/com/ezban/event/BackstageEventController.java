package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.event.model.EventStatus;
import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcategory.model.EventCategoryService;
import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/backstage")
public class BackstageEventController {
    @Autowired
    HostService hostService;

    @Autowired
    EventService eventService;

    @Autowired
    EventCategoryService eventCategoryService;


    /**
     * 新增活動頁面
     */
    @GetMapping("/event")
    public String event(Model model) {
        List<EventCategory> eventCategories = eventCategoryService.findAll();
        model.addAttribute("categories", eventCategories);
        return "/backstage/event/event";
    }

    /**
     * 新增活動
     */
    @PostMapping("/event")
    public String create(Model model,
                         Principal principal,
                         @RequestParam Map<String, String> allParams,
                         @RequestParam(value = "eventImg", required = false) MultipartFile eventImg) throws IOException {

        Host host = hostService.findHostByAccount(principal.getName()).orElseThrow();

        Event event = new Event();
        EventCategory category = eventCategoryService.findById(Integer.parseInt(allParams.get("eventCategory")));
        event.setHost(host);
        event.setEventStatus(EventStatus.DRAFT);
        setEventInfo(allParams, event, category);

        // 將圖片轉換為byte[] 並設置到 eventImg 屬性中
        if (eventImg != null && !eventImg.isEmpty()) {
            byte[] imageBytes = eventImg.getBytes();
            event.setEventImg(imageBytes);
        }

        event = eventService.add(event);
        model.addAttribute("event", event);
        return "redirect:/backstage/events/" + event.getEventNo() + "/desc";
    }

    /**
     * 顯示活動列表頁面
     */
    @GetMapping("/events")
    public String events(Model model, Principal principal) {
        Host host = hostService.findHostByAccount(principal.getName()).orElseThrow();
        model.addAttribute("events", eventService.findByHostNo(host.getHostNo()));
        return "/backstage/event/events";
    }

    @GetMapping("/events/{eventNo}/overview")
    public String overview(Principal principal, Model model, @PathVariable Integer eventNo) {
        Integer hostNo = hostService.findHostByAccount(principal.getName()).orElseThrow().getHostNo();

        Event event = eventService.findById(eventNo);
        if (!Objects.equals(event.getHost().getHostNo(), hostNo)) {
            model.addAttribute("message", "你無權查看該活動");
            return "/backstage/event/warning";
        }

        model.addAttribute("event", eventService.findById(eventNo));
        model.addAttribute("eventNo", eventNo);
        return "/backstage/event/event-overview";
    }

    /**
     * 查看活動詳細內容頁面
     */
    @GetMapping("/events/{eventNo}")
    public String eventInfo(Model model, Principal principal, @PathVariable Integer eventNo) {
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }
        model.addAttribute("event", eventService.findById(eventNo));
        model.addAttribute("eventNo", eventNo);
        model.addAttribute("categories", eventCategoryService.findAll());
        return "/backstage/event/event";
    }




    /**
     * 修改活動詳細內容
     */
    @PutMapping("/events/{eventNo}/desc")
    @ResponseBody
    public ResponseEntity<String> updateDesc(Model model, @PathVariable Integer eventNo, @RequestBody Map<String, String> event, Principal principal) {
        Event existingEvent = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, existingEvent)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to access this page.");
        }
        existingEvent.setEventDesc(event.get("eventDesc"));
        eventService.update(existingEvent);
        model.addAttribute("event", existingEvent);
        model.addAttribute("eventNo", eventNo);
        return ResponseEntity.status(HttpStatus.OK).body("儲存成功");
    }

    /**
     * 更新活動基本資料
     */
    @PutMapping("/events/{eventNo}")
    public String update(Model model, Principal principal, @PathVariable Integer eventNo,
                         @RequestParam Map<String, String> allParams,
                         @RequestParam(value = "eventImg", required = false) MultipartFile eventImg,
                         RedirectAttributes redirectAttributes) throws IOException {

        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }
        EventCategory category = eventCategoryService.findById(Integer.parseInt(allParams.get("eventCategory")));
        setEventInfo(allParams, event, category);

        // 將圖片轉換為byte[] 並設置到 eventImg 屬性中
        if (eventImg != null && !eventImg.isEmpty()) {
            byte[] imageBytes = eventImg.getBytes();
            event.setEventImg(imageBytes);
        }
        // 儲存更新的活動
        eventService.update(event);
        redirectAttributes.addFlashAttribute("message", "儲存成功");
        return "redirect:/backstage/events/" + eventNo;
    }

    /**
     * 查看活動描述頁面
     */
    @GetMapping("/events/{eventNo}/desc")
    public String desc(Model model, Principal principal, @PathVariable Integer eventNo) {
        Event event = eventService.findById(eventNo);
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "/backstage/event/warning";
        }
        model.addAttribute("event", eventService.findById(eventNo));
        return "/backstage/event/event-desc";
    }


    private void setEventInfo(@RequestParam Map<String, String> allParams, Event event, EventCategory category) {
        event.setEventName(allParams.get("eventName"));
        event.setEventCategory(category);
        event.setEventAddTime(formatDate(allParams.get("eventAddTime")));
        event.setEventRemoveTime(formatDate(allParams.get("eventRemoveTime")));
        event.setEventStartTime(formatDate(allParams.get("eventStartTime")));
        event.setEventEndTime(formatDate(allParams.get("eventEndTime")));
        event.setEventCity(allParams.get("eventCity"));
        event.setEventDetailedAddress(allParams.get("eventDetailedAddress"));
    }

    /**
     * 將 html的localDateTime 格式化為 Timestamp
     */
    private Timestamp formatDate(String date) {
        if (date == null || date.isEmpty()) {
            return null;
        }
        String dateString = date.toString().replace("T", " ");
        LocalDateTime dateTime;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try {
            dateTime = LocalDateTime.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            dateString += ":00";
            dateTime = LocalDateTime.parse(dateString, formatter);
        }

        return Timestamp.valueOf(dateTime);
    }


}
