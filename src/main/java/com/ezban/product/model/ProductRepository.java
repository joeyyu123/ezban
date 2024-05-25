package com.ezban.product.model;

import com.ezban.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByHost(Host host);

    @Query(value = "SELECT * FROM product WHERE product_category_no=?1 AND product_status = 1 AND NOW() BETWEEN product_add_time AND product_remove_time;", nativeQuery = true)
    List<Product> findByProductCategoryProductCategoryNo(Integer productCategoryNo);

    Optional<Product> findByProductNo(Integer productNo);

    @Query(value = "SELECT * FROM product WHERE (host_no = ?1 OR product_category_no = ?2) AND product_no <> ?3 LIMIT 4", nativeQuery = true)
    List<Product> findByHostNoandProductCategoryNp(Integer hostNo, Integer productCategoryNo, Integer productNo);

    @Query(value = "SELECT * FROM product WHERE product_status = 1 AND NOW() BETWEEN product_add_time AND product_remove_time;", nativeQuery = true)
    List<Product> findLaunched();
}