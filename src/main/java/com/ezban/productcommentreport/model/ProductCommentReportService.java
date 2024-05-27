package com.ezban.productcommentreport.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentRepository;

@Service
public class ProductCommentReportService {

    private final ProductCommentReportRepository reportRepository;
    private final ProductCommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public ProductCommentReportService(ProductCommentReportRepository reportRepository, ProductCommentRepository commentRepository, MemberRepository memberRepository) {
        this.reportRepository = reportRepository;
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
    }

    public ProductCommentReport save(ProductCommentReport report) {
        Member member = report.getMember();
        if (member.getMemberNo() == null) {
            member = memberRepository.save(member);
        }
        report.setMember(member);
        return reportRepository.save(report);
    }

    @Transactional(readOnly = true)
    public List<ProductCommentReport> findAll() {
        return reportRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ProductCommentReport> findById(Integer reportId) {
        return reportRepository.findById(reportId);
    }

    @Transactional
    public void saveComment(ProductComment comment) {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateAllReportsWithCommentNo(Integer commentNo, Byte reportStatus) {
        List<ProductCommentReport> reports = reportRepository.findByProductComment_ProductCommentNo(commentNo);
        reports.forEach(report -> report.setReportStatus(reportStatus));
        reportRepository.saveAll(reports);
    }
}
