package com.ezban.qa.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QaService {

    @Autowired
    private QaRepository qaRepository;

    public List<Qa> findAllQas() {
        return qaRepository.findAll();
    }

    public Qa saveQa(Qa qa) {
        return qaRepository.save(qa);
    }

    public Qa updateQa(Integer id, Qa qaDetails) {
        Qa qa = qaRepository.findById(id).orElseThrow(() -> new RuntimeException("FAQ not found"));
        qa.setQaTitle(qaDetails.getQaTitle());
        qa.setQaContent(qaDetails.getQaContent());
        return qaRepository.save(qa);
    }

    public void deleteQa(Integer id) {
        qaRepository.deleteById(id);
    }
}
