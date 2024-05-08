package com.ezban.event;

import com.ezban.event.model.Event;
import com.ezban.event.model.EventService;
import com.ezban.eventcategory.model.EventCategory;
import com.ezban.eventcategory.model.EventCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

@Controller
@RequestMapping("/backstage/events")
public class BackstageEventController {

    @Autowired
    EventService eventService;

    @Autowired
    EventCategoryService eventCategoryService;


    /**
     * 顯示活動列表頁面
     */
    @GetMapping("")
    public String events(Model model) {
        // TODO 取得session 並顯示該host 的活動
        // 從session 取得hostNo，並查詢該host 的活動
        // Integer hostNo = (Integer) session.getAttribute("hostNo");
        // model.addAttribute("events", eventService.findByHostNo(hostNo));

        model.addAttribute("events", eventService.findAll());
        return "/backstage/event/events";
    }

    @GetMapping("/{eventNo}/overview")
    public String overview(Model model, @PathVariable Integer eventNo) {
        model.addAttribute("event", eventService.findById(eventNo));
        model.addAttribute("eventNo", eventNo);
        return "/backstage/event/event-overview";
    }
    /**
     * 查看活動詳細內容頁面
     */
    @GetMapping("/{eventNo}")
    public String event(Model model, @PathVariable Integer eventNo) {
        model.addAttribute("event", eventService.findById(eventNo));
        model.addAttribute("eventNo", eventNo);
        model.addAttribute("categories", eventCategoryService.findAll());
        return "/backstage/event/event";
    }


    /**
     * 修改活動詳細內容
     */
    @PutMapping("/{eventNo}/desc")
    @ResponseBody
    public String updateDesc(Model model, @PathVariable Integer eventNo, @RequestBody Map<String, String> event) {
        Event existingEvent = eventService.findById(eventNo);
        existingEvent.setEventDesc(event.get("eventDesc"));
        eventService.update(existingEvent);
        model.addAttribute("event", existingEvent);
        model.addAttribute("eventNo", eventNo);
        return "success";
    }

    // @PostMapping("/backstage/events/{eventNo}")
    // public String create(Model model, @PathVariable Integer eventNo,
    //                      @RequestParam Map<String, String> allParams,
    //                      @RequestParam(value = "eventImg", required = false) MultipartFile eventImg) {

    //     Event event = new Event();

    //     return "redirect:/backstage/events/" + eventNo;
    // }


    /**
     * 更新活動基本資料
     */
    @PutMapping("/{eventNo}")
    public String update(Model model, @PathVariable Integer eventNo,
                         @RequestParam Map<String, String> allParams,
                         @RequestParam(value = "eventImg", required = false) MultipartFile eventImg) throws IOException {

        Event event = eventService.findById(eventNo);
        EventCategory category = eventCategoryService.findById(Integer.parseInt(allParams.get("eventCategory")));
        event.setEventName(allParams.get("eventName"));
        event.setEventCategory(category);
        event.setEventAddTime(formatDate(allParams.get("eventAddTime")));
        event.setEventRemoveTime(formatDate(allParams.get("eventRemoveTime")));
        event.setEventStartTime(formatDate(allParams.get("eventStartTime")));
        event.setEventEndTime(formatDate(allParams.get("eventEndTime")));
        event.setRegistrationStartTime(formatDate(allParams.get("registrationStartTime")));
        event.setRegistrationEndTime(formatDate(allParams.get("registrationEndTime")));
        event.setEventCity(allParams.get("eventCity"));
        event.setEventDetailedAddress(allParams.get("eventDetailedAddress"));

        // 將圖片轉換為byte[] 並設置到 eventImg 屬性中
        if (eventImg != null && !eventImg.isEmpty()) {
            byte[] imageBytes = eventImg.getBytes();
            event.setEventImg(imageBytes);
        }
        // 儲存更新的活動
        eventService.update(event);
        model.addAttribute("event", event);
        return "redirect:/backstage/events/" + eventNo;
    }

    /**
     * 查看活動描述頁面
     */
    @GetMapping("/{eventNo}/desc")
    public String desc(Model model, @PathVariable Integer eventNo) {
        model.addAttribute("event", eventService.findById(eventNo));
        return "/backstage/event/event-desc";
    }




    /**
     * 將 html的localDateTime 格式化為 Timestamp
     */

    private Timestamp formatDate(Object date) {
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
