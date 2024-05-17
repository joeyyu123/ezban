package com.ezban.saveproduct.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaveProductRepository extends JpaRepository<SaveProduct,Integer> {

    @Query(value = "select * from save_product sp where sp.member_no = ?1", nativeQuery = true)
    List<SaveProduct> findByMember(Integer memberNo);

    @Query(value = "select * from save_product sp where sp.member_no=?1 and sp.product_no=?2", nativeQuery = true)
    Optional<SaveProduct> findSaveProductByMemberAndProduct(Integer memberNo, Integer productNo);
}
