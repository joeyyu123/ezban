package com.ezban.productorder.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOrderRepository extends JpaRepository<ProductOrder,Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "update ProductOrder po set po.productPaymentStatus = 2, po.productProcessStatus = 1 where po.productOrderNo = ?1")
	void cancelByProductOrderNo(int productOrderNo);
}
