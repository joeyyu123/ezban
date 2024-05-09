package com.ezban.host.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostRepository;

@RestController
public class HostLoginController {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  

    @PostMapping("/host/login")
    @Transactional  
    public ResponseEntity<String> login(@RequestBody Host loginDetails) {
        Optional<Host> optionalHost = hostRepository.findByHostAccount(loginDetails.getHostAccount());
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            if (passwordEncoder.matches(loginDetails.getHostPwd(), host.getHostPwd())) {
                host.sethostlogin(LocalDateTime.now());  
                hostRepository.save(host);  
                return ResponseEntity.ok("登入成功！");
            }
        }
        return ResponseEntity.status(401).body("登入失敗：帳號或密碼錯誤。");
    }
}
