package com.ezban.productcomment.model;

public class ProductCommentDTO {
    private Integer memberNo;
    private String commentContent;
    private Integer productRate;

    public ProductCommentDTO(Integer memberNo, String commentContent, Integer productRate) {
        this.memberNo = memberNo;
        this.commentContent = commentContent;
        this.productRate = productRate;
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

    // Nested class for CommentStatsDTO
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