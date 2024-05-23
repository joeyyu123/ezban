package com.ezban.productcomment.model;



import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.ezban.productcomment.model.ProductComment;

public interface ProductCommentRepository extends JpaRepository<ProductComment, Integer> {
    List<ProductComment> findByProduct_ProductNo(Integer productNo);
    long countByProduct_ProductNo(Integer productNo);
}