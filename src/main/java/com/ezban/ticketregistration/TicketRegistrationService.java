package com.ezban.ticketregistration;

import com.ezban.registrationform.model.RegistrationFormService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketRegistrationService {
    @Autowired
    TicketRegistrationRepository registrationFormRepository;

    @Autowired
    RegistrationFormService registrationFormService;


    public void save(TicketRegistration ticketRegistration) {
        registrationFormRepository.save(ticketRegistration);
    }

    public void saveAll(String request) {
        Gson gson = new Gson();
        TicketRegistration ticketRegistration = gson.fromJson(request, TicketRegistration.class);
        registrationFormRepository.save(ticketRegistration);
    }
}


