package com.ezban.ticketorder.model.Service;

import com.ezban.event.model.Event;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.ticketorder.model.TicketOrder;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class TicketOrderEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private QrcodeTicketService qrcodeTicketService;

    public void sendOrderEmail(TicketOrder ticketOrder) throws MessagingException, IOException, WriterException {
        Event event = ticketOrder.getTicketOrderDetails().iterator().next().getTicketType().getEvent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);

        // 編寫郵件內容
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h1>恭喜您已成功報名 - ").append(event.getEventName()).append(" ！</h1>");
        sb.append("<h2>您的訂單編號為：").append(ticketOrder.getTicketOrderNo()).append("</h2>");
        sb.append("<p>以下是您的票券:</p>");

        ticketOrder.getTicketOrderDetails().forEach(detail -> {
            String ticketTypeName = detail.getTicketType().getTicketTypeName();
            Integer ticketTypeQty = detail.getTicketTypeQty();

            List<QrcodeTicket> qrcodeTickets = qrcodeTicketService.findByOrderDetailNo(detail.getTicketOrderDetailNo());

            sb.append("<div><label>").append(ticketTypeName).append("，共").append(ticketTypeQty).append("張").append("</label></div>");
            qrcodeTickets.forEach(qrcodeTicket -> {
                String imageCid = "ticket-" + qrcodeTicket.getTicketNo();
                sb.append("<div><img src=\"cid:").append(imageCid).append("\" /></div>");
            });
        });
        sb.append("</body></html>");

        // 設定郵件內容
        helper.setTo(ticketOrder.getMember().getMemberMail());
        helper.setSubject(event.getEventName() + " - 報名成功通知");
        helper.setText(sb.toString(), true);

        // 附加 QR Code 圖片
        ticketOrder.getTicketOrderDetails().forEach(detail -> {
            List<QrcodeTicket> qrcodeTickets = qrcodeTicketService.findByOrderDetailNo(detail.getTicketOrderDetailNo());

            qrcodeTickets.forEach(qrcodeTicket -> {
                try {
                    BufferedImage qrCodeImage = qrcodeTicketService.generateQRCodeLogo(String.valueOf(qrcodeTicket.getTicketNo()), 500, 500);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(qrCodeImage, "png", baos);
                    String imageCid = "ticket-" + qrcodeTicket.getTicketNo();
                    helper.addInline(imageCid, new ByteArrayResource(baos.toByteArray()), "image/png");
                } catch (WriterException | IOException | MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        });

        mailSender.send(message);
    }
}