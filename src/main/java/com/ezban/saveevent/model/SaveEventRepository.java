package com.ezban.saveevent.model;

import com.ezban.saveproduct.model.SaveProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface SaveEventRepository extends JpaRepository<SaveEvent,Integer> {

    @Transactional
    @Modifying
    @Query(value = "select * from save_event se where se.member_no =?1", nativeQuery = true)
    List<SaveEvent> findByMember(Integer memberNo);

    @Query(value = "select * from save_event sp where sp.member_no=?1 and sp.event_no=?2", nativeQuery = true)
    Optional<SaveEvent> findSaveEventByMemberAndEvent(Integer memberNo, Integer eventNo);

}