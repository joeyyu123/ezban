package com.ezban.member.model;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MemberMailService {

	@Autowired
	private JavaMailSender mailsender;

	public void sendEmail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("ezbantest@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		try {
			mailsender.send(message);
			System.out.println("Email sent successfully to:" + to);
		} catch (MailException e) {
			System.out.println("Error sending email to " + to + ": " + e.getMessage());
		}
	}

	public String sendVerificationCodeEmail(String to) {
		String verificationCode = MemberPassRandom.generateVerificationCode();
		try {
			sendEmail(to, "Verification Code", "Your verification code is: " + verificationCode);
			System.out.println("Sent verification code to: " + to);
			return verificationCode;
		} catch (MailException e) {
			System.out.println("Error sending verification code to " + to + ": " + e.getMessage());
			return null; // 如果發送失敗，返回 null
		}
	}
}



