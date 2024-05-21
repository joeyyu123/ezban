package com.ezban.productorder.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    @Query(value = "select * from product_order po where po.member_no = ?1", nativeQuery = true)
    List<ProductOrder> findByMember(Integer memberNo);

    @Query(value="select distinct po.* " +
            "from product_order po " +
            "join product_order_detail pd on po.product_order_no = pd.product_order_no " +
            "join product p on pd.product_no = p.product_no "+" where p.host_no =?1" ,
            nativeQuery = true)
    List<ProductOrder> findByHost(Integer hostNo);

}