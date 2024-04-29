package com.ezban.productreport.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductReportRepository extends JpaRepository<ProductReport,Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "delete from product_report where productReportNo =?1", nativeQuery = true)
	void deleteByProductReportNo(int productReportNo);
}
