package com.ezban.eventcomment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezban.member.model.Member;

@Repository
public interface EventCommentRepository extends JpaRepository<Member, Integer> {
   
}
