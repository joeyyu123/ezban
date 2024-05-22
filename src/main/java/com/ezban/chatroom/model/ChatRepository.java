//package com.ezban.chatroom.model;
//
//import com.ezban.event.model.Event;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface ChatRepository extends JpaRepository<Event, Integer> {
//
//    @Query("SELECT h.hostName FROM Event e JOIN e.host h WHERE e.eventNo = :eventNo")
//    String findHostNameByEventNo(@Param("eventNo") int eventNo);
//
//}