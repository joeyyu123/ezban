package com.ezban.ticketregistration.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRegistrationRepository extends MongoRepository<TicketRegistration,String > {

    List<TicketRegistration> findAllByEventNo(String eventNo);

    void deleteByTicketOrderNo(String ticketOrderNo);

}
