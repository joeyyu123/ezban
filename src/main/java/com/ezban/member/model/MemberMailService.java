package com.ezban.member.model;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MemberMailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String to, String subject, String content) {
	    MimeMessage mimeMessage = mailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	        helper.setFrom(new InternetAddress("ezbantest@gmail.com"));
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, false); // 如果内容是 HTML，则设置为 'true'，如果是纯文本，则设置为 false

	        mailSender.send(mimeMessage);
	        System.out.println("成功發送電子郵件至：" + to);
	    } catch (MessagingException e) {
	        System.out.println("發送電子郵件至 " + to + " 時出錯：" + e.getMessage());
	    }
	}

	public String sendForgotPasswordVerificationCodeEmail(String to) {
        String verificationCode = MemberPassRandom.generateVerificationCode();
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(new InternetAddress("ezbantest@gmail.com"));
            helper.setTo(to);
            helper.setSubject("Verification Code");
            helper.setText("Your verification code is: " + verificationCode);

            mailSender.send(mimeMessage);
            System.out.println("Sent verification code to: " + to);
            return verificationCode;
        } catch (MessagingException e) {
            System.out.println("Error sending verification code to " + to + ": " + e.getMessage());
            return null;
        }
    }

    public void sendRegisterVerificationEmail(String to, String code) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(new InternetAddress("ezbantest@gmail.com"));
            helper.setTo(to);
            helper.setSubject("您的驗證碼");
            helper.setText("您的驗證碼是：" + code);

            mailSender.send(mimeMessage);
            System.out.println("成功發送註冊驗證郵件至：" + to);
        } catch (MessagingException e) {
            System.out.println("發送註冊驗證郵件至 " + to + " 時出錯：" + e.getMessage());
        }
    }
}
