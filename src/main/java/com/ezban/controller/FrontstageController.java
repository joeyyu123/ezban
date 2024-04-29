package com.ezban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontstage")
public class FrontstageController {

    @GetMapping("")
    public String index() {
        return "/frontstage/index2";
    }

    @GetMapping("/template")
    public String template() {
        return "/frontstage/template2";
    }

}
