package com.ezban.host.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HostPageController {

    @GetMapping("/hostlogin")
    public String showLoginPage() {
        return "backstage/HostLogin/hostlogin";  
    }

    @GetMapping("/hostregister")
    public String showRegisterPage() {
        return "backstage/HostLogin/hostregister";  
    }
    
    @GetMapping("/passwordreset")
    public String showpasswordreset() {
    	return "backstage/HostLogin/passwordreset";
    }
    
    @GetMapping("/hostpassrevise")
    public String showhostpassrevise() {
    	return "backstage/HostLogin/hostpassrevise";
    }
}
	