package com.ezban.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;

//@RestController
//@RequestMapping("/api/admin")
////public class AdminController {
////
//    @Autowired
//    private AdminService adminService;
//
//    @PostMapping("/adminregister")
//    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
//        try {
////            Admin registeredAdmin = adminService.registerAdmin(admin);
//            return new ResponseEntity<>(registeredAdmin, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
//        }
//    }
//}
