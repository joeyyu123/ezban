package com.ezban.productreport.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductReportRepository extends JpaRepository<ProductReport,Integer> {
    List<ProductReport> findAll(Sort sort);

}
