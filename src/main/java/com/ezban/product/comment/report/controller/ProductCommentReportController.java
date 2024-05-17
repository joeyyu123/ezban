package com.ezban.product.comment.report.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentRepository;
import com.ezban.productcommentreport.model.ProductCommentReport;
import com.ezban.productcommentreport.model.ProductCommentReportService;

@RestController
@RequestMapping("/api/product/comment/report")
public class ProductCommentReportController {

    private final ProductCommentReportService reportService;
    private final ProductCommentRepository commentRepository;

    @Autowired
    public ProductCommentReportController(ProductCommentReportService reportService, ProductCommentRepository commentRepository) {
        this.reportService = reportService;
        this.commentRepository = commentRepository;
    }

    @PostMapping
    public ResponseEntity<ProductCommentReport> reportComment(@RequestBody Map<String, Object> request) {
        try {
            Integer productCommentNo = (Integer) request.get("productCommentNo");
            if (productCommentNo == null) {
                throw new IllegalArgumentException("productCommentNo must not be null");
            }

            ProductComment productComment = commentRepository.findById(productCommentNo)
                    .orElseThrow(() -> new RuntimeException("ProductComment not found"));

            ProductCommentReport report = new ProductCommentReport();
            report.setProductComment(productComment);

            Member member = new Member();
            member.setMemberNo((Integer) request.get("memberNo"));
            report.setMember(member);

            report.setReportReason((String) request.get("reportReason"));
            report.setReportDate(Instant.parse((String) request.get("reportDate")));
            report.setReportStatus(((Number) request.get("reportStatus")).byteValue());

            ProductCommentReport savedReport = reportService.save(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
