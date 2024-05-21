package com.ezban.productreport.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductReportRepository extends JpaRepository<ProductReport,Integer> {
    List<ProductReport> findAll(Sort sort);

}
