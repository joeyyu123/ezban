package com.ezban.productcomment.model;

public class ProductCommentDTO {
    private Integer productCommentNo;
    private Integer memberNo;
    private String commentContent;
    private Integer productRate;
    private Integer productCommentStatus;

    public ProductCommentDTO(Integer productCommentNo, Integer memberNo, String commentContent, Integer productRate, Integer productCommentStatus) {
        this.productCommentNo = productCommentNo;
        this.memberNo = memberNo;
        this.commentContent = commentContent;
        this.productRate = productRate;
        this.productCommentStatus = productCommentStatus;
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

    public Integer getProductCommentStatus() {
        return productCommentStatus;
    }

    public void setProductCommentStatus(Integer productCommentStatus) {
        this.productCommentStatus = productCommentStatus;
    }

    public static class CommentStatsDTO {
        private double averageRating;
        private long ratingCount;

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
}