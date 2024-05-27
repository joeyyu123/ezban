package com.ezban.productcomment.model;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;
import com.ezban.productcommentreport.model.ProductCommentReport;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "product_comment")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_comment_no", nullable = false)
    private Integer productCommentNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_no", nullable = false)
    @JsonBackReference
    private Product product;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    @JsonBackReference
    private Member member;

    @Column(name = "product_comment_content")
    private String productCommentContent;

    @Min(1)
    @Column(name = "product_rate")
    private Integer productRate;

    @Column(name = "product_comment_date", nullable = false)
    private LocalDateTime productCommentDate;

    @Column(name = "product_comment_status")
    private Byte productCommentStatus;

    @OneToMany(mappedBy = "productComment")
    @JsonManagedReference
    private Set<ProductCommentReport> productCommentReports = new LinkedHashSet<>();

    public Set<ProductCommentReport> getProductCommentReports() {
        return productCommentReports;
    }

    public void setProductCommentReports(Set<ProductCommentReport> productCommentReports) {
        this.productCommentReports = productCommentReports;
    }

    public ProductComment() {
        // 默认构造方法
    }

    public ProductComment(Integer productCommentNo, @NotNull Product product, @NotNull Member member,
                          String productCommentContent, Integer productRate, LocalDateTime productCommentDate, Byte productCommentStatus) {
        this.productCommentNo = productCommentNo;
        this.product = product;
        this.member = member;
        this.productCommentContent = productCommentContent;
        this.productRate = productRate;
        this.productCommentDate = productCommentDate;
        this.productCommentStatus = productCommentStatus;
    }

    // 新增的构造函数
    public ProductComment(ProductCommentDTO dto, Member member, Product product) {
        this.productCommentNo = dto.getProductCommentNo();
        this.member = member;
        this.product = product;
        this.productCommentContent = dto.getCommentContent();
        this.productRate = dto.getProductRate();
        this.productCommentDate = dto.getProductCommentDate();
        this.productCommentStatus = dto.getProductCommentStatus();
    }

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

    public String getProductCommentContent() {
        return productCommentContent;
    }

    public void setProductCommentContent(String productCommentContent) {
        this.productCommentContent = productCommentContent;
    }

    public Integer getProductRate() {
        return productRate;
    }

    public void setProductRate(Integer productRate) {
        this.productRate = productRate;
    }

    public LocalDateTime getProductCommentDate() {
        return productCommentDate;
    }

    public void setProductCommentDate(LocalDateTime productCommentDate) {
        this.productCommentDate = productCommentDate;
    }

    public Byte getProductCommentStatus() {
        return productCommentStatus;
    }

    public void setProductCommentStatus(Byte productCommentStatus) {
        this.productCommentStatus = productCommentStatus;
    }

    @Override
    public String toString() {
        return "ProductComment [productCommentNo=" + productCommentNo + ", product=" + product + ", member=" + member
                + ", productCommentContent=" + productCommentContent + ", productRate=" + productRate
                + ", productCommentDate=" + productCommentDate + ", productCommentStatus=" + productCommentStatus + "]";
    }
}
