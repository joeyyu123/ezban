package com.ezban.productcomment.model;



import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommentService {

    private final ProductCommentRepository commentRepository;

    @Autowired
    public ProductCommentService(ProductCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public ProductComment save(ProductComment comment) {
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<ProductCommentDTO> findAllComments() {
        List<ProductComment> comments = commentRepository.findAll();
        comments.forEach(comment -> Hibernate.initialize(comment.getMember())); // 初始化延遲加載的Member
        return comments.stream()
                       .map(comment -> new ProductCommentDTO(
                               comment.getMember().getMemberNo(),
                               comment.getProductCommentContent(),
                               comment.getProductRate()))
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductCommentDTO> findCommentsByProductNo(Integer productNo) {
        List<ProductComment> comments = commentRepository.findByProduct_ProductNo(productNo);
        comments.forEach(comment -> Hibernate.initialize(comment.getMember())); // 初始化延遲加載的Member
        return comments.stream()
                       .map(comment -> new ProductCommentDTO(
                               comment.getMember().getMemberNo(),
                               comment.getProductCommentContent(),
                               comment.getProductRate()))
                       .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public double getAverageRating(Integer productNo) {
        List<ProductComment> comments = commentRepository.findByProduct_ProductNo(productNo);
        return comments.stream()
                       .mapToInt(ProductComment::getProductRate)
                       .average()
                       .orElse(0.0);
    }

    @Transactional(readOnly = true)
    public long getRatingCount(Integer productNo) {
        return commentRepository.countByProduct_ProductNo(productNo);
    }
}