package com.ezban.ecpay.service;

import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderService;
import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class EcpayService {

    @Autowired
    TicketOrderService ticketOrderService;

    public String genAioCheckOutALL(Integer ticketOrderNo) {
        TicketOrder ticketOrder = ticketOrderService.findById(ticketOrderNo);
        // 使用 LocalDateTime 來處理日期和時間
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime dateTime = LocalDateTime.ofInstant(ticketOrder.getTicketOrderTime().toInstant(), ZoneId.systemDefault());
        String merchantTradeDate = dateTime.format(formatter1);
        String test = dateTime.format(formatter2);

        AllInOne all = new AllInOne("");
        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(test + ticketOrder.getTicketOrderNo());
        obj.setMerchantTradeDate(merchantTradeDate);
        obj.setTotalAmount(String.valueOf(ticketOrder.getTicketCheckoutAmount()));
        obj.setTradeDesc("test Description");
        obj.setItemName("票券");
        obj.setReturnURL("http://homeyu.asuscomm.com:443/ecpay/return");
        obj.setOrderResultURL("http://localhost:8080/events/order-result");
        obj.setNeedExtraPaidInfo("N");
        String form = all.aioCheckOut(obj, null);
        return form;
    }
}
