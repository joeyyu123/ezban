package com.ezban.saveproduct.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveProductRepository extends JpaRepository<SaveProduct,Integer> {

}
