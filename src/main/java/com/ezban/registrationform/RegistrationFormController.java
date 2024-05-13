package com.ezban.registrationform;

import com.ezban.fieldExample.model.FieldExample;
import com.ezban.fieldExample.model.FieldExampleService;
import com.ezban.registrationform.model.RegistrationForm;
import com.ezban.registrationform.model.RegistrationFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backstage/events")
public class RegistrationFormController {
    @Autowired
    private RegistrationFormService registrationFormService;
    @Autowired
    private FieldExampleService fieldExampleService;

    /**
     * 查看報名表設定頁面
     */
    @GetMapping("/{eventNo}/form")
    public String registration(Model model, @PathVariable String eventNo) {
//        model.addAttribute("event", eventService.findById(eventNo));
        model.addAttribute("registrationForm", registrationFormService.findByEventNo(eventNo));
        model.addAttribute("fieldExamples", fieldExampleService.findAllSortByIdNumber());
        return "/backstage/event/registrationForm";
    }

    @PostMapping("{eventNo}/form")
    @ResponseBody
    public ResponseEntity<String> saveOrUpdate(Model model, @PathVariable String eventNo, @RequestBody Map<String, List<FieldExample>> request) {
        RegistrationForm registrationForm = registrationFormService.findByEventNo(eventNo);
        if (registrationForm == null) {
            registrationForm = new RegistrationForm();
            registrationForm.setEventNo(eventNo);
        }

        List<FieldExample> questions = request.get("questions");
        registrationForm.setQuestions(questions);

        registrationFormService.save(registrationForm);

        return ResponseEntity.ok("success");
    }
}
