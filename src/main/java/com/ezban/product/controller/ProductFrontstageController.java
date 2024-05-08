package com.ezban.product.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/product")
public class ProductFrontstageController {

    @GetMapping("/shopall")
    public String shopall() {
        return "/frontstage/product/shopall";
    }

    @GetMapping("/shopdetail")
    public String shopdetail() {
        return "/frontstage/product/shopdetail";
    }


}
