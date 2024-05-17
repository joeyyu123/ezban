package com.ezban.productcomment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;

@Entity
@Table(name = "product_comment")
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_no", nullable = false)
    private Integer productCommentNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @Min(1)
    @Column(name = "product_rate")
    private Integer productRate;

    @Column(name = "product_comment_content")
    private String productCommentContent;

    @NotNull
    @Column(name = "product_comment_date", nullable = false)
    private LocalDateTime productCommentDate;

    @Column(name = "product_comment_status")
    private Integer productCommentStatus;

    // Getter 和 Setter 方法
    public Integer getProductCommentNo() {
        return productCommentNo;
    }

    public void setProductCommentNo(Integer productCommentNo) {
        this.productCommentNo = productCommentNo;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
        this.productRate = productRate;
    }

    public String getProductCommentContent() {
        return productCommentContent;
    }

    public void setProductCommentContent(String productCommentContent) {
        this.productCommentContent = productCommentContent;
    }

    public LocalDateTime getProductCommentDate() {
        return productCommentDate;
    }

    public void setProductCommentDate(LocalDateTime productCommentDate) {
        this.productCommentDate = productCommentDate;
    }

    public Integer getProductCommentStatus() {
        return productCommentStatus;
    }

    public void setProductCommentStatus(Integer productCommentStatus) {
        this.productCommentStatus = productCommentStatus;
    }
}
