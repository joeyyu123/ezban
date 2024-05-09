package com.ezban.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezban.member.model.MemebrAuthenticate;

@Controller
public class LoginController {

    @Autowired
    private MemebrAuthenticate memberAuthenticate;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String memberMail = loginRequest.getMemberMail();
        String memberPwd = loginRequest.getMemberPwd();

        ResponseEntity<String> responseEntity = memberAuthenticate.checkAccount(memberMail, memberPwd);

        Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("code", responseEntity.getStatusCodeValue());
        responseObject.put("message", responseEntity.getBody());

        return ResponseEntity.ok(responseObject);
    }
    
    @GetMapping("/login")
    public String getLogin() {
        return "frontstage/login";
    }

    static class LoginRequest {
        private String memberMail;
        private String memberPwd;

        public String getMemberMail() {
            return memberMail;
        }

        public void setMemberMail(String memberMail) {
            this.memberMail = memberMail;
        }

        public String getMemberPwd() {
            return memberPwd;
        }

        public void setMemberPwd(String memberPwd) {
            this.memberPwd = memberPwd;
        }
    }
}
