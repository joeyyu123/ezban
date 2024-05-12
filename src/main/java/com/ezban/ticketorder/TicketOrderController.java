package com.ezban.ticketorder;

import com.ezban.member.model.Member;
import com.ezban.qrcodeticket.model.QrcodeTicketService;
import com.ezban.registrationform.model.RegistrationForm;
import com.ezban.registrationform.model.RegistrationFormService;
import com.ezban.ticketorder.model.InsufficientTicketQuantityException;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/events/order")
public class TicketOrderController {
    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private TicketOrderService ticketOrderService;

    @Autowired
    private TicketRegistrationService ticketRegistrationService;

    @Autowired
    private RegistrationFormService registrationFormService;

    @Autowired
    private QrcodeTicketService qrcodeTicketService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;


//    @PostMapping("")
//    @ResponseBody
//    public ResponseEntity<String> buyTicket(Model model,
//                                            @RequestParam Integer eventNo,
//                                            @RequestParam List<Integer> ticketTypeNo,
//                                            @RequestParam List<Integer> ticketTypeQty) throws InsufficientTicketQuantityException {
//        Gson gson = new Gson();
//
//        // 之後改成從session 取得 memberNo
//        Member member = new Member();
//        member.setMemberNo(1);
//
//        List<TicketOrderDetail> ticketOrderDetails;
//
//        // 建立一個List來存放訂單詳情
//        List<Map<String, Integer>> orderDetailList = new ArrayList<>();
//
//        // 將ticketTypeNo和ticketTypeQty組合成訂單詳情
//        for (int i = 0; i < ticketTypeNo.size(); i++) {
//            if (ticketTypeQty.get(i) == 0) {
//                continue;
//            }
//            Map<String, Integer> orderDetail = new HashMap<>();
//            orderDetail.put("ticketTypeNo", ticketTypeNo.get(i));
//            orderDetail.put("ticketTypeQty", ticketTypeQty.get(i));
//            orderDetailList.add(orderDetail);
//        }
//
//        ticketOrderDetails = ticketOrderService.createOrder(member, orderDetailList);
//        int orderNo = ticketOrderDetails.get(0).getTicketOrder().getTicketOrderNo();
//
//        RegistrationForm registrationForm = registrationFormService.findByEventNo(String.valueOf(eventNo));
//        List<TicketOrderRegistrationForm> ticketOrderRegistrationForms = registrationFormService.getTicketOrderRegistrationForms(eventNo,orderNo);
//
//        System.out.println(ticketOrderRegistrationForms);
//        return ResponseEntity.ok(gson.toJson(ticketOrderRegistrationForms));
//    }

    @PostMapping("")
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
        List<TicketOrderRegistrationForm> ticketOrderRegistrationForms = registrationFormService.getTicketOrderRegistrationForms(Integer.valueOf(eventNo),orderNo);

        Dto dto = new Dto();
        dto.setEventNo(eventNo);
        dto.setTicketOrderNo(String.valueOf(orderNo));
        dto.setTicketOrderRegistrationForms(ticketOrderRegistrationForms);
        return ResponseEntity.ok(gson.toJson(dto));
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
