package com.ezban.host.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class HostMailService {

    @Autowired
    private JavaMailSender mailSender; // 注入JavaMailSender，用於發送郵件

    /**
     * 發送郵件
     * @param to 收件人地址
     * @param subject 郵件主題
     * @param content 郵件內容，支持HTML格式
     */
    public void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage(); // 創建一個MimeMessage對象
        try {
            // 使用MimeMessageHelper來設置郵件內容
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("ezbantest@gmail.com"); // 設置發件人
            helper.setTo(to); // 設置收件人
            helper.setSubject(subject); // 設置郵件主題
            helper.setText(content, true); // 設置郵件內容，true表示支持HTML格式

            mailSender.send(message); // 發送郵件
            System.out.println("Email sent successfully to: " + to);
        } catch (MessagingException | MailException e) {
            // 捕捉並處理郵件發送過程中的異常
            System.out.println("Error sending email to " + to + ": " + e.getMessage());
        }
    }

    /**
     * 發送隨機密碼郵件
     * @param to 收件人地址
     * @param subject 郵件主題
     * @return 生成的隨機密碼
     */
    public String sendRandomPasswordEmail(String to, String subject) {
        // 生成10位的隨機密碼
        String randomPassword = HostPassRandom.generatePassword(10);
        // 發送包含隨機密碼的郵件，內容為繁體中文
        sendEmail(to, subject, "您的新密碼是：" + randomPassword);
        return randomPassword; // 返回生成的隨機密碼
    }

    /**
     * 發送驗證碼郵件
     * @param to 收件人地址
     * @return 生成的驗證碼
     */
    public String sendVerificationCodeEmail(String to) {
        // 生成6位數的驗證碼
        String verificationCode = HostPassRandom.generateVerificationCode();
        try {
            // 發送包含驗證碼的郵件，內容為繁體中文
            sendEmail(to, "驗證碼", "您的驗證碼是：" + verificationCode);
            return verificationCode; // 返回生成的驗證碼
        } catch (MailException e) {
            // 捕捉並處理郵件發送過程中的異常
            System.out.println("Error sending verification code to " + to + ": " + e.getMessage());
            return null; // 如果發送失敗，返回null
        }
    }
}
