package com.ezban.saveproduct.model;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "save_product", schema = "ezban")
public class SaveProduct implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "save_product_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saveProductNo;

    @NotNull(message = "會員編號: 請勿空白")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @NotNull(message = "商品編號: 請勿空白")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    public SaveProduct() {
    }

    public Integer getSaveProductNo() {
        return this.saveProductNo;
    }

    public void setSaveProductNo(Integer saveProductNo) {
        this.saveProductNo = saveProductNo;
    }

    public Member getMemberVO() {
        return this.member;
    }

    public void setMemberVO(Member member) {
        this.member = member;
    }

    public Product getProductVO() {
        return this.product;
    }

    public void setProductVO(Product product) {
        this.product = product;
    }
}