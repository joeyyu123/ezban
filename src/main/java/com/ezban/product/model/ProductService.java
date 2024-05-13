package com.ezban.product.model;

import com.ezban.host.model.Host;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product addProduct(ProductDto productDto);

    Product addProductandImages(ProductDto productDto, MultipartFile[] files) throws IOException;

    Product updateProduct(Integer productNo, ProductDto productDto);

    Product getProductByProductNo(Integer productNo);

    List<Product> getProductsByHost(Host host);

    List<Product> getProductsByProductCategoryNo(Integer productCategoryNo);

    List<Product> getAll();

}
