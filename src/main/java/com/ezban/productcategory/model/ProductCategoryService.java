package com.ezban.productcategory.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void addCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void updateCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public void deleteCategory(Integer productCategoryNo) {
        if (productCategoryRepository.existsById(productCategoryNo)) {
            productCategoryRepository.deleteById(productCategoryNo);
        }
    }

    public List<ProductCategory> getAllProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getOneCategory(Integer productCategoryNo) {
        Optional<ProductCategory> optional = productCategoryRepository.findById(productCategoryNo);
        return optional.orElse(null);
    }
}
