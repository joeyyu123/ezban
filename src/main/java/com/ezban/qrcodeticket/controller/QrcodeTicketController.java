package com.ezban.qrcodeticket.controller;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/backstage/qrcodeticket")
public class QrcodeTicketController {

    @Autowired
    QrcodeTicketService qrcodeTicketSvc;

    @Autowired
    MemberService memberSvc;

    @Autowired
    TicketOrderDetailService ticketOrderDetailSvc;

    @GetMapping("/select_page")
    public String selectPage() {
        return "backstage/qrcodeticket/select_page";
    }

    @GetMapping("/listAllQrcodeTicket")
    public String listAllQrcodeTicket(Model model, Principal principal) {
        Integer hostNo = Integer.parseInt(principal.getName());


        System.out.println("hostNo: " + hostNo);

        List<Map<String, Object>> qrcodeTicketsWithHostNo = qrcodeTicketSvc.findAll()
                .stream()
                .filter(ticket -> hostNo.equals(qrcodeTicketSvc.getHostNoByTicketNo(ticket.getTicketNo())))
                .map(ticket -> {
                    Map<String, Object> ticketWithHostNo = new HashMap<>();
                    ticketWithHostNo.put("ticket", ticket);
                    ticketWithHostNo.put("hostNo", hostNo);
                    return ticketWithHostNo;
                })
                .collect(Collectors.toList());

        model.addAttribute("qrcodeTicketListData", qrcodeTicketsWithHostNo);
        return "backstage/qrcodeticket/listAllQrcodeTicket";
    }

    @GetMapping("/listOneQrcodeTicket")
    public String listOneQrcodeTicket(Model model) {
        return "backstage/qrcodeticket/listOneQrcodeTicket";
    }

    @GetMapping("/update_qrcodeTicket_input")
    public String updateQrcodeTicketInput() {
        return "backstage/qrcodeticket/update_qrcodeTicket_input";
    }

    @GetMapping("/addQrcodeTicket")
    public String addQrcodeTicket(ModelMap model) {
        QrcodeTicket qrcodeTicket = new QrcodeTicket();
        model.addAttribute("qrcodeTicket", qrcodeTicket);
        return "backstage/qrcodeticket/addQrcodeTicket";
    }

    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insert(@Valid QrcodeTicket qrcodeTicket, BindingResult result) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            // 驗證失敗，返回錯誤訊息
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "輸入格式錯誤。",
                    "errors", result.getAllErrors()
            ));
        }

        try {
            /*************************** 2.開始新增資料 *****************************************/
            qrcodeTicketSvc.addQrcodeTicket(qrcodeTicket);

            /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Qrcode資料新增成功"
            ));
        } catch (Exception e) {
            /*************************** 處理新增過程中的異常 ************************************/
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "新增優惠券過程中出現錯誤",
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/getOne_For_Update")
    public String getOne_For_Update(@RequestParam("ticketNo") String ticketNo, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        QrcodeTicket qrcodeTicket = qrcodeTicketSvc.getOneQrcodeTicket(Long.valueOf(ticketNo));

        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("ticketNo", ticketNo);
        return "backstage/qrcodeticket/update_qrcodeTicket_input"; // 查詢完成後轉交update_qrcodeTicket_input.html
    }

    @PostMapping("/getOne_For_Display")
    public String getOne_For_Display(
            /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            @NotEmpty(message = "票券編號: 請勿空白")
            @Digits(integer = 8, fraction = 0, message = "票券編號: 請填數字-請勿超過{integer}位數")
            @Min(value = 3120000, message = "票券編號: 不能小於{value}")
            @Max(value = 9999999, message = "票券編號: 不能超過{value}")
            @RequestParam("ticketNo") String ticketNo,
            ModelMap model, Principal principal) {

        /***************************2.開始查詢資料*********************************************/
        QrcodeTicket qrcodeTicket = qrcodeTicketSvc.getOneQrcodeTicket(Long.parseLong(ticketNo));

        if (qrcodeTicket == null) {
            model.addAttribute("message", "查無此票券編號");
            return "backstage/qrcodeticket/getOne_For_Display";
        }

        Integer hostNo = qrcodeTicketSvc.getHostNoByTicketNo(Long.parseLong(ticketNo));

        // 確認當前登入的主辦方是否是這張票券所屬的主辦方
        if (!principal.getName().equals(hostNo.toString())) {
            model.addAttribute("message", "您無權查看此票券");
            return "backstage/qrcodeticket/getOne_For_Display";
        }

        List<QrcodeTicket> list = qrcodeTicketSvc.findAll();
        model.addAttribute("qrcodeTicketListData", list);
        model.addAttribute("ticketOrderDetailListData", new TicketOrderDetail());
        model.addAttribute("qrcodeTicket", qrcodeTicket);

        return "backstage/qrcodeticket/getOne_For_Display";
    }

    /*******************************產生QR Code*********************************************/
    @GetMapping("/generateQRCode")
    public ResponseEntity<String> generateQRCode(@RequestParam("ticketNo") Long ticketNo, Principal principal) {
        try {
            Integer hostNo = qrcodeTicketSvc.getHostNoByTicketNo(ticketNo);

            // 確認當前登入的host是否是這張票券所屬的host
            if (!principal.getName().equals(hostNo.toString())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("您沒有權限生成此票券的QR Code");
            }

            BufferedImage qrCode = qrcodeTicketSvc.generateQRCodeLogo(String.valueOf(ticketNo), 600, 600);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCode, "PNG", byteArrayOutputStream);
            byte[] imageData = byteArrayOutputStream.toByteArray();
            String base64Image = Base64.getEncoder().encodeToString(imageData);

            return ResponseEntity.ok(base64Image);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("產生QR Code錯誤: " + e.getMessage());
        }
    }

    /*******************************QR Code兌換*********************************************/
    @GetMapping("/coupon/redeem")
    public ResponseEntity<String> redeemCoupon(@RequestParam Long ticketNo, Principal principal) {
        try {
            boolean success = qrcodeTicketSvc.redeemTicket(ticketNo, principal);
            if (success) {
                return ResponseEntity.ok("編號:" + ticketNo + "的QR Code已成功兌換");
            } else {
                return ResponseEntity.badRequest().body("兌換QR Code失敗，QR Code可能已被使用或不存在");
            }
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("發生錯誤:" + e.getMessage());
        }
    }


    @ModelAttribute("memberListData")
    protected List<Member> referenceListData() {
        return memberSvc.getAllMembers();
    }

    @ModelAttribute("hostMapData") //
    protected Map<Integer, String> referenceMapData() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Host1");
        map.put(2, "Host2");
        map.put(3, "Host3");
        map.put(4, "Host4");
        map.put(5, "Host5");
        map.put(6, "Host6");
        map.put(7, "Host7");
        map.put(8, "Host8");
        map.put(9, "Host9");
        map.put(10, "Host10");
        return map;
    }

    @ModelAttribute("ticketOrderDetailListData")
    protected List<TicketOrderDetail> referenceListData2() {
        return ticketOrderDetailSvc.findAll();
    }

    @ModelAttribute("qrcodeTicketListData")
    protected List<QrcodeTicket> referenceListData(Model model) {
        return qrcodeTicketSvc.findAll();
    }
}
