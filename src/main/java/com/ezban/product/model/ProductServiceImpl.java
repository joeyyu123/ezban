package com.ezban.product.model;

import com.ezban.host.model.Host;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.productimg.model.ProductImg;
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

    @Autowired
    private MemberRepository memberRepository;


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
     */
    @Transactional
    public void addProductandImages(ProductDto productDto, MultipartFile[] files) throws IOException {
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
    }

    @Override
    public Product updateProduct(Integer productNo, ProductDto productDto) {
        Product product = ProductMapper.toEntity(productDto);
        return productRepository.save(product);
    }

    /**
     * 更新商品及商品圖片
     *
     * @param productDto   商品資料
     * @param deleteImages 要刪除的圖片編號
     * @param files        新增的圖片
     */
    @Transactional
    public void updateProductAndImages(ProductDto productDto, List<Integer> deleteImages, MultipartFile[] files) throws IOException {
        Product product = ProductMapper.toEntity(productDto);
        List<ProductImg> remainingImages = productImgService.getImgByProduct(product.getProductNo());

        if (deleteImages != null) {
            remainingImages.removeIf(image -> deleteImages.contains(image.getProductImgNo()));
            productImgService.deleteImgByImgNos(deleteImages);
        }

        boolean hasValidImg = false;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                hasValidImg = true;
                break;
            }
        }
        if (remainingImages.isEmpty() && !hasValidImg) {
            throw new IllegalStateException("*請上傳至少一張圖片");
        }
        if (hasValidImg) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    productImgService.addImage(file, product.getProductNo());
                }
            }
        }
        productRepository.save(product);
    }

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
        return productRepository.findLaunched();
    }

    public List<Product> getProductsByHostandCategory(Integer hostNo, Integer productCategoryNo, Integer productNo) {
        return productRepository.findByHostNoandProductCategoryNp(hostNo, productCategoryNo, productNo);
    }

    /**
     * 檢查商品的庫存是否足夠，若足夠要更新庫存數量。
     *
     * @param productNo 商品編號
     * @param quantity  訂單的商品數量
     * @return 如果庫存足夠並更新庫存成功，回傳 true。
     *
     */
    @Transactional
    public boolean checkAndUpdateStock(Integer productNo, Integer quantity) {
        Product product = productRepository.findById(productNo).orElseThrow(() ->
                new EntityNotFoundException("查無此商品")
        );

        Integer currentStock = product.getRemainingQty();
        if (currentStock >= quantity) {
            int newStock = currentStock - quantity;
            product.setRemainingQty(newStock);
            if (newStock == 0) {
                product.setProductStatus((byte) 2);
            }
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 檢查商品是否為該廠商所擁有
     *
     * @param principal  使用者
     * @param productDto 商品資料
     * @return 如果商品是該廠商上架的，回傳 true。
     *
     */
    public boolean isAuthenticated(Principal principal, ProductDto productDto) {
        return principal.getName().equals(productDto.getHostNo().toString());
    }

    public Member getMemberByMemberNo(Integer memberNo) {
        return memberRepository.findById(memberNo).orElseThrow();
    }
}




