package com.ezban.productcommentreportcontroller;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.admin.model.Admin;
import com.ezban.member.model.Member;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentRepository;
import com.ezban.productcommentreport.model.ProductCommentReport;
import com.ezban.productcommentreport.model.ProductCommentReportDTO;
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

    @GetMapping
    public ResponseEntity<List<ProductCommentReportDTO>> getAllReports() {
        try {
            List<ProductCommentReportDTO> reports = reportService.findAll().stream()
                    .map(ProductCommentReport::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<ProductCommentReportDTO> reportComment(@RequestBody Map<String, Object> request) {
        try {
            Integer productCommentNo = (Integer) request.get("productCommentNo");
            Integer memberNo = (Integer) request.get("memberNo");
            String reportReason = (String) request.get("reportReason");
            Instant reportDate = Instant.parse((String) request.get("reportDate"));
            Byte reportStatus = ((Number) request.get("reportStatus")).byteValue();

            ProductComment productComment = commentRepository.findById(productCommentNo)
                    .orElseThrow(() -> new RuntimeException("ProductComment not found"));

            Member member = new Member();
            member.setMemberNo(memberNo);

            ProductCommentReport report = new ProductCommentReport();
            report.setProductComment(productComment);
            report.setMember(member);
            report.setReportReason(reportReason);
            report.setReportDate(reportDate);
            report.setReportStatus(reportStatus);

            ProductCommentReport savedReport = reportService.save(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<ProductCommentReportDTO> updateReportStatus(@PathVariable Integer reportId, @RequestBody Map<String, Object> request) {
        try {
            ProductCommentReport report = reportService.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            Integer reportStatus = (Integer) request.get("reportStatus");
            Integer adminNo = (Integer) request.get("adminNo");

            if (reportStatus != null) {
                report.setReportStatus(reportStatus.byteValue());

                if (reportStatus == 2) {
                    ProductComment comment = report.getProductComment();
                    comment.setProductCommentStatus((byte)-1);
                    reportService.saveComment(comment);  // 假設在服務中有保存方法
                    reportService.updateAllReportsWithCommentNo(comment.getProductCommentNo(), reportStatus.byteValue());  // 更新所有報告狀態
                } else if (reportStatus == 1) {
                    ProductComment comment = report.getProductComment();
                    comment.setProductCommentStatus((byte)0);
                    reportService.saveComment(comment);  // 假設在服務中有保存方法
                    reportService.updateAllReportsWithCommentNo(comment.getProductCommentNo(), reportStatus.byteValue());  // 更新所有報告狀態
                }
            }

            if (adminNo != null) {
                Admin admin = new Admin();
                admin.setAdminNo(adminNo);
                report.setAdmin(admin);
            }

            ProductCommentReport updatedReport = reportService.save(report);
            return ResponseEntity.ok(updatedReport.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
