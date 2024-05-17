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
    
    @GetMapping("/qaback")
    public String showqaback() {
    	return "backstage/qaback/qaback";
    }
    
    @GetMapping("/qa")
    public String showqa() {
    	return "frontstage/qa/qa";
    }
    
    @GetMapping("/productcomment")
    public String showproductcomment() {
    	return "frontstage/product/productcomment";
    }
    
    @GetMapping("/adminregister")
    public String showadminregister() {
        return "backstage/admin/adminregister";  
    }
    
    @GetMapping("/adminlogin")
    public String showadminlogin() {
        return "backstage/admin/adminlogin";  
    }
    
    @GetMapping("/adminpassrevise")
    public String showadminpassrevise() {
        return "backstage/admin/adminpassrevise";  
    }
    
    @GetMapping("/adminpasswordreset")
    public String showadminpasswordreset() {
        return "backstage/admin/adminpasswordreset";  
    }
}
	