package com.ezban.chatroom.model;

import com.ezban.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostChatRepository extends JpaRepository<Host, Integer> {

    Optional<Host> findByHostNo(Integer hostNo);
}
