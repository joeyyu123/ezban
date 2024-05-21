package com.ezban.productreport.model;

import java.util.List;
import java.util.Optional;

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
    public void addProductReport(@Valid AddProductReportDTO addProductReportDTO) {

        ProductReport productReport = new ProductReport();
        Product product = productRepository.findById(addProductReportDTO.getProductNo()).get();
        productReport.setProduct(product);
        Member member = memberRepository.findById(addProductReportDTO.getMemberNo()).get() ;
        productReport.setMember(member);
        productReport.setReportReason(addProductReportDTO.getReportReason());

        repository.save(productReport);
    }


    // 顯示全部被檢舉的商品
    public List<ProductReport> getAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "reportStatus"));
    }


    // 更新審核的狀態
    public void updateProductReport(UpdateProductReportDTO updateProductReportDTO, String adminName, Integer adminNo) {

        Optional<ProductReport> optionalProductReport = repository.findById(updateProductReportDTO.getProductReportNo());
        if (optionalProductReport.isPresent()) {
            ProductReport productReport = optionalProductReport.get();
            productReport.setReportStatus(updateProductReportDTO.getSelectedValue());
            repository.save(productReport);
        }
    }

}