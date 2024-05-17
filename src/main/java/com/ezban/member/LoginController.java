package com.ezban.member;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberAuthenticate;
import com.ezban.member.model.MemberMailService;
import com.ezban.member.model.MemberRepository;

@Controller
public class LoginController {

	@Autowired
	private MemberRepository memRepository;

	@Autowired
	private MemberMailService emailService;

	@Autowired
	private MemberAuthenticate memberAuthenticate;
	
	@Autowired
    private PasswordEncoder passwordEncoder; 

	@PostMapping("/login")
	@ResponseBody
	@Transactional  
    public ResponseEntity<String> login(@RequestBody Member LoginRequest, HttpSession session) {
        Optional<Member> optionalMember = memRepository.findByMemberMail(LoginRequest.getMemberMail());
        if (optionalMember.isPresent()) {
            Member mem = optionalMember.get();
            if (passwordEncoder.matches(LoginRequest.getMemberPwd(), mem.getMemberPwd())) {
                memRepository.save(mem);  
                return ResponseEntity.ok("登入成功！");
            }
        }
        return ResponseEntity.status(401).body("登入失敗：帳號或密碼錯誤。");
    }

	@GetMapping("/loginPage")
	public String getLogin() {
		return "frontstage/login";
	}
	
	@GetMapping("userRegisterPage")
	public String getRegister() {
		return "frontstage/register";
	}

	@PostMapping("/forgotPassword")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> forgotPassword(@RequestParam("memberMail") String memberMail) {
	    try {
	        if (!isValidEmail(memberMail)) {
	            throw new InvalidEmailException("無效的電子郵件地址");
	        }

	        Optional<Member> optionalMember = memRepository.findByMemberMail(memberMail);
	        if (!optionalMember.isPresent()) {
	            throw new MemberNotFoundException("找不到該會員");
	        }

	        Member mem = optionalMember.get();

	        String resetToken = UUID.randomUUID().toString();
	        mem.setResetToken(resetToken);
	        memRepository.save(mem);

	        String resetUrl = "http://localhost:8080/resetPassword?token=" + resetToken;

	        String emailBody = "您好，請點擊以下網址來修改您的密碼：" + resetUrl;
	        emailService.sendEmail(memberMail, "重设密码", emailBody);

	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 200);
	        responseObject.put("message", "重設密碼網址已寄至您的電子郵件信箱");
	        responseObject.put("resetUrl", resetUrl);
	        return ResponseEntity.ok(responseObject);
	    } catch (InvalidEmailException e) {
	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 400);
	        responseObject.put("message", e.getMessage());
	        return ResponseEntity.badRequest().body(responseObject);
	    } catch (MemberNotFoundException e) {
	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 404);
	        responseObject.put("message", e.getMessage());
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
	    } catch (Exception e) {
	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 500);
	        responseObject.put("message", "內部伺服器錯誤");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
	    }
	}

	private boolean isValidEmail(String memberMail) {
	    return memberMail != null && memberMail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	}

	class InvalidEmailException extends Exception {
	    InvalidEmailException(String message) {
	        super(message);
	    }
	}

	class MemberNotFoundException extends Exception {
	    MemberNotFoundException(String message) {
	        super(message);
	    }
	}

	
	@GetMapping("/forgotPassword")
	public String getForgotPassword() {
		return "frontstage/forgotPassword";
	}
	
	@PostMapping("/changePassword")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> changePassword(@RequestParam("email") String email, @RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword) {
	    // 根据用户的电子邮件查找用户
	    Optional<Member> optionalMember = memRepository.findByMemberMail(email);
	    
	    if (!optionalMember.isPresent()) {
	        // 如果找不到对应的用户，返回相应的错误信息
	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 404);
	        responseObject.put("message", "找不到该用户");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
	    }

	    // 如果找到对应的用户，验证当前密码是否正确
	    Member mem = optionalMember.get();
	    if (!passwordEncoder.matches(currentPassword, mem.getMemberPwd())) {
	        // 如果当前密码不正确，返回相应的错误信息
	        Map<String, Object> responseObject = new HashMap<>();
	        responseObject.put("code", 401);
	        responseObject.put("message", "当前密码不正确");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObject);
	    }
	    
	    // 更新用户的密码为新密码
	    mem.setMemberPwd(passwordEncoder.encode(newPassword)); // 假设使用了密码加密器passwordEncoder
	    memRepository.save(mem);
	    
	    // 返回成功信息
	    Map<String, Object> responseObject = new HashMap<>();
	    responseObject.put("code", 200);
	    responseObject.put("message", "密码修改成功");
	    return ResponseEntity.ok(responseObject);
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