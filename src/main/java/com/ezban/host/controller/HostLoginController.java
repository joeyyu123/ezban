package com.ezban.host.controller;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class HostLoginController {

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/host/login")
    @Transactional
    public RedirectView login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Optional<Host> optionalHost = hostRepository.findByHostAccount(username);
        if (optionalHost.isPresent()) {
            Host host = optionalHost.get();
           //加密邏輯 if (passwordEncoder.matches(password, host.getHostPwd())) {
            if (password.equals(host.getHostPwd())) { // 直接比較未加密的密碼
            	host.sethostlogin(LocalDateTime.now());
                hostRepository.save(host);
                return new RedirectView("/backstage"); // 更新重定向到 backstage/index.html
            }
        }
        return new RedirectView("/hostlogin.html?error=true"); // 登入失敗時重定向到登入頁面並顯示錯誤訊息
    }
}
