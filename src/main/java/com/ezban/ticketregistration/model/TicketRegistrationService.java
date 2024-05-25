package com.ezban.ticketregistration.model;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketRegistrationService {
    @Autowired
    TicketRegistrationRepository registrationFormRepository;


    public void save(TicketRegistration ticketRegistration) {
        registrationFormRepository.save(ticketRegistration);
    }

    public TicketRegistration saveAll(String request) {
        Gson gson = new Gson();
        TicketRegistration ticketRegistration = gson.fromJson(request, TicketRegistration.class);
        ticketRegistration = registrationFormRepository.save(ticketRegistration);

        return ticketRegistration;
    }

    public List<TicketRegistration> findAllByEventNo(String eventNo){

        return registrationFormRepository.findAllByEventNo(eventNo);
    }

    public void deleteByTicketOrderNo(String ticketNo) {
        registrationFormRepository.deleteByTicketOrderNo(ticketNo);
    }
}


