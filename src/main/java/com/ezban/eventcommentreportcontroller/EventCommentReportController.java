package com.ezban.eventcommentreportcontroller;

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
import com.ezban.eventcomment.model.EventComment;
import com.ezban.eventcomment.model.EventCommentRepository;
import com.ezban.eventcommentreport.model.EventCommentReport;
import com.ezban.eventcommentreport.model.EventCommentReportDTO;
import com.ezban.eventcommentreport.model.EventCommentReportService;

@RestController
@RequestMapping("/api/event/comment/report")
public class EventCommentReportController {

    private final EventCommentReportService reportService;
    private final EventCommentRepository commentRepository;
    private final AdminRepository adminRepository;

    @Autowired
    public EventCommentReportController(EventCommentReportService reportService, EventCommentRepository commentRepository, AdminRepository adminRepository) {
        this.reportService = reportService;
        this.commentRepository = commentRepository;
        this.adminRepository = adminRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAllReports(Principal principal) {
        try {
            Integer adminNo = Integer.parseInt(principal.getName());
            Admin admin = adminRepository.findById(adminNo)
                    .orElseThrow(() -> new RuntimeException("Admin not found: " + adminNo));
            List<EventCommentReportDTO> reports = reportService.findAll().stream()
                    .map(EventCommentReport::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> reportComment(@RequestBody Map<String, Object> request) {
        try {
            Integer eventCommentNo = (Integer) request.get("eventCommentNo");
            Integer memberNo = (Integer) request.get("memberNo");
            String reportReason = (String) request.get("reportReason");
            Instant reportDate = Instant.parse((String) request.get("reportDate"));
            Byte reportStatus = ((Number) request.get("reportStatus")).byteValue();

            EventComment eventComment = commentRepository.findById(eventCommentNo)
                    .orElseThrow(() -> new RuntimeException("EventComment not found"));

            Member member = new Member();
            member.setMemberNo(memberNo);

            EventCommentReport report = new EventCommentReport();
            report.setEventComment(eventComment);
            report.setMember(member);
            report.setReportReason(reportReason);
            report.setReportTime(reportDate);
            report.setReportStatus(reportStatus);

            EventCommentReport savedReport = reportService.save(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReport.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<?> updateReportStatus(@PathVariable Integer reportId, @RequestBody Map<String, Object> request, Principal principal) {
        try {
            EventCommentReport report = reportService.findById(reportId)
                    .orElseThrow(() -> new RuntimeException("Report not found"));

            Integer reportStatus = (Integer) request.get("reportStatus");

            if (reportStatus != null) {
                report.setReportStatus(reportStatus.byteValue());

                if (reportStatus == 2) {
                    EventComment comment = report.getEventComment();
                    comment.setEventCommentStatus((byte) -1);
                    reportService.saveComment(comment);
                    reportService.updateAllReportsWithCommentNo(comment.getEventCommentNo(), reportStatus.byteValue());
                } else if (reportStatus == 1) {
                    EventComment comment = report.getEventComment();
                    comment.setEventCommentStatus((byte) 0);
                    reportService.saveComment(comment);
                    reportService.updateAllReportsWithCommentNo(comment.getEventCommentNo(), reportStatus.byteValue());
                }
            }

            Integer adminNo = Integer.parseInt(principal.getName());
            Admin admin = adminRepository.findById(adminNo)
                    .orElseThrow(() -> new RuntimeException("Admin not found: " + adminNo));
            report.setAdmin(admin);

            EventCommentReport updatedReport = reportService.save(report);
            return ResponseEntity.ok(updatedReport.toDTO());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error", "message", e.getMessage()));
        }
    }
}
