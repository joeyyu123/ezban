package com.ezban.qa.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QaService {

    @Autowired
    private QaRepository qaRepository;

    public Qa addQa(String title, String content) {
        Qa qa = new Qa(title, content);
        return qaRepository.save(qa);
    }

    public List<Qa> getAllQa() {
        return qaRepository.findAll();
    }
}
