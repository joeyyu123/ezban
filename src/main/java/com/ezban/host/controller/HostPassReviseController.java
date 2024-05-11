package com.ezban.host.controller;

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

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.host.model.PasswordChangeRequest;

@RestController
@RequestMapping("/api/host")
public class HostPassReviseController {

    @Autowired
    private HostService hostService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/hostpassrevise")
    public ResponseEntity<Map<String, Object>> changePassword(@RequestBody PasswordChangeRequest request) {
        System.out.println("Received request: Account = " + request.getAccount()
                           + ", OldPassword = " + request.getOldPassword()
                           + ", NewPassword = " + request.getNewPassword());

        Optional<Host> hostOptional = hostService.findHostByAccount(request.getAccount());
        if (hostOptional.isPresent()) {
            Host host = hostOptional.get();
//   加密邏輯      if (passwordEncoder.matches(request.getOldPassword(), host.getHostPwd())) {
//                host.setHostPwd(passwordEncoder.encode(request.getNewPassword()));
           
            if (request.getOldPassword().equals(host.getHostPwd())) {//使用未加密密碼
            	host.setHostPwd(request.getNewPassword());           //使用未加密密碼
            
            	hostService.saveHost(host);
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
