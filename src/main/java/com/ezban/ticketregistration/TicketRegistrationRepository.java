package com.ezban.ticketregistration;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRegistrationRepository extends MongoRepository<TicketRegistration,String > {

}
