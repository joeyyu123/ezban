package com.ezban.host.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HostRepository extends JpaRepository<Host, Integer> {
    Optional<Host> findByHostAccount(String hostAccount);  
    Optional<Host> findByHostName(String hostName);
    Optional<Host> findByHostMail(String hostMail);
    Optional<Host> findByHostPhone(String hostPhone);
    Optional<Host> findByHostAccountAndHostMail(String account, String email);

    Optional<Host> findHostByHostNo(Integer hostNo);

    @Query("SELECT h.hostName FROM Host h JOIN Event e ON h.hostNo = e.host.hostNo WHERE e.eventNo = :eventNo")
    String findHostNameByEventNo(@Param("eventNo") Integer eventNo);

    @Query("SELECT h FROM Host h JOIN Event e ON h.hostNo = e.host.hostNo WHERE e.eventNo = :eventNo")
    Optional<Host> findHostByEventNo(@Param("eventNo") Integer eventNo);
}
