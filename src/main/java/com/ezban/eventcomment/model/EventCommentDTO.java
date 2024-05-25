package com.ezban.eventcomment.model;

public class EventCommentDTO {
    private Integer eventCommentNo;
    private Integer eventNo; // 添加 eventNo 屬性
    private Integer memberNo;
    private String eventCommentContent;
    private Integer eventCommentRate;
    private Byte eventCommentStatus;

    public EventCommentDTO(Integer eventCommentNo, Integer eventNo, Integer memberNo, String eventCommentContent, Integer eventCommentRate, Byte eventCommentStatus) {
        this.eventCommentNo = eventCommentNo;
        this.eventNo = eventNo; // 初始化 eventNo
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

    public Integer getEventNo() { // 添加 getter 方法
        return eventNo;
    }

    public void setEventNo(Integer eventNo) { // 添加 setter 方法
        this.eventNo = eventNo;
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

    public static class EventAndMemberDTO { // 添加 EventAndMemberDTO 類
        private Integer eventNo;
        private Integer memberNo;

        public EventAndMemberDTO(Integer eventNo, Integer memberNo) {
            this.eventNo = eventNo;
            this.memberNo = memberNo;
        }

        public Integer getEventNo() {
            return eventNo;
        }

        public void setEventNo(Integer eventNo) {
            this.eventNo = eventNo;
        }

        public Integer getMemberNo() {
            return memberNo;
        }

        public void setMemberNo(Integer memberNo) {
            this.memberNo = memberNo;
        }
    }
}
