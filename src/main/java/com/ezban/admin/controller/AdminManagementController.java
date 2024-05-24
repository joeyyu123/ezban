package com.ezban.admin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;

@Controller
@RequestMapping("/adminsManagement")
public class AdminManagementController {
	
	@Autowired
    private AdminService adminService;

    @GetMapping("/")
    public String getAllAdmins(Model model) {
        model.addAttribute("admins", adminService.getAll());
        return "admins";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Admin> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }
    
    @PutMapping("/{id}/disable")
    @ResponseBody
    public void disableAdmin(@PathVariable Integer id) {
        adminService.disableAdmin(id);
    }

}
