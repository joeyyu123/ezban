package com.ezban.productimg.model;

import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductImgService {

    @Autowired
    private ProductImgRepository productImgRepository;

    @Autowired
    private ProductRepository productRepository;


    public Integer addImage(MultipartFile file, Integer productNo) throws IOException {
        ProductImg productImg = new ProductImg();
        productImg.setProductImg(file.getBytes());
        Product product = productRepository.findById(productNo).orElse(null);
        productImg.setProduct(product);

        productImg = productImgRepository.save(productImg);

        return productImg.getProductImgNo();
    }

    public void deleteImgByImgNo(Integer productImgNo) {
        productImgRepository.deleteByImgNo(productImgNo);
    }

    public void deleteImgByImgNos(List<Integer> productImgNos) {
        productImgRepository.deleteByImgNos(productImgNos);
    }

    public ProductImg getImgByImgNo(Integer productImgNo) {
        return productImgRepository.findById(productImgNo).orElse(null);
    }

    public List<ProductImg> getImgByProduct(Integer productNo) {
        return productImgRepository.findByProductProductNo(productNo);
    }

    public ProductImg getFirstProductImg(Integer productNo) {
        return productImgRepository.findFirstByProductProductNo(productNo);
    }

}
