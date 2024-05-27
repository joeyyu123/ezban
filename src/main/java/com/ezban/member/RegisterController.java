package com.ezban.member;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberMailService;
import com.ezban.member.model.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("memberRegisterController")
public class RegisterController {

    @Autowired
    MemberRepository memberRepository;

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
        Logger logger = LoggerFactory.getLogger(this.getClass());
        
        try {
            // 檢查是否已經存在相同電子郵件的會員
            Optional<Member> existingMember = memberRepository.findByMemberMail(member.getMemberMail());
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

            // 保存會員信息
            memberRepository.save(member);

            // 寄出驗證碼郵件
            emailService.sendRegisterVerificationEmail(member.getMemberMail(), verificationCode);

            return ResponseEntity.ok("註冊成功，請檢查您的電子郵件以驗證您的帳戶");
        } catch (DataIntegrityViolationException e) {
            // 處理資料完整性違反異常
            logger.error("資料完整性違反異常", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("註冊資料不合法：" + e.getMessage());
        } catch (MailException e) {
            // 處理郵件發送異常
            logger.error("郵件發送異常", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("寄送驗證郵件時發生錯誤：" + e.getMessage());
        } catch (Exception e) {
            // 處理其他所有異常
            logger.error("註冊過程中發生未預期的錯誤", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊時發生內部錯誤：" + e.getMessage());
        }
    }
    
    @GetMapping("/userLoginPage")
	public String getLogin() {
		return "frontstage/login";
	}
    
    @GetMapping("/registerPage")
	public String getRegister() {
		return "frontstage/register";
	}
    
    @GetMapping("/errorPage")
    public String getErrorPage() {
        return "frontstage/errorPage";
    }



    @Transactional
    @PostMapping("/verifyMember")
    @ResponseBody
    public ModelAndView verifyMember(@RequestBody Map<String, String> request) {
        String memberMail = request.get("memberMail");
        String code = request.get("code");

        try {
            Optional<Member> optionalMember = memberRepository.findByMemberMail(memberMail);
            if (!optionalMember.isPresent()) {
                return new ModelAndView("redirect:/errorPage").addObject("message", "未找到該電子郵件的用戶");
            }

            Member member = optionalMember.get();
            if (member.getVerificationCode() == null || !member.getVerificationCode().equals(code)) {
                return new ModelAndView("redirect:/errorPage").addObject("message", "驗證碼不正確");
            }

            if (member.getVerificationCodeExpiry().isBefore(LocalDateTime.now())) {
                return new ModelAndView("redirect:/errorPage").addObject("message", "驗證碼已過期");
            }

            // 驗證成功，清空驗證碼
            member.setVerificationCode(null);
            member.setVerificationCodeExpiry(null);
            memberRepository.save(member);

            // 驗證成功，重定向到登入頁面，並返回成功消息
            ModelAndView modelAndView = new ModelAndView("redirect:/loginPage");
            modelAndView.addObject("message", "驗證成功");
            return modelAndView;
        } catch (Exception e) {
            e.printStackTrace(); // 打印異常堆棧以便調試
            ModelAndView modelAndView = new ModelAndView("redirect:/errorPage");
            modelAndView.addObject("message", "驗證時發生內部錯誤：" + e.getMessage());
            return modelAndView;
        }
    }


}