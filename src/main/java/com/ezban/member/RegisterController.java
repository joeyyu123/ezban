package com.ezban.member;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberMailService;
import com.ezban.member.model.MemberRepository;

@Controller("memberRegisterController")
public class RegisterController {

    @Autowired
    MemberRepository memrepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberMailService emailService;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerMember(@RequestBody Member member) {
        try {
            Optional<Member> existingMember = memrepository.findByMemberMail(member.getMemberMail());
            if (existingMember.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("該電子郵件已被使用");
            }

            // 加密密碼
            String encodedPassword = passwordEncoder.encode(member.getMemberPwd());
            member.setMemberPwd(encodedPassword);

            // 生成驗證碼
            String verificationCode = UUID.randomUUID().toString();
            member.setVerificationCode(verificationCode);
            member.setVerificationCodeExpiry(LocalDateTime.now().plusHours(1)); // 設置驗證碼到期時間為1小時

            memrepository.save(member);

            // 寄出驗證碼
            emailService.sendRegisterVerificationEmail(member.getMemberMail(), verificationCode);

            return ResponseEntity.ok("註冊成功，請檢查您的電子郵件以驗證您的帳戶");
        } catch (Exception e) {
            e.printStackTrace(); // 打印異常堆棧以便調試
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊時發生內部錯誤：" + e.getMessage());
        }
    }
    
    @GetMapping("registerPage")
	public String getRegister() {
		return "frontstage/register";
	}

    @Transactional
    @PostMapping("/verifyMember")
    @ResponseBody
    public ResponseEntity<String> verifyMember(@RequestBody Map<String, String> request) {
        String memberMail = request.get("memberMail");
        String code = request.get("code");

        try {
            Optional<Member> optionalMember = memrepository.findByMemberMail(memberMail);
            if (!optionalMember.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("未找到該電子郵件的用戶");
            }

            Member member = optionalMember.get();
            if (member.getVerificationCode() == null || !member.getVerificationCode().equals(code)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("驗證碼不正確");
            }

            if (member.getVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("驗證碼已過期");
            }

            // 驗證成功，清空驗證碼
            member.setVerificationCode(null);
            member.setVerificationCodeExpiry(null);
            memrepository.save(member);

            return ResponseEntity.ok("驗證成功");
        } catch (Exception e) {
            e.printStackTrace(); // 打印異常堆棧以便調試
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("驗證時發生內部錯誤：" + e.getMessage());
        }
    }
}