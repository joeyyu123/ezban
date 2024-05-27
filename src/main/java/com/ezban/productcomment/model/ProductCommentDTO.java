package com.ezban.productcomment.model;

import java.time.LocalDateTime;

public class ProductCommentDTO {

    private Integer productCommentNo;
    private Integer memberNo;
    private Integer productNo;
    private String commentContent;
    private Integer productRate;
    private Byte productCommentStatus;
    private LocalDateTime productCommentDate; // 新增的字段

    public ProductCommentDTO() {}

    // 添加新的構造函數
    public ProductCommentDTO(Integer productCommentNo, Integer memberNo, Integer productNo, String commentContent, Integer productRate, Byte productCommentStatus, LocalDateTime productCommentDate) {
        this.productCommentNo = productCommentNo;
        this.memberNo = memberNo;
        this.productNo = productNo;
        this.commentContent = commentContent;
        this.productRate = productRate;
        this.productCommentStatus = productCommentStatus;
        this.productCommentDate = productCommentDate;
    }

    // 从 ProductComment 实例初始化的构造函数
    public ProductCommentDTO(ProductComment comment) {
        this.productCommentNo = comment.getProductCommentNo();
        this.memberNo = comment.getMember().getMemberNo();
        this.productNo = comment.getProduct().getProductNo();
        this.commentContent = comment.getProductCommentContent();
        this.productRate = comment.getProductRate();
        this.productCommentStatus = comment.getProductCommentStatus();
        this.productCommentDate = comment.getProductCommentDate();
    }

    public Integer getProductCommentNo() {
        return productCommentNo;
    }

    public void setProductCommentNo(Integer productCommentNo) {
        this.productCommentNo = productCommentNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
        this.productRate = productRate;
    }

    public Byte getProductCommentStatus() {
        return productCommentStatus;
    }

    public void setProductCommentStatus(Byte productCommentStatus) {
        this.productCommentStatus = productCommentStatus;
    }

    public LocalDateTime getProductCommentDate() {
        return productCommentDate;
    }

    public void setProductCommentDate(LocalDateTime productCommentDate) {
        this.productCommentDate = productCommentDate;
    }

    public static class CommentStatsDTO {

        private double averageRating;
        private long ratingCount;

        public CommentStatsDTO() {}

        public CommentStatsDTO(double averageRating, long ratingCount) {
            this.averageRating = averageRating;
            this.ratingCount = ratingCount;
        }

        public double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(double averageRating) {
            this.averageRating = averageRating;
        }

        public long getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(long ratingCount) {
            this.ratingCount = ratingCount;
        }
    }

    // 确保 ProductAndMemberDTO 是静态的
    public static class ProductAndMemberDTO {
        private Integer productNo;
        private Integer memberNo;

        public ProductAndMemberDTO(Integer productNo, Integer memberNo) {
            this.productNo = productNo;
            this.memberNo = memberNo;
        }

        public Integer getProductNo() {
            return productNo;
        }

        public void setProductNo(Integer productNo) {
            this.productNo = productNo;
        }

        public Integer getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(Integer memberNo) {
            this.memberNo = memberNo;
        }
    }
}