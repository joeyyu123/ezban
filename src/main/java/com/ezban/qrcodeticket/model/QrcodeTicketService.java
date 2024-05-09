package com.ezban.qrcodeticket.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("qrcodeTicketService")
public class QrcodeTicketService {

    @Autowired
    QrcodeTicketRepository qrcodeTicketrepository;


    public void addQrcodeTicket(QrcodeTicket qrcodeTicket) {
        qrcodeTicketrepository.save(qrcodeTicket);
    }

    public void updateQrcodeTicket(QrcodeTicket qrcodeTicket){
        qrcodeTicketrepository.save(qrcodeTicket);
    }

//    public void deleteByQrcodeTicket(Long ticketNo){
//        if (repository.existsById(ticketNo))
//            repository.deleteByQrcodeTicketNo(ticketNo);
//            repository.deleteById(ticketNo);
//    }

    public QrcodeTicket getOneQrcodeTicket(Long ticketNo) {
        Optional<QrcodeTicket> optional = qrcodeTicketrepository.findById(ticketNo);
        return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }

    public List<QrcodeTicket> getAll() {
        return qrcodeTicketrepository.findAll();
    }
}
