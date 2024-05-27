package com.ezban.productorder.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class ProductOrderEmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOrderEmail(ProductOrder productOrder) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
        // 郵件內文
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><body>");
        stringBuilder.append("<h2>親愛的會員您好： </h2>");
        stringBuilder.append("<h2>近期已收到您取消訂單的申請；通知您今日已完成退款作業。 </h2>");
        stringBuilder.append("<h2>您的訂單編號為：").append(productOrder.getProductOrderNo()).append("。</h2>");
        stringBuilder.append("<h2>總退款金額是: NT$ ").append(productOrder.getProductCheckoutAmount()).append(" 。</h2>");
        stringBuilder.append("<p>若有任何疑問，歡迎來電聯絡我們，將有專人協助您確認。</p>");
        stringBuilder.append("</body></html>");
        // 寄件人| 收件人| 主旨| 內容(html)
        helper.setFrom("ezbantest@gmail.com");
        helper.setTo(productOrder.getMember().getMemberMail());
        helper.setSubject(" ezban-退款成功通知信!");
        helper.setText(stringBuilder.toString(), true);

        mailSender.send(message);

    }

}