package com.ezban.eventcomment.model;

public class EventCommentDTO {
    private Integer eventCommentNo;
    private Integer memberNo;
    private String eventCommentContent;
    private Integer eventCommentRate;
    private Byte eventCommentStatus;

    public EventCommentDTO(Integer eventCommentNo, Integer memberNo, String eventCommentContent, Integer eventCommentRate, Byte eventCommentStatus) {
        this.eventCommentNo = eventCommentNo;
        this.memberNo = memberNo;
        this.eventCommentContent = eventCommentContent;
        this.eventCommentRate = eventCommentRate;
        this.eventCommentStatus = eventCommentStatus;
    }

    public Integer getEventCommentNo() {
        return eventCommentNo;
    }

    public void setEventCommentNo(Integer eventCommentNo) {
        this.eventCommentNo = eventCommentNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getEventCommentContent() {
        return eventCommentContent;
    }

    public void setEventCommentContent(String eventCommentContent) {
        this.eventCommentContent = eventCommentContent;
    }

    public Integer getEventCommentRate() {
        return eventCommentRate;
    }

    public void setEventCommentRate(Integer eventCommentRate) {
        this.eventCommentRate = eventCommentRate;
    }

    public Byte getEventCommentStatus() {
        return eventCommentStatus;
    }

    public void setEventCommentStatus(Byte eventCommentStatus) {
        this.eventCommentStatus = eventCommentStatus;
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
