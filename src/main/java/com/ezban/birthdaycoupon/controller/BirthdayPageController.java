package com.ezban.birthdaycoupon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BirthdayPageController {

    @GetMapping("/birthdaycoupon")
    public String showBirthdayCoupon() {
        return "frontstage/birthdaycoupon/birthdaycoupon";
    }
}
