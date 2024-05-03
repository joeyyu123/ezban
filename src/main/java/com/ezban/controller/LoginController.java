package com.ezban.controller;

import com.ezban.member.model.MemebrAuthenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private MemebrAuthenticate memberAuthenticate;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String memberMail = loginRequest.getMemberMail();
        String memberPwd = loginRequest.getMemberPwd();

        ResponseEntity<String> response = memberAuthenticate.checkAccount(memberMail, memberPwd);

        return response;
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

