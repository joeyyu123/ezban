package com.ezban.productimg.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg, Integer> {

    // 一次刪除一張圖片
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_img WHERE product_img_no = ?1", nativeQuery = true)
    void deleteByImgNo(Integer productImgNo);

    // 一次刪除多張圖片
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM product_img WHERE product_img_no IN :productImgNos", nativeQuery = true)
    void deleteByImgNos(List<Integer> productImgNos);

    // 取每個商品的所有圖片
    List<ProductImg> findByProductProductNo(Integer productNo);

    // 取每個商品的首圖
    ProductImg findFirstByProductProductNo(Integer productNo);

}