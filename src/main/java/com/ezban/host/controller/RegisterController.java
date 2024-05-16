package com.ezban.host.controller;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostRepository;

@RestController
public class RegisterController {

    @Autowired
    HostRepository hostRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @PostMapping("/hostregister")
    public ResponseEntity<String> registerHost(@RequestBody Host host) {
        try {
            Optional<Host> existingHost = hostRepository.findByHostMail(host.getHostMail());
            if (existingHost.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("該電子郵件已被使用");
            }

            hostRepository.save(host);

            return ResponseEntity.ok("註冊成功");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("註冊時發生內部錯誤" + e.getMessage());
        }
    }
}
