package com.ezban.qrcodeticket.controller;

import com.ezban.qrcodeticket.model.JwtUtil;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.Principal;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/frontstage/qrcodeticket")
public class MemberQrcodeTicketController {

    @Autowired
    private QrcodeTicketService qrcodeTicketSvc;

    @Autowired
    JwtUtil jwtUtil;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    @GetMapping("/showQrcodePage")
    public String showQrcodePage(Model model) {
        return "frontstage/qrcodeticket/memberqrcodeticket";
    }

    @GetMapping("/getMemberTickets")
    @ResponseBody
    public ResponseEntity<List<Integer>> getMemberTickets(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());
        List<QrcodeTicket> tickets = qrcodeTicketSvc.findTicketsByMemberNo(memberNo);
        List<Integer> ticketOrderDetailNos = tickets.stream()
                .map(ticket -> ticket.getTicketOrderDetail().getTicketOrderDetailNo())
                .collect(Collectors.toList());
        return ResponseEntity.ok(ticketOrderDetailNos);
    }


    @GetMapping("/generateQRCode")
    @ResponseBody
    public ResponseEntity<String> generateQRCode(@RequestParam Integer ticketOrderDetailNo, Principal principal) {
        System.out.println("Received ticketOrderDetailNo: " + ticketOrderDetailNo);
        Integer memberNo = Integer.parseInt(principal.getName());
        boolean isTicketBelongToMember = qrcodeTicketSvc.isTicketBelongToMember(ticketOrderDetailNo, memberNo);
        if (!isTicketBelongToMember) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("您沒有權限生成此票券的QR Code");
        }

        String jwtToken = qrcodeTicketSvc.generateJwtToken(Long.valueOf(ticketOrderDetailNo));
        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        String fullUrl = baseUrl + "/backstage/qrcodeticket/coupon/redeem?token=" + jwtToken;

        try {
            BufferedImage qrCode = qrcodeTicketSvc.generateQRCodeLogo(fullUrl, 250, 250);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrCode, "PNG", baos);
            byte[] imageBytes = baos.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return ResponseEntity.ok(base64Image);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("生成QR Code失敗");
        }
    }
}
