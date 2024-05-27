package com.ezban.admin.controller;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/adminsManagement")
public class AdminManagementController {

    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminManagementController.class);

    @GetMapping("")
    public String getAllAdmins(Model model) {
        List<Admin> admins = adminService.getAllAdmins();
        logger.info("Admins: {}", admins);
        model.addAttribute("admins", admins);
        return "backstage/adminmanage/admin";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Optional<Admin> getAdminById(@PathVariable Integer id) {
        return adminService.getAdminById(id);
    }

    @PostMapping("/disable")
    public String disableAdmin(@RequestParam("adminNo") Integer adminNo) {
        adminService.disableAdmin(adminNo);
        return "redirect:/adminsManagement/";
    }

    @PostMapping("/enable")
    public String enableAdmin(@RequestParam("adminNo") Integer adminNo) {
        adminService.enableAdmin(adminNo);
        return "redirect:/adminsManagement/";
    }
    
    @GetMapping("/search")
    public String searchAdmins(@RequestParam("searchAccount") String searchAccount, Model model) {
        List<Admin> searchedAdmins = adminService.searchAdminsByAccount(searchAccount);
        model.addAttribute("admins", searchedAdmins);
        return "backstage/adminmanage/admin";
    }


}
