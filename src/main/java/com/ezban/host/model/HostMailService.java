package com.ezban.host.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class HostMailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("ezbantest@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        try {
            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (MailException e) {
            System.out.println("Error sending email to " + to + ": " + e.getMessage());
        }
    }

    public void sendRandomPasswordEmail(String to, String subject) {
        String randomPassword = HostPassRandom.generatePassword(10); // 生成10位的隨機密碼
        sendEmail(to, subject, "Your random password is: " + randomPassword);
    }

    public String sendVerificationCodeEmail(String to) {
        String verificationCode = HostPassRandom.generateVerificationCode(); // 生成6位數的驗證碼
        try {
            sendEmail(to, "Verification Code", "Your verification code is: " + verificationCode);
            return verificationCode;
        } catch (MailException e) {
            System.out.println("Error sending verification code to " + to + ": " + e.getMessage());
            return null; // 如果發送失敗，返回 null
        }
    }
}
