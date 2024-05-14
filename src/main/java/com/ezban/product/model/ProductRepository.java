package com.ezban.product.model;

import com.ezban.host.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByHost(Host host);

    List<Product> findByProductCategoryProductCategoryNo(Integer productCategoryNo);
}