package com.ezban.saveproduct.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SaveProductRepository extends JpaRepository<SaveProduct,Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from save_product where saveProductNo =?1", nativeQuery = true)
	void deleteBySaveProductNo(int saveProductNo);
}
