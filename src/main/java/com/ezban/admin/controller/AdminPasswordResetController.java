package com.ezban.admin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminRepository;
import com.ezban.host.model.HostMailService;

@Controller
public class AdminPasswordResetController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HostMailService hostMailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/admin/reset-password")
    @ResponseBody
    public String resetPassword(@RequestParam("adminAccount") String account, @RequestParam("adminMail") String email) {
        Optional<Admin> adminOptional = adminRepository.findByAdminAccountAndAdminMail(account, email);
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            String randomPassword = hostMailService.sendRandomPasswordEmail(admin.getAdminMail(), "Password Reset");

//            String encryptedPassword = passwordEncoder.encode(randomPassword); // 加密密碼
//            admin.setAdminPwd(encryptedPassword); // 設置加密後密碼
            
            admin.setAdminPwd(randomPassword); // 直接使用未加密的隨機密碼
            
            adminRepository.save(admin); // 儲存更新後的資料
            return "新密碼已發送到您的電子郵件。";
        } else {
            return "提供的資料匹配不到任何用戶。";
        }
    }
}
