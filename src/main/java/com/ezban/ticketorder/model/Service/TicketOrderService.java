package com.ezban.ticketorder.model.Service;

import com.ezban.event.model.Event;
import com.ezban.event.model.ServiceDemo;
import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.qrcodeticket.model.QrcodeTicket;
import com.ezban.qrcodeticket.model.QrcodeTicketRepository;
import com.ezban.ticketorder.model.*;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeRepository;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TicketOrderService implements ServiceDemo<TicketOrder> {
    @Autowired
    private HostService hostService;

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private QrcodeTicketRepository qrcodeTicketrepository;

    public  List<TicketOrder> findByMember(Member member) {
        return ticketOrderRepository.findByMember(member, Sort.by("ticketOrderTime").descending());
    }

    public  List<TicketOrder> findByMemberNoAndStatus(Member member, TicketOrderStatus status) {

        return ticketOrderRepository.findByMemberAndTicketOrderStatus(member, status, Sort.by("ticketOrderTime").descending());
    }

    @Override
    @Transactional
    public TicketOrder add(TicketOrder vo) {
        return ticketOrderRepository.save(vo);
    }

    @Transactional
    public List<TicketOrderDetail> createOrder(Member member, List<Map<String, Integer>> orderDetailList) throws InsufficientTicketQuantityException {
        TicketOrder ticketOrder = new TicketOrder();

        Integer totalOrderAmount = this.calculateTotalAmount(orderDetailList);

        ticketOrder.setMember(member);
        ticketOrder.setTicketOrderTime(new Timestamp(System.currentTimeMillis()));
        ticketOrder.setTicketOrderAmount(totalOrderAmount);
        ticketOrder.setTicketOrderStatus(TicketOrderStatus.PROCESSING);
        ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.UNPAID);
        ticketOrder.setTicketCheckoutAmount(totalOrderAmount);

        ticketOrder = this.add(ticketOrder);

        List<TicketOrderDetail> ticketOrderDetails = new ArrayList<TicketOrderDetail>();
        for (Map<String, Integer> orderDetail : orderDetailList) {
            TicketType ticketType = ticketTypeService.findById(orderDetail.get("ticketTypeNo"));

            if (ticketType.getRemainingTicketQty() < orderDetail.get("ticketTypeQty")){
                throw new InsufficientTicketQuantityException(ticketType.getTicketTypeName() + "剩餘票數不足");
            }
            ticketType.setRemainingTicketQty(ticketType.getRemainingTicketQty() - orderDetail.get("ticketTypeQty"));

            TicketOrderDetail ticketOrderDetail = new TicketOrderDetail();
            ticketOrderDetail.setTicketType(ticketType);
            ticketOrderDetail.setTicketOrder(ticketOrder);
            ticketOrderDetail.setTicketTypeQty(orderDetail.get("ticketTypeQty"));
            ticketOrderDetail.setTicketTypePrice(ticketType.getTicketTypePrice());
            ticketOrderDetail.setIncludedTicketQty(ticketType.getIncludedTickets());

            ticketOrderDetails.add(ticketOrderDetail);
        }

        ticketOrderDetails = ticketOrderDetailService.saveAll(ticketOrderDetails);

        return ticketOrderDetails;
    }

    @Override
    @Transactional
    public TicketOrder update(TicketOrder vo) {
        return ticketOrderRepository.save(vo);
    }

    @Override
    public TicketOrder findById(Integer id) {
        return ticketOrderRepository.findById(id).orElseThrow();
    }

    @Override
    public List<TicketOrder> findAll() {
        return ticketOrderRepository.findAll();
    }

    public Integer calculateTotalAmount(List<Map<String, Integer>> orderDetailList) {

        int totalAmount = 0;
        
        for (Map<String, Integer> orderDetail : orderDetailList) {
            TicketType ticketType = ticketTypeRepository.findById(orderDetail.get("ticketTypeNo")).orElse(null);
            if (ticketType == null) {
                return null;
            }
            int price = ticketType.getTicketTypePrice();
            int quantity = orderDetail.get("ticketTypeQty");
            totalAmount += price * quantity;
        }
        return totalAmount;
    }


    @Transactional
    public TicketOrder finishOrder(TicketOrder ticketOrder, String paymentDate) {
        ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.PAID);
        ticketOrder.setTicketOrderStatus(TicketOrderStatus.FINISHED);
        // 將"YYYY/MM/DD HH:mm:ss" 轉換成 "YYYY-MM-DD HH:mm:ss"
        paymentDate = paymentDate.replace("/", "-");
        Timestamp ticketOrderPayTime = Timestamp.valueOf(paymentDate);
        ticketOrder.setTicketOrderPayTime(ticketOrderPayTime);

        ticketOrder = this.update(ticketOrder);
        return ticketOrder;
    }

    /**
     * 根據 ticketOrder  建立對應的qrcodeTickets到資料庫
     */
    public List<QrcodeTicket> createQrcodeTickets(TicketOrder ticketOrder) {
        Member member = ticketOrder.getMember();
        Set<TicketOrderDetail> ticketOrderDetails = ticketOrder.getTicketOrderDetails();
        List<QrcodeTicket> qrcodeTickets = new ArrayList<>();

        // 取得活動結束時間
        Timestamp eventEndTime = ticketOrderDetails.iterator().next().getTicketType().getEvent().getEventEndTime();

        for (TicketOrderDetail ticketOrderDetail : ticketOrderDetails) {
            if (qrcodeTicketrepository.findByTicketOrderDetailTicketOrderDetailNo(ticketOrderDetail.getTicketOrderDetailNo()).isEmpty()) {
                // 每個票種有幾張， 乘上該票種為幾人套票
                int i = ticketOrderDetail.getTicketTypeQty() * ticketOrderDetail.getIncludedTicketQty();
                for (int j = 0; j < i; j++) {
                    QrcodeTicket qrcodeTicket = new QrcodeTicket();
                    qrcodeTicket.setTicketOrderDetail(ticketOrderDetail);

                    qrcodeTicket.setMember(member);
                    qrcodeTicket.setTicketUsageStatus((byte) 0);
                    qrcodeTicket.setTicketValidTime(eventEndTime);

                    qrcodeTickets.add(qrcodeTicket);
                }
                qrcodeTickets = qrcodeTicketrepository.saveAll(qrcodeTickets);
            } else {
                // 已經有qrcodeTicket了， 就不用再建立了

                qrcodeTickets.addAll(qrcodeTicketrepository.findByTicketOrderDetailTicketOrderDetailNo(ticketOrderDetail.getTicketOrderDetailNo()));

            }
        }
        return qrcodeTickets;
    }

    public List<TicketOrder> findByEvent(Event event) {

        return ticketOrderRepository.findByEventNo(event.getEventNo(),Sort.by("ticketOrderTime").descending());
    }

    public List<TicketOrder> findByEventAndStatus(Event event, TicketOrderStatus orderStatus) {
        return ticketOrderRepository.findByEventNoAndStatus(event.getEventNo(), orderStatus, Sort.by("ticketOrderTime").descending());
    }

    public List<TicketOrder> findByEventNoAndTicketOrderNo(Integer eventNo, Integer ticketOrderNo) {
        return ticketOrderRepository.findByEventNoAndTicketOrderNo(eventNo,ticketOrderNo,Sort.by("ticketOrderTime").descending());
    }

    public Page<TicketOrder> findByEvent(Event event, Pageable pageable) {
        return ticketOrderRepository.findByEventNo(event.getEventNo(), pageable);
    }

    public Page<TicketOrder> findByEventAndStatus(Event event, TicketOrderStatus orderStatus, Pageable pageable) {
        return ticketOrderRepository.findByEventNoAndStatus(event.getEventNo(), orderStatus, pageable);
    }

    public Page<TicketOrder> findByEventNoAndTicketOrderNo(Integer eventNo, Integer ticketOrderNo, Pageable pageable) {
        return ticketOrderRepository.findByEventNoAndTicketOrderNo(eventNo, ticketOrderNo, pageable);
    }

    public boolean isAuthorizedForTicketOrder(Principal principal, Integer ticketOrderNo){
        TicketOrder ticketOrder = ticketOrderRepository.findById(ticketOrderNo).orElse(null);
        if(ticketOrder == null){
            return false;
        }
        Host host = hostService.findHostByAccount(principal.getName()).orElse(null);
        if(host == null){
            return false;
        }
        return ticketOrder.getTicketOrderDetails().iterator().next().getTicketType().getEvent().getHost().getHostNo().equals(host.getHostNo());
    }


}
