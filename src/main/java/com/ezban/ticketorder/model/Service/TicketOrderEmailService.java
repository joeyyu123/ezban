package com.ezban.ticketorder.model.Service;

import com.ezban.event.model.Event;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.ticketorder.model.TicketOrder;

import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.tickettype.model.TicketType;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.RedisTemplate;
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
import java.util.Set;

@Service
public class TicketOrderEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private QrcodeTicketService qrcodeTicketService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 寄送報名成功通知郵件
     */
    public void sendOrderEmail(TicketOrder ticketOrder) throws MessagingException, IOException, WriterException {
        Event event = ticketOrder.getTicketOrderDetails().iterator().next().getTicketType().getEvent();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED);

        // 設定郵件內容
        helper.setFrom("ezbantest@gmail.com");
        helper.setTo(ticketOrder.getMember().getMemberMail());
        helper.setSubject(event.getEventName() + " - 報名成功通知");
        helper.setText(generateEmailContent(ticketOrder, event), true);

        // 附加 QR Code 圖片
        attachQRCodes(ticketOrder, helper);

        mailSender.send(message);
    }

    /**
     * 寄送活動訂閱者通知郵件
     * @param ticketOrder
     * @throws MessagingException
     */
    public void sendEmailToSubscribers(TicketOrder ticketOrder) throws MessagingException {
        Set<TicketOrderDetail> ticketOrderDetails = ticketOrder.getTicketOrderDetails();

        for (TicketOrderDetail ticketOrderDetail : ticketOrderDetails) {
            TicketType ticketType = ticketOrderDetail.getTicketType();
            String redisKey = "ticket.type:" + ticketType.getTicketTypeNo() + ":subscribers:";
            Set<Object> reservedUsers = redisTemplate.opsForSet().members(redisKey);

            if (reservedUsers != null && !reservedUsers.isEmpty()) {
                notifySubscribers(reservedUsers, ticketType);
                redisTemplate.delete(redisKey); // 清空該票種的預約通知
            }
        }
    }

    /**
     * 寄送活動取消通知
     * @param ticketOrder
     * @param event
     * @return
     */
    public void sendCancelEmail(TicketOrder ticketOrder, Event event) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED);

        // 設定郵件內容
        helper.setFrom("ezbantest@gmail.com");
        helper.setTo(ticketOrder.getMember().getMemberMail());
        helper.setSubject(event.getEventName() + " - 報名取消通知");
        helper.setText("很抱歉通知您，因主辦廠商取消本次活動，故將取消您的報名並進行退款。");

        mailSender.send(message);
    }

    private String generateEmailContent(TicketOrder ticketOrder, Event event) {
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

        return sb.toString();
    }

    private void attachQRCodes(TicketOrder ticketOrder, MimeMessageHelper helper) {
        ticketOrder.getTicketOrderDetails().forEach(detail -> {
            List<QrcodeTicket> qrcodeTickets = qrcodeTicketService.findByOrderDetailNo(detail.getTicketOrderDetailNo());

            qrcodeTickets.forEach(qrcodeTicket -> {
                try {
                    BufferedImage qrCodeImage = qrcodeTicketService.generateQRCodeLogo(String.valueOf(qrcodeTicket.getTicketNo()), 500, 500); // TODO: 設定data為認證的網址
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(qrCodeImage, "png", baos);
                    String imageCid = "ticket-" + qrcodeTicket.getTicketNo();
                    helper.addInline(imageCid, new ByteArrayResource(baos.toByteArray()), "image/png");
                } catch (WriterException | IOException | MessagingException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }



    private void notifySubscribers(Set<Object> reservedUsers, TicketType ticketType) throws MessagingException {
        for (Object memberNo : reservedUsers) {
            Member member = memberService.getMemberByMemberNo(String.valueOf(memberNo));
            sendEmail(member, ticketType);
        }
    }

    private void sendEmail(Member member, TicketType ticketType) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED);

        StringBuilder sb = new StringBuilder();
        sb.append("您已訂閱的 [ ").append(ticketType.getTicketTypeName()).append(" ]票券已釋出，請前往 ")
                .append(ticketType.getEvent().getEventName()).append(" 活動頁面進行購買。");

        // 設定郵件內容
        helper.setFrom("ezbantest@gmail.com");
        helper.setTo(member.getMemberMail());
        helper.setSubject(ticketType.getEvent().getEventName() + " - 票券釋出通知");
        helper.setText(sb.toString());

        mailSender.send(message);
    }
}