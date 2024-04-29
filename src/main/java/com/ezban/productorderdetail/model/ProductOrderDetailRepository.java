package com.ezban.productorderdetail.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail,Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from product_order_detail where productOrderDetailNo =?1", nativeQuery = true)
	void deleteByProductOrderDetailNo(int productOrderDetailNo);
}
