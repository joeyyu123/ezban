package com.ezban.host.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostRepository;

@RestController
public class HostLoginController {

    @Autowired
    private HostRepository hostRepository;

    @PostMapping("/host/login")
    @Transactional
    public RedirectView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Host> optionalHost = hostRepository.findByHostAccount(username);
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
            if (password.equals(host.getHostPwd())) { // 直接比较未加密的密码
                host.setHostLogin(LocalDateTime.now());
                hostRepository.save(host);
                return new RedirectView("/backstage"); // 成功登录后重定向到 /backstage
            }
        }
        return new RedirectView("/hostlogin.html?error=true"); // 登录失败时重定向到登录页面并显示错误信息
    }
}
