package com.ezban.saveproduct.model;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "save_product", schema = "ezban")
public class SaveProduct implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "save_product_no", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer saveProductNo;

    @NotNull(message = "* 會員編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "member_no",nullable = false)
    private Member member;

    @NotNull(message = "* 商品編號: 請勿空白 !")
    @ManyToOne
    @JoinColumn(name = "product_no", nullable = false)
    private Product product;

    @NotNull(message = "* 商品收藏狀態: 請勿空白 !")
    @Column(name = "save_status", nullable = false)
    @Min(value = 0, message = "* 商品收藏狀態不正確 !")
    @Max(value = 1, message = "* 商品收藏狀態不正確 !")
    private Byte saveStatus;

    public SaveProduct() {
    }

    public Integer getSaveProductNo() {
        return saveProductNo;
    }

    public void setSaveProductNo(Integer saveProductNo) {
        this.saveProductNo = saveProductNo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Byte getSaveStatus() {
        return saveStatus;
    }

    public void setSaveStatus(Byte saveStatus) {
        this.saveStatus = saveStatus;
    }


}