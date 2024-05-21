//package com.ezban.admin.controller;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import javax.transaction.Transactional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.view.RedirectView;
//
//import com.ezban.admin.model.Admin;
//import com.ezban.admin.model.AdminRepository;
//
//@RestController
//public class AdminLoginController {
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostMapping("/admin/login")
//    @Transactional
//    public RedirectView login(@RequestParam("username") String username, @RequestParam("password") String password) {
//        Optional<Admin> optionalAdmin = adminRepository.findByAdminAccount(username);
//        if (optionalAdmin.isPresent()) {
//            Admin admin = optionalAdmin.get();
//            // 加密邏輯
//            // if (passwordEncoder.matches(password, admin.getAdminPwd())) {
//            if (password.equals(admin.getAdminPwd())) { // 直接比較未加密的密碼
//                admin.setAdminLogin(LocalDateTime.now());
//                adminRepository.save(admin);
//                return new RedirectView("/adminmanage"); // 更新重定向到 backstage/index.html
//            }
//        }
//        return new RedirectView("/adminlogin.html?error=true"); // 登入失敗時重定向到登入頁面並顯示錯誤訊息
//    }
//}
