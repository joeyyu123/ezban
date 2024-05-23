package com.ezban.admin.controller;

import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminService;
import com.ezban.admin.model.AdminPasswordChangeRequest;

@RestController
@RequestMapping("/api/admin")
public class AdminPassReviseController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/adminpassrevise")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody AdminPasswordChangeRequest request) {
        System.out.println("Received request: Account = " + request.getAccount()
                           + ", OldPassword = " + request.getOldPassword()
                           + ", NewPassword = " + request.getNewPassword());

        Optional<Admin> adminOptional = adminService.findAdminByAccount(request.getAccount());
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
// 加密邏輯            if (passwordEncoder.matches(request.getOldPassword(), admin.getAdminPwd())) {
//                admin.setAdminPwd(passwordEncoder.encode(request.getNewPassword()));
            if (request.getOldPassword().equals(admin.getAdminPwd())) {//使用未加密密碼
            	admin.setAdminPwd(request.getNewPassword());           //使用未加密密碼
            	
                adminService.saveAdmin(admin);
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "密碼更新成功。");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "當前密碼不正確。");
                return ResponseEntity.badRequest().body(response);
            }
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "帳號不存在。");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
