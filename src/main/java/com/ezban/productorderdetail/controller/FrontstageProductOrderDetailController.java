package com.ezban.productorderdetail.controller;

import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.productorderdetail.model.ProductOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/productorderdetail")
public class FrontstageProductOrderDetailController {

    @Autowired
    ProductOrderDetailService productOrderDetailSvc;

    // 依據訂單編號顯示所有的明細
    @PostMapping("/findByProductOrderDetail/{productOrderNo}")
    public String findByProductOrderDetail(@PathVariable("productOrderNo") Integer productOrderNo, Model model) {
        List<ProductOrderDetail> productOrderDetails = productOrderDetailSvc.findByProductOrder(productOrderNo);
        model.addAttribute("productOrderDetailListData", productOrderDetails);
        return "frontstage/productorderdetail/listAllProductOrderDetail";
    }


    // 新增訂單明細(待確認能否正常新增)
    @GetMapping("/addProductOrderDetail")
    public String addProductOrderDetail(ModelMap model) {
        ProductOrderDetail productOrderDetail = new ProductOrderDetail();
        model.addAttribute("productOrderDetail", productOrderDetail);
        return "frontstage/productorderdetail/addProductOrderDetail";
    }
    @PostMapping("/insert")
    public String insert(@Valid ProductOrderDetail productOrderDetail, BindingResult result, ModelMap model) throws Exception {
        productOrderDetailSvc.addProductOrderDetail(productOrderDetail);
        List<ProductOrderDetail> list = productOrderDetailSvc.findByProductOrder(productOrderDetail.getProductOrderDetailNo());
        model.addAttribute("productOrderDetailListData", list);
        return "redirect:/productOrderDetail/listAllProductOrderDetail";
    }

}