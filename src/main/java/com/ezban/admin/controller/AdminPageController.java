package com.ezban.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {
	
	@GetMapping("/adminregister")
    public String showadminregister() {
        return "backstage/admin/adminregister";  
    }

}
