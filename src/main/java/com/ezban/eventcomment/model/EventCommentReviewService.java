package com.ezban.eventcomment.model;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.member.model.Member;

@Service
public class EventCommentReviewService {

	 @Autowired
	    private EventCommentReviewRepository eventCommentRepository;

	    public List<EventComment> getCommentsByMember(Member member) {
	        return eventCommentRepository.findByMember(member);
	    }
}
