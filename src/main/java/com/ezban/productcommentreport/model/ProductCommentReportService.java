package com.ezban.productcommentreport.model;



import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommentReportService {

    private final ProductCommentReportRepository reportRepository;

    @Autowired
    public ProductCommentReportService(ProductCommentReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ProductCommentReport save(ProductCommentReport report) {
        return reportRepository.save(report);
    }
}
