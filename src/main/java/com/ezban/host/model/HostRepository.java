package com.ezban.host.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HostRepository extends JpaRepository<Host, Integer> {
    Optional<Host> findByHostAccount(String hostAccount);  
    Optional<Host> findByHostName(String hostName);
    Optional<Host> findByHostMail(String hostMail);
    Optional<Host> findByHostPhone(String hostPhone);
    Optional<Host> findByHostAccountAndHostMail(String account, String email);
}
