package com.ezban.saveproduct.model;

import java.util.List;
import java.util.Optional;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveProductService {

    @Autowired
    private SaveProductRepository saveProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MemberRepository memberRepository;


    //收藏與取消收藏商品
    public SaveProduct toggleSaveStatus(AddSaveProductDTO addSaveProductDTO) {

        SaveProduct saveProduct;
        // 1. 有沒有 SaveProduct(用 ProductNo and MemberNo 檢查)
        Optional<SaveProduct> saveProducts = saveProductRepository.findSaveProductByMemberAndProduct(addSaveProductDTO.getMemberNo(), addSaveProductDTO.getProductNo());
        if (saveProducts.isEmpty()) {

            // 1-1. 沒有;則新增收藏
            saveProduct = new SaveProduct();
            Product product = productRepository.findById(addSaveProductDTO.getProductNo()).orElseThrow(() -> new RuntimeException("找不到商品"));
            saveProduct.setProduct(product);
            Member member = memberRepository.findById(addSaveProductDTO.getMemberNo()).orElseThrow(() -> new RuntimeException("找不到會員"));
            saveProduct.setMember(member);
            saveProduct.setSaveStatus((byte) 1);

            saveProductRepository.save(saveProduct);

        }

        // 1-2. 有;則修改狀態為取消收藏
        else {

            saveProduct = saveProducts.get();
            if (saveProduct.getSaveStatus() == 1) {
                saveProduct.setSaveStatus((byte) 0);
            } else {
                saveProduct.setSaveStatus((byte) 1);
            }
            saveProductRepository.save(saveProduct);

        }

        return saveProduct;

    }


    // 會員查找自己的收藏
    public List<SaveProduct> findByMember(Integer memberNo) {
        return saveProductRepository.findByMember(memberNo);
    }

}