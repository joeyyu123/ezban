package com.ezban.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/backstage")
public class BackstageController {

    @GetMapping("")
    public String index() {
        return "/backstage/index";
    }

    @GetMapping("/template")
    public String template() {
        return "/backstage/template";
    }

    @GetMapping("/productorder/selectPage")
    public String productOrderSelectPage() {return "/backstage/productorder/selectPage";}

}
