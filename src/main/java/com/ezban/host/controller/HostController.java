package com.ezban.host.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.host.model.HostRepository;
import com.ezban.host.model.HostMailService;
import org.springframework.dao.DataIntegrityViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/host")
public class HostController {

    private static final Logger logger = LoggerFactory.getLogger(HostController.class);

    @Autowired
    private HostService hostService;

    @Autowired
    private HostRepository hostRepository;

    @Autowired
    private HostMailService hostMailService;

    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @PostMapping("/hostregister")
    public ResponseEntity<?> registerHost(@RequestBody @Valid Host host) {
        Map<String, Object> response = new HashMap<>();
        try {
            Host registeredHost = hostService.registerHost(
                host.getHostAccount(), 
                host.getHostName(), 
                host.getHostMail(), 
                host.getHostPhone()
            );

            response.put("success", true);
            response.put("host", registeredHost);
            response.put("message", "註冊成功。初始密碼已寄送至信箱。");
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getMostSpecificCause().getMessage();
            logger.error("Data integrity violation: {}", errorMessage, e);
            String message;
            switch (errorMessage) {
                case "host_account_unique":
                    message = "帳號已有人使用";
                    break;
                case "host_mail_unique":
                    message = "信箱已有人使用";
                    break;
                case "host_name_unique":
                    message = "名稱已有人使用";
                    break;
                case "host_phone_unique":
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
            logger.error("Exception during host registration: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "註冊失敗，請重試。");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/checkAccount/{account}")
    public ResponseEntity<?> checkAccount(@PathVariable String account) {
        boolean exists = hostRepository.findByHostAccount(account).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/checkName/{name}")
    public ResponseEntity<?> checkName(@PathVariable String name) {
        boolean exists = hostRepository.findByHostName(name).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @GetMapping("/checkMail/{mail}")
    public ResponseEntity<?> checkMail(@PathVariable String mail) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean exists = hostRepository.findByHostMail(mail).isPresent();
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
        boolean exists = hostRepository.findByHostPhone(phone).isPresent();
        return ResponseEntity.ok(Map.of("exists", exists));
    }

    @PostMapping("/setupPassword")
    public ResponseEntity<?> setupHostPassword(@RequestParam int hostNo) {
        try {
            hostService.setupHostPassword(hostNo);
            return ResponseEntity.ok("密碼設置成功並已發送電子郵件。");
        } catch (Exception e) {
            logger.error("Error setting up password", e);
            return ResponseEntity.badRequest().body("密碼設置失敗: " + e.getMessage());
        }
    }
}