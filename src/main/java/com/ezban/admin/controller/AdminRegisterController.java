package com.ezban.admin.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminRepository;
import com.ezban.host.model.HostMailService;

@RestController
public class AdminRegisterController {

    @Autowired
    AdminRepository adminRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/adminregister")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin) {
        try {
            Optional<Admin> existingAdmin = adminRepository.findByAdminMail(admin.getAdminMail());
            if (existingAdmin.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("該電子郵件已被使用");
            }

            adminRepository.save(admin);
           
            return ResponseEntity.ok("註冊成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊時發生內部錯誤: " + e.getMessage());
        }
    }
}
