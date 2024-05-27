package com.ezban.productcommentreportcontroller;

import java.security.Principal;
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
import com.ezban.admin.model.AdminRepository;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
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
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ProductCommentReportController(ProductCommentReportService reportService, ProductCommentRepository commentRepository, AdminRepository adminRepository, MemberRepository memberRepository) {
        this.reportService = reportService;
        this.commentRepository = commentRepository;
        this.adminRepository = adminRepository;
        this.memberRepository = memberRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllReports(Principal principal) {
        try {
            Integer adminNo = Integer.parseInt(principal.getName());
            Admin admin = adminRepository.findById(adminNo)
                    .orElseThrow(() -> new RuntimeException("Admin not found: " + adminNo));
            List<ProductCommentReportDTO> reports = reportService.findAll().stream()
                    .map(ProductCommentReport::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> reportComment(@RequestBody Map<String, Object> request) {
        try {
            // 確保從請求中正確獲取數據
            Integer productCommentNo = (Integer) request.get("productCommentNo");
            Integer memberNo = (Integer) request.get("memberNo");
            String reportReason = (String) request.get("reportReason");
            Instant reportDate = Instant.parse((String) request.get("reportDate"));
            Byte reportStatus = ((Number) request.get("reportStatus")).byteValue();

            // 檢查 ID 是否為 null
            if (productCommentNo == null || memberNo == null) {
                throw new IllegalArgumentException("ProductCommentNo and MemberNo must not be null");
            }

            // 確保從數據庫中查找 ProductComment
            ProductComment productComment = commentRepository.findById(productCommentNo)
                    .orElseThrow(() -> new RuntimeException("ProductComment not found"));

            // 確保從數據庫中查找 Member
            Member member = memberRepository.findById(memberNo)
                    .orElseThrow(() -> new RuntimeException("Member not found"));

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }


    @PutMapping("/{reportId}")
    public ResponseEntity<?> updateReportStatus(@PathVariable Integer reportId, @RequestBody Map<String, Object> request, Principal principal) {
        try {
            ProductCommentReport report = reportService.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            Integer reportStatus = (Integer) request.get("reportStatus");

            if (reportStatus != null) {
                report.setReportStatus(reportStatus.byteValue());

                ProductComment comment = report.getProductComment();
                if (reportStatus == 2) {
                    comment.setProductCommentStatus((byte) -1);
                } else if (reportStatus == 1) {
                    comment.setProductCommentStatus((byte) 0);
                }
                reportService.saveComment(comment);
                reportService.updateAllReportsWithCommentNo(comment.getProductCommentNo(), reportStatus.byteValue());
            }

            Integer adminNo = Integer.parseInt(principal.getName());
            Admin admin = adminRepository.findById(adminNo)
                    .orElseThrow(() -> new RuntimeException("Admin not found: " + adminNo));
            report.setAdmin(admin);

            ProductCommentReport updatedReport = reportService.save(report);
            return ResponseEntity.ok(updatedReport.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }
}
