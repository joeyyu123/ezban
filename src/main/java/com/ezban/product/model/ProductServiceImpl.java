package com.ezban.product.model;

import com.ezban.host.model.Host;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
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

    /**
     * 新增商品及商品圖片
     *
     * @param productDto 商品資料
     * @param files      商品圖片
     * @return 新增的商品
     * @throws IOException 處理圖片例外
     */
    @Transactional
    @Override
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

    @Override
    public Product updateProduct(Integer productNo, ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        return productRepository.save(product);
    }

//    public void deleteProduct(Integer productNo) {
//        if (productRepository.existsById(productNo)) {
//            productRepository.deleteById(productNo);
//        }
//    }

    @Override
    public List<Product> getProductsByHost(Host host) {
        return productRepository.findByHost(host);
    }

    @Override
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

    /**
     * 檢查商品的庫存是否足夠，若足夠要更新庫存數量。
     *
     * @param productNo 商品編號
     * @param quantity  訂單的商品數量
     * @return 如果庫存足夠並更新庫存成功，回傳 true。
     */
    @Transactional
    @Override
    public boolean checkAndUpdateStock(Integer productNo, Integer quantity) {
        Product product = productRepository.findById(productNo).orElseThrow(() ->
                new EntityNotFoundException("查無此商品")
        );

        Integer currentStock = product.getRemainingQty();
        if (currentStock >= quantity) {
            product.setRemainingQty(currentStock - quantity);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

    public boolean isAuthenticated(Principal principal, ProductDto productDto) {
        return principal.getName().equals(productDto.getHostNo().toString());
    }
}




