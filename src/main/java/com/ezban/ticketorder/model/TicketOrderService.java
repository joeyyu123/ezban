package com.ezban.ticketorder.model;

import com.ezban.event.model.ServiceDemo;
import com.ezban.member.model.Member;
import com.ezban.ticketorderdetail.model.TicketOrderDetail;
import com.ezban.ticketorderdetail.model.TicketOrderDetailService;
import com.ezban.ticketregistration.TicketRegistration;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeRepository;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TicketOrderService implements ServiceDemo<TicketOrder> {

    @Autowired
    private TicketTypeService ticketTypeService;

    @Autowired
    private TicketOrderDetailService ticketOrderDetailService;

    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Override
    @Transactional
    public TicketOrder add(TicketOrder vo) {
        return ticketOrderRepository.save(vo);
    }

    @Transactional
    public List<TicketOrderDetail> createOrder(Member member, List<Map<String, Integer>> orderDetailList) throws InsufficientTicketQuantityException {
        TicketOrder ticketOrder = new TicketOrder();
        ticketOrder.setMember(member);
        ticketOrder.setTicketOrderTime(new Timestamp(System.currentTimeMillis()));
        ticketOrder.setTicketOrderAmount(this.calculateTotalAmount(orderDetailList));
        ticketOrder.setTicketOrderStatus(TicketOrderStatus.PROCESSING);
        ticketOrder.setTicketOrderPaymentStatus(TicketOrderPaymentStatus.UNPAID);
        ticketOrder.setTicketCheckoutAmount(0);

        ticketOrder = this.add(ticketOrder);

        List<TicketOrderDetail> ticketOrderDetails = new ArrayList<TicketOrderDetail>();
        for (Map<String, Integer> orderDetail : orderDetailList) {
            TicketType ticketType = ticketTypeService.findById(orderDetail.get("ticketTypeNo"));

            if (ticketType.getRemainingTicketQty() < orderDetail.get("ticketTypeQty")){
                throw new InsufficientTicketQuantityException("票券已售完，請重新選擇。");
            }
            ticketType.setTicketTypeQty(ticketType.getRemainingTicketQty() - orderDetail.get("ticketTypeQty"));

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
        return ticketOrderRepository.findById(id).orElse(null);
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


}
