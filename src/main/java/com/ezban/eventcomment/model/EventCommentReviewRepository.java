package com.ezban.eventcomment.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezban.member.model.Member;

@Repository
public interface EventCommentReviewRepository extends JpaRepository<EventComment, Integer> {
	
	List<EventComment> findByMember(Member member);
   
}
