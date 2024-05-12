package com.ezban.qa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "qa", schema = "ezban")
public class Qa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qa_no", nullable = false)
    private Integer qaNo;

    @Column(name = "qa_title", length = 50)
    private String qaTitle;

    @Column(name = "qa_content")
    private String qaContent;

    public Qa() {
    }

    public Qa(String qaTitle, String qaContent) {
        this.qaTitle = qaTitle;
        this.qaContent = qaContent;
    }

    public Integer getQaNo() {
        return qaNo;
    }

    public void setQaNo(Integer qaNo) {
        this.qaNo = qaNo;
    }

    public String getQaTitle() {
        return qaTitle;
    }

    public void setQaTitle(String qaTitle) {
        this.qaTitle = qaTitle;
    }

    public String getQaContent() {
        return qaContent;
    }

    public void setQaContent(String qaContent) {
        this.qaContent = qaContent;
    }
}
