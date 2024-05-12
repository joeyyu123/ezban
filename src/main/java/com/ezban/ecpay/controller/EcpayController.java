package com.ezban.ecpay.controller;

import com.ezban.ecpay.service.EcpayService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderPaymentStatus;
import com.ezban.ticketorder.model.TicketOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Controller
public class EcpayController {


    @Autowired
    private EcpayService ecpayService;

    @Autowired
    private TicketOrderService ticketOrderService;

    // 用來測試能不能連接到伺服器用，之後可以移除
    @GetMapping("/ecpay/return")
    @ResponseBody
    public String ecpayReturn() {
        return "OK";
    }

    // 票券訂單付款成功後，ECPay會將付款結果通知到此處 需等到正式上線後才接的到request
    @PostMapping("/ecpay/return")
    @ResponseBody
    public String ecpay(
            @RequestParam Map<String, String> params) {
        int rtnCode = Integer.parseInt((params.get("RtnCode")));
        String merchantTradeNo = params.get("MerchantTradeNo");
        String paymentDate = params.get("PaymentDate");

        // 將"YYYY/MM/DD HH:mm:ss" 轉換成 "YYYY-MM-DD HH:mm:ss"
        paymentDate = paymentDate.replace("/", "-");
        Timestamp ticketOrderPayTime = Timestamp.valueOf(paymentDate);

        if (rtnCode == 1) {
            // 回傳的merchantTradeNo 格視為 yyyyMMddHHmmss+數字，數字為訂單編號
            TicketOrder ticketOrder = ticketOrderService.findById(Integer.valueOf(merchantTradeNo.substring(14)));
            ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.PAID);
            ticketOrder.setTicketOrderPayTime(ticketOrderPayTime);

            ticketOrderService.update(ticketOrder);

            // TODO 寄送包含QRCoder的email給使用者
        }

        return "1|OK";
    }

}
