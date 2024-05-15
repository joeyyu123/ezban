package com.ezban.ticketorder;

import com.ezban.ecpay.service.EcpayService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketorder.model.InsufficientTicketQuantityException;
import com.ezban.ticketorder.model.Service.TicketOrderEmailService;
import com.ezban.ticketorder.model.Service.TicketOrderService;
import com.ezban.ticketorder.model.Service.TicketOrderStatusService;
import com.ezban.ticketorder.model.TicketOrder;
import com.ezban.ticketorder.model.TicketOrderStatus;
import com.ezban.ticketorder.model.dto.Dto;
import com.ezban.ticketorder.model.dto.TicketOrderRegistrationForm;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Controller
//@RequestMapping("/events/order")
public class TicketOrderController {
    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private RegistrationFormService registrationFormService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TicketOrderStatusService ticketOrderStatusService;

    @Autowired
    private EcpayService ecpayService;

    @Autowired
    private TicketOrderEmailService ticketOrderEmailService;

    @GetMapping("/events/orders")
    public String orderPage(Model model, @RequestParam(value = "orderStatus", required = false) Integer orderStatus, Principal principal) {
//        Member member = memberService.getMemberByMemberMail(principal.getName());

        Member member = memberService.getMemberById(1); // TODO 改成從session取得memberNo

        if (orderStatus == null) {
            model.addAttribute("ticketOrders", ticketOrderService.findByMember(member));
        } else {
            TicketOrderStatus ticketOrderStatus = TicketOrderStatus.values()[orderStatus];

            model.addAttribute("ticketOrders", ticketOrderService.findByMemberNoAndStatus(member, ticketOrderStatus));
            model.addAttribute("orderStatus", orderStatus);
        }
        return "/frontstage/event/ticket_orders";
    }

    @DeleteMapping("/events/orders/{ticketOrderNo}")
    @ResponseBody
    public ResponseEntity<String> cancelOrder(@PathVariable("ticketOrderNo") Integer ticketOrderNo) {
        Member member = memberService.getMemberById(1);// TODO 改成從CurrentMember取得memberNo


        TicketOrder ticketOrder = ticketOrderService.findById(ticketOrderNo);



        if (Objects.equals(ticketOrder.getMember().getMemberNo(), member.getMemberNo())) { // 檢查是否為訂單擁有者
            ticketOrderStatusService.cancelTicketOrder(ticketOrder);
            return ResponseEntity.ok("取消訂單成功");
        } else {
            return ResponseEntity.badRequest().body("Access denied, you are not the owner of this order");
        }
    }


    @PostMapping("/events/order")
    @ResponseBody
    public ResponseEntity<String> buyTicket(Model model, @RequestBody List<TicketOrderRegistrationForm> ticketOrders) throws InsufficientTicketQuantityException {
        Gson gson = new Gson();

        // 之後改成從session 取得 memberNo
        Member member = memberService.getMemberById(1); // TODO 改成從session取得memberNo

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

    @PostMapping("/events/order/payment")
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

    /**
     * 付款完成後回傳給使用者付款結果
     *
     * @param rtnCode         if return code is 1, payment is successful. Otherwise, payment is failed.
     * @param merchantTradeNo 訂單編號
     * @param paymentDate     付款日期時間
     * @return 付款結果頁面
     */
    @PostMapping("/events/order-result")
    public String orderResult(Model model, @RequestParam(value = "RtnCode") Integer rtnCode, @RequestParam("MerchantTradeNo") String merchantTradeNo, @RequestParam("PaymentDate") String paymentDate) {

        // 檢查付款是否成功
        if (rtnCode == 1) {
            model.addAttribute("message", "付款成功，請至我的票券查看");

            // TODO 修改訂單狀態 以下這段在正式上線後應註解掉，由EcpayController中更新訂單狀態
            TicketOrder ticketOrder = ticketOrderService.findById(Integer.valueOf(merchantTradeNo.substring(14)));
            ticketOrder = ticketOrderService.finishOrder(ticketOrder, paymentDate);

            // 儲存QRCode票券到資料庫
            ticketOrderService.createQrcodeTickets(ticketOrder);

            try {
                ticketOrderEmailService.sendOrderEmail(ticketOrder);
            } catch (MessagingException | IOException | WriterException e) {
                System.out.println(e.getMessage());
            }

            // =================================================================================

        } else {
            model.addAttribute("message", "付款失敗");
        }
        return "/frontstage/event/order_result";
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
