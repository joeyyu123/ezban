package com.ezban.registrationform.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationFormService {
    @Autowired
    RegistrationFormRepository registrationFormRepository;

    public RegistrationForm findByEventNo(String eventNo) {
        return registrationFormRepository.getByEventNo(eventNo);
    }

    public void save(RegistrationForm registrationForm) {
        registrationFormRepository.save(registrationForm);
    }
}
