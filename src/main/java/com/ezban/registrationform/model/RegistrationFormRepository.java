package com.ezban.registrationform.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistrationFormRepository extends MongoRepository<RegistrationForm,String> {
    RegistrationForm getByEventNo(String eventNo);
}
