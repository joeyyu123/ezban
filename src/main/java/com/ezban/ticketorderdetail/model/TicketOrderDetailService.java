package com.ezban.ticketorderdetail.model;

import com.ezban.event.model.ServiceDemo;
import com.ezban.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderDetailService implements ServiceDemo<TicketOrderDetail> {

    @Autowired
    private TicketOrderDetailRepository repository;

    @Override
    public TicketOrderDetail add(TicketOrderDetail vo) {
        return repository.save(vo);
    }

    @Override
    public TicketOrderDetail update(TicketOrderDetail vo) {
        return repository.save(vo);
    }

    @Override
    public TicketOrderDetail findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<TicketOrderDetail> findAll() {
        return repository.findAll();
    }

    public List<TicketOrderDetail> saveAll(Iterable<TicketOrderDetail> ticketOrderDetails) {
        return repository.saveAll(ticketOrderDetails);
    }

    public List<TicketOrderDetail> findByOrderNo(Integer orderNo){
        return repository.findByTicketOrderTicketOrderNo(orderNo);
    }

    public TicketOrderDetailDto convertTicketOrderDetailToDto(TicketOrderDetail ticketOrderDetail){
        TicketOrderDetailDto dto = new TicketOrderDetailDto();
        dto.setTicketOrderNo(ticketOrderDetail.getTicketOrder().getTicketOrderNo());
        dto.setTicketTypeName(ticketOrderDetail.getTicketType().getTicketTypeName());
        dto.setTicketTypePrice(ticketOrderDetail.getTicketTypePrice());
        dto.setTicketTypeQty(ticketOrderDetail.getTicketTypeQty());

        dto.setMemberName(ticketOrderDetail.getTicketOrder().getMember().getMemberName());
        dto.setMemberEmail(ticketOrderDetail.getTicketOrder().getMember().getMemberMail());
        dto.setMemberPhone(ticketOrderDetail.getTicketOrder().getMember().getMemberPhone());
        return dto;
    }

    public List<TicketOrderDetailDto> convertTicketOrderDetailsToDtos(List<TicketOrderDetail> ticketOrderDetails){
        return ticketOrderDetails.stream().map(this::convertTicketOrderDetailToDto).toList();
    }
}
