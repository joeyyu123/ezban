package com.ezban.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezban.qa.model.Qa;
import com.ezban.qa.model.QaService;

@Controller
public class QaController {

    @Autowired
    private QaService qaService;

    @GetMapping("/qa")
    public String qaPage(Model model) {
        List<Qa> questions = qaService.getAllQa();
        model.addAttribute("questions", questions);
        return "qa";
    }

    @GetMapping("/qa/add")
    public String addQa(@RequestParam String title, @RequestParam String content) {
        qaService.addQa(title, content);
        return "redirect:/qa";
    }
}
