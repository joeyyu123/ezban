package com.ezban.host.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostMailService;
import com.ezban.host.model.HostRepository;

@Controller
public class HostPasswordResetController {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostMailService hostMailService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/reset-password")
    @ResponseBody
    public String resetPassword(@RequestParam("hostAccount") String account, @RequestParam("hostMail") String email) {
        Optional<Host> hostOptional = hostRepository.findByHostAccountAndHostMail(account, email);
        if (hostOptional.isPresent()) {
            Host host = hostOptional.get();
            String randomPassword = hostMailService.sendRandomPasswordEmail(host.getHostMail(), "Password Reset");
            
//            String encryptedPassword = passwordEncoder.encode(randomPassword); // 加密密碼
//            host.setHostPwd(encryptedPassword); // 設置加密後密碼
            
            host.setHostPwd(randomPassword); // 直接使用未加密的隨機密碼
            
            hostRepository.save(host); // 儲存更新後的資料
            return "新密碼已發送到您的電子郵件。";
        } else {
            return "提供的資料匹配不到任何用戶。";
        }
    }
}
