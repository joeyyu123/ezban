package com.ezban.productcomment.model;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCommentService implements ProductCommentInterface {

    private final ProductCommentRepository commentRepository;

    @Autowired
    public ProductCommentService(ProductCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public ProductComment save(ProductComment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<ProductComment> findAll() {
        return commentRepository.findAll();
    }
}
