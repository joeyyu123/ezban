package com.ezban.saveevent.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SaveEventRepository extends JpaRepository<SaveEvent,Integer> {

    @Transactional
    @Modifying
    @Query(value = "delete from save_event where save_event_no =?1", nativeQuery = true)
    void deleteBySaveEventNo(int saveEventNo);
}
