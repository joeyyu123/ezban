package com.ezban.productcategory.model;

import com.ezban.product.model.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "product_category", schema = "ezban")
public class ProductCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_no", nullable = false)
    private Integer productCategoryNo;

    @Column(name = "product_category_name", length = 50)
    private String productCategoryName;

    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Product> products = new LinkedHashSet<>();

    public ProductCategory() {
    }

    public Integer getProductCategoryNo() {
        return productCategoryNo;
    }

    public void setProductCategoryNo(Integer productCategoryNo) {
        this.productCategoryNo = productCategoryNo;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }


}
