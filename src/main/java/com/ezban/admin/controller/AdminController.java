package com.ezban.admin.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
	
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminRepository;
import com.ezban.admin.model.AdminService;
import com.ezban.host.model.HostMailService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private HostMailService hostMailService;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @PostMapping("/adminregister")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid Admin admin) {
        Map<String, Object> response = new HashMap<>();
        try {
            Admin registeredAdmin = adminService.registerAdmin(
                admin.getAdminAccount(),
                admin.getAdminName(),
                admin.getAdminMail(),
                admin.getAdminPhone()
            );

            response.put("success", true);
            response.put("admin", registeredAdmin);
            response.put("message", "註冊成功。初始密碼已寄送至信箱。");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getMostSpecificCause().getMessage();
            logger.error("Data integrity violation: {}", errorMessage, e);
            String message;
            switch (errorMessage) {
                case "admin_account_unique":
                    message = "帳號已有人使用";
                    break;
                case "admin_mail_unique":
                    message = "信箱已有人使用";
                    break;
                case "admin_name_unique":
                    message = "名稱已有人使用";
                    break;
                case "admin_phone_unique":
                    message = "電話已有人使用";
                    break;
                default:
                    message = "註冊失敗，請重試。";
                    break;
            }
            response.put("success", false);
            response.put("message", message);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            logger.error("Exception during admin registration: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "註冊失敗，請重試。");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/checkAccount/{account}")
    public ResponseEntity<?> checkAccount(@PathVariable String account) {
        boolean exists = adminRepository.findByAdminAccount(account).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/checkName/{name}")
    public ResponseEntity<?> checkName(@PathVariable String name) {
        boolean exists = adminRepository.findByAdminName(name).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/checkMail/{mail}")
    public ResponseEntity<?> checkMail(@PathVariable String mail) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean exists = adminRepository.findByAdminMail(mail).isPresent();
            response.put("exists", exists);
            if (!exists) {
                // Send verification code, not password
                String verificationCode = hostMailService.sendVerificationCodeEmail(mail);
                if (verificationCode != null) {
                    verificationCodes.put(mail, verificationCode);
                    response.put("verificationCode", verificationCode);
                } else {
                    response.put("error", "無法發送驗證碼。請確認信箱是否正常。");
                    return ResponseEntity.badRequest().body(response);
                }
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error during email verification", e);
            response.put("error", "伺服器錯誤，請稍後重試。");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/verifyCode/{mail}/{code}")
    public ResponseEntity<?> verifyCode(@PathVariable String mail, @PathVariable String code) {
        String savedCode = verificationCodes.get(mail);
        boolean valid = savedCode != null && savedCode.equals(code);
        verificationCodes.remove(mail);
        return ResponseEntity.ok(Map.of("valid", valid));
    }

    @GetMapping("/checkPhone/{phone}")
    public ResponseEntity<?> checkPhone(@PathVariable String phone) {
        boolean exists = adminRepository.findByAdminPhone(phone).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/setupPassword")
    public ResponseEntity<?> setupAdminPassword(@RequestParam int adminNo) {
        try {
            adminService.setupAdminPassword(adminNo);
            return ResponseEntity.ok("密碼設置成功並已發送電子郵件。");
        } catch (Exception e) {
            logger.error("Error setting up password", e);
            return ResponseEntity.badRequest().body("密碼設置失敗: " + e.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<Map<String, Object>> getCurrentAdmin(HttpSession session) {
        Integer adminId = (Integer) session.getAttribute("adminId");
        if (adminId == null) {
            return ResponseEntity.status(401).body(Map.of("error", "未登錄"));
        }

        Admin admin = adminRepository.findById(adminId)
            .orElseThrow(() -> new UsernameNotFoundException("找不到當前登錄的管理員"));

        Map<String, Object> response = new HashMap<>();
        response.put("adminId", admin.getAdminNo());
        response.put("adminAccount", admin.getAdminAccount());
        response.put("adminName", admin.getAdminName());

        return ResponseEntity.ok(response);
    }
}

