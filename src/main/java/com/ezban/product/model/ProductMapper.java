package com.ezban.product.model;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostRepository;
import com.ezban.productcategory.model.ProductCategory;
import com.ezban.productcategory.model.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ProductMapper {

    private static ProductCategoryRepository productCategoryRepository;
    private static HostRepository hostRepository;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public ProductMapper(ProductCategoryRepository catRepo, HostRepository hostRepo) {
        productCategoryRepository = catRepo;
        hostRepository = hostRepo;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product();

        if (productDto.getProductNo() != null) {
            product.setProductNo(productDto.getProductNo());
        }

        product.setProductName(productDto.getProductName());
        product.setProductDesc(productDto.getProductDesc());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductAddQty(productDto.getProductAddQty());
        product.setRemainingQty(productDto.getRemainingQty());

        product.setProductAddTime(productDto.getProductAddTimeTimeStamp());
        product.setProductRemoveTime(productDto.getProductRemoveTimeTimeStamp());

        product.setProductStatus(productDto.getProductStatus());

//        product.setProductCategoryNo(productDto.getProductCategoryNo());
//        product.setHostNo(productDto.getHostNo());

        ProductCategory category = productCategoryRepository.findById(productDto.getProductCategoryNo()).orElse(null);
        product.setProductCategory(category);

        Host host = hostRepository.findById(productDto.getHostNo()).orElse(null);
        product.setHost(host);

        return product;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setProductName(product.getProductName());
        productDto.setProductDesc(product.getProductDesc());
        productDto.setProductPrice(product.getProductPrice());
        productDto.setProductAddQty(product.getProductAddQty());
        productDto.setRemainingQty(product.getRemainingQty());
        productDto.setProductAddTime(dateFormat.format(product.getProductAddTime()));
        productDto.setProductRemoveTime(dateFormat.format(product.getProductRemoveTime()));
        productDto.setProductStatus(product.getProductStatus());

//        productDto.setHostNo(product.getHostNo());
//        productDto.setProductCategoryNo(product.getProductCategoryNo());

        if (product.getProductCategory() != null) {
            productDto.setProductCategoryNo(product.getProductCategory().getProductCategoryNo());
        }
        if (product.getHost() != null) {
            productDto.setHostNo(product.getHost().getHostNo());
        }
        return productDto;
    }
}
