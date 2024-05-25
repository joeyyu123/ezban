package com.ezban.qrcodeticket.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.Principal;
import java.util.*;
import java.util.List;

@Service("qrcodeTicketService")
public class QrcodeTicketService {

    @Autowired
    QrcodeTicketRepository qrcodeTicketrepository;


    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public void addQrcodeTicket(QrcodeTicket qrcodeTicket) {
        qrcodeTicketrepository.save(qrcodeTicket);
    }

    public void updateQrcodeTicket(QrcodeTicket qrcodeTicket){
        qrcodeTicketrepository.save(qrcodeTicket);
    }

//    public void deleteByQrcodeTicket(Long ticketNo){
//        if (repository.existsById(ticketNo))
//            repository.deleteByQrcodeTicketNo(ticketNo);
//            repository.deleteById(ticketNo);
//    }

    public QrcodeTicket getOneQrcodeTicket(Long ticketNo) {
        Optional<QrcodeTicket> optional = qrcodeTicketrepository.findById(ticketNo);
        return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<QrcodeTicket> findAll() {
        return qrcodeTicketrepository.findAll();
    }

    /*******************************用在QR Code插入Logo*************************************/
    public BufferedImage generateQRCodeLogo(String ticketNo, int width, int height) throws WriterException, IOException {
        System.out.println("QR Code 內容: " + ticketNo);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String fullUrl = baseUrl + "/backstage/qrcodeticket/coupon/redeem?ticketNo=" + ticketNo;

        //設定QR Code編碼格式
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        //產生QR Code
        BitMatrix bitMatrix = qrCodeWriter.encode(fullUrl, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        //Logo路徑
        BufferedImage logo = ImageIO.read(applicationContext.getResource("classpath:/static/images/ezban.png").getInputStream());
        return insertLogo(qrImage, logo);
    }

    /*******************************insertLogo*********************************************/
    private BufferedImage insertLogo(BufferedImage qrImage, BufferedImage logoImage) {
        int qrWidth = qrImage.getWidth();
        int qrHeight = qrImage.getHeight();
        int logoWidth = logoImage.getWidth();
        int logoHeight = logoImage.getHeight();


        double logoMaxWidth = qrWidth * 0.3;
        double logoMaxHeight = qrHeight * 0.3;

        double scaleFactor = Math.min(logoMaxWidth / logoWidth, logoMaxHeight / logoHeight);

        int scaledWidth = (int) (logoWidth * scaleFactor);
        int scaledHeight = (int) (logoHeight * scaleFactor);

        int x = (qrWidth - scaledWidth) / 2;
        int y = (qrHeight - scaledHeight) / 2;

        Graphics2D g = qrImage.createGraphics();
        g.drawImage(logoImage, x, y, scaledWidth, scaledHeight, null);
        g.dispose();

        return qrImage;
    }

//    public String generateQRCodeBase64(String data) throws WriterException, IOException {
//        BufferedImage qrCode = generateQRCodeLogo(data, 600, 600);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(qrCode, "PNG", baos);
//        byte[] imageBytes = baos.toByteArray();
//        return Base64.getEncoder().encodeToString(imageBytes);
//    }

    /*******************************判斷QR Code狀態*****************************************/
    public boolean redeemTicket(Long ticketNo, Principal principal) {
        Optional<QrcodeTicket> optional = qrcodeTicketrepository.findById(ticketNo);
        if (optional.isPresent()) {
            QrcodeTicket qrcodeTicket = optional.get();
            Integer hostNo = getHostNoByTicketNo(ticketNo);

            // 確認當前登入的主辦方是否是這張票券所屬的主辦方
            if (!principal.getName().equals(hostNo.toString())) {
                throw new SecurityException("您沒有權限兌換此票券");
            }

            if (qrcodeTicket.getTicketUsageStatus() == QrcodeTicket.TicketUsageStatus.UNUSED) {
                qrcodeTicket.setTicketUsageStatus(QrcodeTicket.TicketUsageStatus.USED);
                qrcodeTicketrepository.save(qrcodeTicket);
                return true;
            } else if (qrcodeTicket.getTicketUsageStatus() == QrcodeTicket.TicketUsageStatus.EXPIRED) {
                return false;
            }
        }
        return false;
    }



    public List<QrcodeTicket> findByOrderDetailNo(Integer ticketOrderDetailNo) {

        return qrcodeTicketrepository.findByTicketOrderDetailTicketOrderDetailNo(ticketOrderDetailNo);
    }

    public Integer getHostNoByTicketNo(long ticketNo) {
        System.out.println("Querying hostNo for ticketNo " + ticketNo);
        Integer hostNo = qrcodeTicketrepository.findHostNoByTicketNo(ticketNo);
        System.out.println("Found hostNo: " + hostNo);
        return hostNo;
    }

    // 新增檢查票券是否屬於指定會員的方法
    public boolean isTicketBelongToMember(Integer ticketOrderDetailNo, Integer memberNo) {
        List<QrcodeTicket> tickets = qrcodeTicketrepository.findByTicketOrderDetailTicketOrderDetailNo(ticketOrderDetailNo);
        return tickets.stream().anyMatch(ticket -> ticket.getMember().getMemberNo().equals(memberNo));
    }

    // 使用JwtUtil產生JWT Token
    public String generateJwtToken(Long ticketOrderDetailNo) {
        return jwtUtil.generateToken(ticketOrderDetailNo);
    }

    // 使用JwtUtil驗證JWT Token
    public boolean validateJwtToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public Long getTicketOrderDetailNoFromJwt(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    // 根據會員編號查找票券的方法
    public List<QrcodeTicket> findTicketsByMemberNo(Integer memberNo) {
        return qrcodeTicketrepository.findTicketsByMemberNo(memberNo);
    }

    public Long getTicketNoByOrderDetailNo(Integer ticketOrderDetailNo) {
        QrcodeTicket ticket = qrcodeTicketrepository.findByTicketOrderDetailNo(ticketOrderDetailNo);
        return ticket != null ? ticket.getTicketNo() : null;
    }


    public Integer countByEventNo(Integer eventNo){
        return qrcodeTicketrepository.countByEventNo(eventNo);
    }

    public Integer countByEventNoAndTicketUsageStatus(Integer eventNo, Byte ticketUsageStatus){
        return qrcodeTicketrepository.countByEventNoAndTicketUsageStatus(eventNo, ticketUsageStatus);
    }
}
