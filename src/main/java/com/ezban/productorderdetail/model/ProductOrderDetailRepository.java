package com.ezban.productorderdetail.model;

import com.ezban.productorder.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductOrderDetailRepository extends JpaRepository<ProductOrderDetail,Integer> {

    @Query(value = "select * from product_order_detail pd where pd.product_order_no = ?1", nativeQuery = true)
    List<ProductOrder> findByProductOrder(Integer productOrderNo);
}
