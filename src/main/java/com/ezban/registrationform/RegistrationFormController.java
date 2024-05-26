package com.ezban.registrationform;

import com.ezban.event.model.Event;
import com.ezban.event.model.Service.EventService;
import com.ezban.fieldExample.model.FieldExample;
import com.ezban.fieldExample.model.FieldExampleService;
import com.ezban.registrationform.model.RegistrationForm;
import com.ezban.registrationform.model.RegistrationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static java.net.URI.create;

@Controller
@RequestMapping("/backstage/events")
public class RegistrationFormController {
    @Autowired
    private EventService eventService;
    @Autowired
    private RegistrationFormService registrationFormService;
    @Autowired
    private FieldExampleService fieldExampleService;

    /**
     * 查看報名表設定頁面
     */
    @GetMapping("/{eventNo}/form")
    public String registration(Model model, Principal principal, @PathVariable String eventNo) {
        model.addAttribute("event", eventService.findById(Integer.valueOf(eventNo)));
        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)) {
            model.addAttribute("message", "You are not authorized to access this page.");
            return "backstage/event/warning";
        }
        model.addAttribute("registrationForm", registrationFormService.findByEventNo(eventNo));
        model.addAttribute("fieldExamples", fieldExampleService.findAllSortByIdNumber());
        return "backstage/event/registration-form";
    }

    @PostMapping("{eventNo}/form")
    @ResponseBody
    public ResponseEntity<String> createRegistrationForm(Principal principal, @PathVariable String eventNo, @RequestBody Map<String, List<FieldExample>> request) {
        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to access this page.");
        }

        RegistrationForm registrationForm = new RegistrationForm();
        registrationForm.setEventNo(eventNo);

        List<FieldExample> questions = request.get("questions");
        registrationForm.setQuestions(questions);

        registrationFormService.save(registrationForm);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(create("/backstage/events/" + eventNo + "/overview")).build();
    }

    @PutMapping("{eventNo}/form")
    @ResponseBody
    public ResponseEntity<String> update(Principal principal, @PathVariable String eventNo, @RequestBody Map<String, List<FieldExample>> request) {
        Event event = eventService.findById(Integer.valueOf(eventNo));
        if (!eventService.isAuthenticated(principal, event)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not authorized to access this page.");
        }

        RegistrationForm registrationForm = registrationFormService.findByEventNo(eventNo);

        if (registrationForm == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Registration form not found.");
        }

        List<FieldExample> questions = request.get("questions");
        registrationForm.setQuestions(questions);

        registrationFormService.save(registrationForm);

        return ResponseEntity.ok("success");
    }
}
