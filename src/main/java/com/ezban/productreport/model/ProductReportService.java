package com.ezban.productreport.model;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.ezban.admin.model.Admin;
import com.ezban.admin.model.AdminRepository;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.validation.Valid;

@Service
public class ProductReportService {

    @Autowired
    private ProductReportRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MemberRepository memberRepository;

    // 新增檢舉商品
    public ProductReport addProductReport(@Valid AddProductReportDTO addProductReportDTO) {

        ProductReport productReport = new ProductReport();
        // 根據 productNo 查找產品
        Product product = productRepository.findById(addProductReportDTO.getProductNo()).orElseThrow(() -> new RuntimeException("找不到商品"));
        productReport.setProduct(product);
        // 根據當前登入查找會員
        Member member = memberRepository.findById(addProductReportDTO.getMemberNo()).orElseThrow(() -> new RuntimeException("找不到會員"));
        productReport.setMember(member);
        // 設置檢舉理由
        productReport.setReportReason(addProductReportDTO.getReportReason());
        // 保存檢舉
        repository.save(productReport);
        return productReport;

    }


    // 顯示全部被檢舉的商品
    public List<ProductReport> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "reportStatus"));
    }


    // 更新審核的狀態
    public void updateProductReport(UpdateProductReportDTO updateProductReportDTO, Integer adminNo) {

        Optional<ProductReport> optionalProductReport = repository.findById(updateProductReportDTO.getProductReportNo());
        if (optionalProductReport.isPresent()) {

            ProductReport productReport = optionalProductReport.get();
            productReport.setReportStatus(updateProductReportDTO.getSelectedValue());
            productReport.setAdmin(adminRepository.findById(adminNo).orElseThrow());

            // 檢查檢舉狀態是否為2（下架）
            if (updateProductReportDTO.getSelectedValue() == 2) {

                Product product = productReport.getProduct();
                if (product != null) {
                    product.setProductStatus((byte) 2); // 將商品狀態設為2（下架）
                    productRepository.save(product); // 商品狀態更改
                }
            }

            repository.save(productReport);

        }

    }

}