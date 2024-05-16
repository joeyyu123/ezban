package com.ezban.product.model;

import com.ezban.host.model.Host;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImgService productImgService;



    @Transactional
    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        return productRepository.save(product);
    }

    @Transactional
    public Product addProductandImages(ProductDto productDto, MultipartFile[] files) throws IOException {
        Product product = ProductMapper.toEntity(productDto);
        product = productRepository.save(product);

        boolean hasValidImage = false;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                hasValidImage = true;
                productImgService.addImage(file, product.getProductNo());
            }
        }
        if (!hasValidImage) {
            throw new IllegalArgumentException("*請上傳至少一張圖片");
        }
        return product;
    }

    public Product updateProduct(Integer productNo, ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        return productRepository.save(product);
    }

    public void deleteProduct(Integer productNo) {
        if (productRepository.existsById(productNo)) {
            productRepository.deleteById(productNo);
        }
    }

    @Override
    public List<Product> getProductsByHost(Host host) {
        return productRepository.findByHost(host);
    }

    public Product getProductByProductNo(Integer productNo) {
        Optional<Product> optional = productRepository.findById(productNo);
        return optional.orElse(null);
    }

    public List<Product> getProductsByProductCategoryNo(Integer productCategoryNo) {
        return productRepository.findByProductCategoryProductCategoryNo(productCategoryNo);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

}


