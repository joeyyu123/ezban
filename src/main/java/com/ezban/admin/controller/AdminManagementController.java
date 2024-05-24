package com.ezban.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;

@RestController
@RequestMapping("/adminsManagement")
public class AdminManagementController {
	
	@Autowired
    private AdminService adminService;

    @GetMapping("/")
    public List<Admin> getAllAdmins() {
        return adminService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Admin> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }
    
    @PutMapping("/{id}/disable")
    public void disableAdmin(@PathVariable Integer id) {
        adminService.disableAdmin(id);
    }

}
