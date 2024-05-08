package com.ezban.ticketorder.model;

import com.ezban.event.model.ServiceDemo;
import com.ezban.tickettype.model.TicketType;
import com.ezban.tickettype.model.TicketTypeRepository;
import com.ezban.tickettype.model.TicketTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TicketOrderService implements ServiceDemo<TicketOrder> {

    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Override
    public TicketOrder add(TicketOrder vo) {
        return ticketOrderRepository.save(vo);
    }

    @Override
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
