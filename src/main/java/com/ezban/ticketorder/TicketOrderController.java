package com.ezban.ticketorder;

import com.ezban.ecpay.service.EcpayService;
import com.ezban.member.model.Member;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.registrationform.model.RegistrationForm;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketorder.model.InsufficientTicketQuantityException;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderPaymentStatus;
import com.ezban.ticketorder.model.TicketOrderService;
import com.ezban.ticketorder.model.dto.Dto;
import com.ezban.ticketorder.model.dto.TicketOrderRegistrationForm;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.ticketregistration.TicketRegistrationService;
import com.ezban.tickettype.model.TicketTypeService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
//@RequestMapping("/events/order")
public class TicketOrderController {

    @Autowired
    private TicketOrderService ticketOrderService;


    @Autowired
    private RegistrationFormService registrationFormService;

    @Autowired
    private QrcodeTicketService qrcodeTicketService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @Autowired
    private EcpayService ecpayService;

    @PostMapping("/events/order")
    @ResponseBody
    public ResponseEntity<String> buyTicket(Model model,
                                            @RequestBody List<TicketOrderRegistrationForm> ticketOrders) throws InsufficientTicketQuantityException {
        Gson gson = new Gson();

        // 之後改成從session 取得 memberNo
        Member member = new Member();
        member.setMemberNo(1);

        List<TicketOrderDetail> ticketOrderDetails;

        // 建立一個List來存放訂單詳情
        List<Map<String, Integer>> orderDetailList = getOrderDetailList(ticketOrders);

        ticketOrderDetails = ticketOrderService.createOrder(member, orderDetailList);
        int orderNo = ticketOrderDetails.get(0).getTicketOrder().getTicketOrderNo();


        String eventNo = ticketOrders.get(0).getEventNo();
        List<TicketOrderRegistrationForm> ticketOrderRegistrationForms = registrationFormService.getTicketOrderRegistrationForms(Integer.valueOf(eventNo), orderNo);

        Dto dto = new Dto();
        dto.setEventNo(eventNo);
        dto.setTicketOrderNo(String.valueOf(orderNo));
        dto.setTicketOrderRegistrationForms(ticketOrderRegistrationForms);
        return ResponseEntity.ok(gson.toJson(dto));
    }

    @PostMapping("/events/ticketOrder/payment")
    @ResponseBody
    public String ecPayPayment(Model model, @RequestBody Map<String, String> params) {
        Integer ticketOrderNo = Integer.parseInt(params.get("ticketOrderNo"));
        String couponCode = params.get("couponCode");

        TicketOrder ticketOrder = ticketOrderService.findById(ticketOrderNo);
        if (couponCode != null && !couponCode.isEmpty()) {
            // TODO 儲存優惠券資料以及修改訂單結帳金額

//            ticketOrder.setEventCoupon(couponCode);
        } else {
            ticketOrder.setTicketCheckoutAmount(ticketOrder.getTicketOrderAmount());
        }
        ticketOrderService.update(ticketOrder);
        String aioCheckOutALLForm = ecpayService.genAioCheckOutALL(ticketOrderNo);
        // 取得回傳的Form，然後導向綠界付款頁面
//        model.addAttribute("form", aioCheckOutALLForm);
        return aioCheckOutALLForm;
    }

    @PostMapping("/events/order-result")
    public String orderResult(Model model, @RequestParam(value = "RtnCode") Integer rtnCode, @RequestParam("MerchantTradeNo") String merchantTradeNo, @RequestParam("PaymentDate") String paymentDate) {

        // 檢查付款是否成功
        if (rtnCode == 1) {
            model.addAttribute("message", "付款成功，請至我的票券查看");

            // 修改訂單狀態 以下這段在正式上線後應註解掉，改成在EcpayController中更新訂單狀態
            TicketOrder ticketOrder = ticketOrderService.findById(Integer.valueOf(merchantTradeNo.substring(14)));
            ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.PAID);
            // 將"YYYY/MM/DD HH:mm:ss" 轉換成 "YYYY-MM-DD HH:mm:ss"
            paymentDate = paymentDate.replace("/", "-");
            Timestamp ticketOrderPayTime = Timestamp.valueOf(paymentDate);
            ticketOrder.setTicketOrderPayTime(ticketOrderPayTime);

            ticketOrderService.update(ticketOrder);
            // TODO 寄送付款成功的郵件以及QRCode票券
            // =================================================================================

        } else {
            model.addAttribute("message", "付款失敗");
        }
        return "/frontstage/event/order-result";
    }

    private List<Map<String, Integer>> getOrderDetailList(List<TicketOrderRegistrationForm> ticketOrders) {
        List<Map<String, Integer>> orderDetailList = new ArrayList<>();

        // 將ticketTypeNo和ticketTypeQty組合成訂單詳情
        for (TicketOrderRegistrationForm ticketOrder : ticketOrders) {
            Map<String, Integer> orderDetail = new HashMap<>();
            orderDetail.put("ticketTypeNo", Integer.parseInt(ticketOrder.getTicketTypeNo()));
            orderDetail.put("ticketTypeQty", Integer.parseInt(ticketOrder.getTicketTypeQty()));
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }
}
