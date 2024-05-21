package com.ezban.saveevent.controller;


import com.ezban.saveevent.model.AddSaveEventDTO;
import com.ezban.saveevent.model.SaveEvent;
import com.ezban.saveevent.model.SaveEventDTO;

import com.ezban.saveevent.model.SaveEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/saveevent")
public class SaveEventController {

    @Autowired
    SaveEventService saveEventSvc;

//    //取得某個會員的所有活動收藏(所有0、1)
//    @GetMapping("/findByMember/{memberNo}")
//    public String findByMember(@PathVariable("memberNo") Integer memberNo, Model model) {
//        List<SaveEvent> saveEvents = saveEventSvc.findSaveEventsByMember(memberNo);
//        model.addAttribute("saveEventListData", saveEvents);
////        model.addAttribute("memberNo", memberNo);  // 動態方式需要
//        return "frontstage/saveevent/saveEventListByMember";
//    }

    // 取得某個會員的所有收藏活動，僅返回收藏狀態為 1 的活動
    @GetMapping("/findByMember")
    public String findOnlyFavoritesByMember(Principal principal, Model model) {
        Integer memberNo = Integer.parseInt(principal.getName());
        List<SaveEventDTO> saveEvents = saveEventSvc.findOnlyFavoritesByMember(memberNo);
        model.addAttribute("saveEventListData", saveEvents);
        return "frontstage/saveevent/saveEventListByMember";
    }

    // 取得某個會員的所有收藏活動，返回收藏狀態為 1 和 0 的活動(愛心)
    @GetMapping("/findFavoritesByMember")
    @ResponseBody
    public ResponseEntity<List<SaveEventDTO>> findFavoritesByMember(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());
        List<SaveEventDTO> saveEvents = saveEventSvc.findSaveEventDTOsByMember(memberNo);
        return new ResponseEntity<>(saveEvents, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        Integer memberNo = Integer.parseInt(principal.getName());
        model.addAttribute("memberNo", memberNo);
        return "profile";
    }

    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insertSaveEvent(Principal principal, @RequestParam("eventNo") Integer eventNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer memberNo = Integer.parseInt(principal.getName());
            AddSaveEventDTO addSaveEventDTO = new AddSaveEventDTO();
            addSaveEventDTO.setMemberNo(memberNo);
            addSaveEventDTO.setEventNo(eventNo);

            SaveEvent saveEvent = saveEventSvc.changtSaveStatus(addSaveEventDTO);

            response.put("success", true);
            response.put("saveStatus", saveEvent.getSaveStatus());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "操作失敗");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
