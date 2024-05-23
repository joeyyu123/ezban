package com.ezban.eventcomment.model;

import java.util.List;

public interface CommentInterface {
	EventComment save(EventComment comment);
	List<EventCommentDTO> findAllComments();

}
