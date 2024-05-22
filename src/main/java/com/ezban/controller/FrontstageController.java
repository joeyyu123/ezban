package com.ezban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
public class FrontstageController {

    @GetMapping("")
    public String index(HttpSession session) {
//        session.setAttribute("memberNo", 2);
        return "/frontstage/index2";
    }

    @GetMapping("/template")
    public String template() {
        return "/frontstage/template2";
    }

}
