package com.ezban.productcommentreport.model;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCommentReportRepository extends JpaRepository<ProductCommentReport, Integer> {
	  List<ProductCommentReport> findByProductComment_ProductCommentNo(Integer productCommentNo);
}
