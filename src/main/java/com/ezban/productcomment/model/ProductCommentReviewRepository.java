package com.ezban.productcomment.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ezban.member.model.Member;
import com.ezban.productcomment.model.ProductComment;

@Repository
public interface ProductCommentReviewRepository extends JpaRepository<ProductComment, Integer> {
	
	List<ProductComment> findByMember(Member member);
}
