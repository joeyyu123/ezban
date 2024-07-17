package com.ezban.member;

import com.ezban.member.model.CaptchaService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberAuthenticate;
import com.ezban.member.model.MemberMailService;
import com.ezban.member.model.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

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
	
	@Autowired
    private CaptchaService captchaService;

	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<String> login(
	        @RequestParam Map<String, String> loginRequest,
	        @RequestParam("recaptchaResponse") String recaptchaResponse,
	        HttpSession session) {
	    try {
	        if (!captchaService.verifyCaptcha(recaptchaResponse)) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("驗證無效碼或已過期");
	        }

	        String memberMail = loginRequest.get("memberMail");
	        String memberPwd = loginRequest.get("memberPwd");

	        Optional<Member> optionalMember = memRepository.findByMemberMail(memberMail);
	        if (optionalMember.isPresent()) {
	            Member mem = optionalMember.get();
	            if (passwordEncoder.matches(memberPwd, mem.getMemberPwd())) {
	                session.setAttribute("loggedInUser", mem);
	                return ResponseEntity.ok("登入成功！");
	            }
	        }
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗：帳號或密碼錯誤。");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("伺服器錯誤，請稍後再試。");
	    }
	}

	
	@PostMapping("/googleLogin")
    @ResponseBody
    public ResponseEntity<String> googleLogin(@RequestBody GoogleLoginRequest googleLoginRequest, HttpSession session) {
        // 这里可以进行 Google ID token 的验证和用户处理
        Optional<Member> optionalMember = verifyGoogleToken(googleLoginRequest.getToken());
        if (optionalMember.isPresent()) {
            Member mem = optionalMember.get();
            session.setAttribute("loggedInUser", mem);
            return ResponseEntity.ok("Google 登入成功！");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Google 登入失敗");
    }
	
	private Optional<Member> verifyGoogleToken(String token) {
	    try {
	        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
	                GoogleNetHttpTransport.newTrustedTransport(),
	                JacksonFactory.getDefaultInstance()
	        ).setAudience(Collections.singletonList("565507201095-j4t6d076snim2vggb855s8ld2ks7ts1d.apps.googleusercontent.com")).build();

	        GoogleIdToken idToken = verifier.verify(token);
	        if (idToken != null) {
	            GoogleIdToken.Payload payload = idToken.getPayload();
	            String email = payload.getEmail();
	            // 根据 email 从数据库中查找用户
	            return memRepository.findByMemberMail(email);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return Optional.empty();
	}


	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/loginPage";
	}

	@RequestMapping(value="/loginPage",method = {RequestMethod.GET,RequestMethod.POST})
	public String getLogin(Model model, @RequestParam(value = "error", required = false) String error) {
		if (error != null) {
			model.addAttribute("error", "帳號或密碼錯誤");
		}
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

			String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
			String resetUrl = baseUrl + "/changePassword?resetToken=" + resetToken;

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
	public ResponseEntity<Map<String, Object>> changePassword(@RequestParam("memberMail") String memberMail,
			@RequestParam("currentPassword") String currentPassword, @RequestParam("newPassword") String newPassword,
			@RequestParam("resetToken") String resetToken) {

		System.out.println("Received request to change password for: " + memberMail);

		Optional<Member> optionalMember = memRepository.findByMemberMail(memberMail);

		if (!optionalMember.isPresent()) {
			// 如果找不到对应的用户，返回相应的错误信息
			System.out.println("User not found for email: " + memberMail);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("code", 404);
			responseObject.put("message", "找不到该用户");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseObject);
		}

		// 如果找到对应的用户
		Member mem = optionalMember.get();

		// 验证token是否匹配
		System.out.println("Received resetToken: " + resetToken);
		System.out.println("Stored resetToken: " + mem.getResetToken());
		if (!resetToken.equals(mem.getResetToken())) {
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("code", 400);
			responseObject.put("message", "連結已失效");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
		}

		// 验证当前密码是否正确
		if (!passwordEncoder.matches(currentPassword, mem.getMemberPwd())) {
			// 如果当前密码不正确，返回相应的错误信息
			System.out.println("Incorrect current password for email: " + memberMail);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("code", 401);
			responseObject.put("message", "当前密码不正确");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObject);
		}

		// 更新用户的密码为新密码
		mem.setMemberPwd(passwordEncoder.encode(newPassword));
		mem.setResetToken(null); // 使链接失效
		memRepository.save(mem);

		// 重新从数据库中读取用户对象以确认更新
		Member updatedMember = memRepository.findById(mem.getMemberNo()).orElse(null);
		if (updatedMember != null && updatedMember.getResetToken() == null) {
			System.out.println("Password changed successfully for email: " + memberMail);
		} else {
			System.out.println("Failed to update resetToken for email: " + memberMail);
			Map<String, Object> responseObject = new HashMap<>();
			responseObject.put("code", 500);
			responseObject.put("message", "更新密碼时发生错误，请重试。");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseObject);
		}

		// 返回成功信息
		Map<String, Object> responseObject = new HashMap<>();
		responseObject.put("message", "密碼修改成功,請以新密碼重新登入");
		return ResponseEntity.ok(responseObject);
	}

	@GetMapping("/changePassword")
	public String getChangePassword() {
		return "frontstage/changePassword";
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
	
	public static class GoogleLoginRequest {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}