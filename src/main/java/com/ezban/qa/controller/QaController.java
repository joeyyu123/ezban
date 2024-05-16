package com.ezban.qa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.qa.model.Qa;
import com.ezban.qa.model.QaService;

@RestController
@RequestMapping("/api/faqs")
public class QaController {

    @Autowired
    private QaService qaService;

    @GetMapping
    public List<Qa> getAllFaqs() {
        return qaService.findAllQas();
    }

    @PostMapping
    public Qa createFaq(@RequestBody Qa qa) {
        return qaService.saveQa(qa);
    }

    @PutMapping("/{id}")
    public Qa updateFaq(@PathVariable Integer id, @RequestBody Qa qaDetails) {
        return qaService.updateQa(id, qaDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaq(@PathVariable Integer id) {
        qaService.deleteQa(id);
        return ResponseEntity.ok().build();
    }
}
