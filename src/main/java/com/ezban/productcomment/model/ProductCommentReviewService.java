package com.ezban.productcomment.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.member.model.Member;

@Service
public class ProductCommentReviewService {

    @Autowired
    private ProductCommentReviewRepository productCommentRepository;

    public List<ProductComment> getCommentsByMember(Member member) {
        return productCommentRepository.findByMember(member);
    }
}