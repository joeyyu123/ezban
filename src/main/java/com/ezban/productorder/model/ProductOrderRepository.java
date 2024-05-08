package com.ezban.productorder.model;

import com.ezban.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    @Query(value = "select * from product_order po where po.member_no = ?1", nativeQuery = true)
    List<ProductOrder> findByMember(Integer member_no);

    @Query(value="SELECT * " +
            "FROM product_order po " +
            "JOIN product_order_detail pd ON po.product_order_no = pd.product_order_no " +
            "JOIN product p ON pd.product_no = p.product_no where p.host_no =?1" ,
            nativeQuery = true)
    List<ProductOrder> findByHost(Integer hostNo);
}
