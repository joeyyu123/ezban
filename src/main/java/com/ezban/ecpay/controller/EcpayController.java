package com.ezban.ecpay.controller;

import com.ezban.ecpay.service.EcpayService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class EcpayController {


    @Autowired
    private EcpayService ecpayService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    // 用來測試能不能連接到伺服器用，之後可以移除
    @GetMapping("/ecpay/return")
    @ResponseBody
    public String ecpayReturn() {
        return "OK";
    }

    /**
     * 處理ECPay票券訂單付款完成後的回傳
     * 票券訂單付款成功後，ECPay會將付款結果通知到此處 需等到正式上線後才接的到request
     * @param params 付款完成後ECpay的回傳參數
     * @return 接受到訊息後傳"1|OK"給ECPay
     */
    @PostMapping("/ecpay/return")
    @ResponseBody
    public String ecpay(@RequestParam Map<String, String> params) {
        // 取得付款結果參數
        int rtnCode = Integer.parseInt((params.get("RtnCode")));
        String merchantTradeNo = params.get("MerchantTradeNo");
        String paymentDate = params.get("PaymentDate");

        // 將"YYYY/MM/DD HH:mm:ss" 轉換成 "YYYY-MM-DD HH:mm:ss"
        paymentDate = paymentDate.replace("/", "-");


        if (rtnCode == 1) {

            TicketOrder ticketOrder = ticketOrderService.findById(Integer.valueOf(merchantTradeNo.substring(14)));
            ticketOrder = ticketOrderService.finishOrder(ticketOrder, paymentDate);

            // 儲存QRCode票券到資料庫
            ticketOrderService.createQrcodeTickets(ticketOrder);

            // TODO 寄送付款成功的郵件以及QRCode票券給購票人
//            ticketOrderEmailService.sendOrderEmail(ticketOrder);

            // =================================================================================

        }

        return "1|OK";
    }

}
