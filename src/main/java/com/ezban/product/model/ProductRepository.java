package com.ezban.product.model;

import com.ezban.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByHost(Host host);

    List<Product> findByProductCategoryProductCategoryNo(Integer productCategoryNo);

    @Query(value = "SELECT * FROM product WHERE (host_no = ?1 OR product_category_no = ?2) AND product_no <> ?3 LIMIT 4", nativeQuery = true)
    List<Product> findByHostNoandProductCategoryNp(Integer hostNo, Integer productCategoryNo, Integer productNo);
}