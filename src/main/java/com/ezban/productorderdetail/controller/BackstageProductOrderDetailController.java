package com.ezban.productorderdetail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.productorderdetail.model.ProductOrderDetailService;

@Controller
public class BackstageProductOrderDetailController {

    @Autowired
    ProductOrderDetailService productOrderDetailSVC;

    // 員工:依據訂單編號顯示明細
    @PostMapping("/admin/productorderdetail/adminOrderDetail/{productOrderNo}")
    public String adminOrderDetail(@PathVariable("productOrderNo") Integer productOrderNo, Model model) {

        List<ProductOrderDetail> productOrderDetails = productOrderDetailSVC.findByProductOrder(productOrderNo);
        model.addAttribute("productOrderDetailListData", productOrderDetails);
        return "backstage/productorderdetail/listAllProductOrderDetail";

    }


    // 廠商:依據訂單編號顯示明細
    @PostMapping("/backstage/productorderdetail/hostOrderDetail/{productOrderNo}")
    public String hostOrderDetail(@PathVariable("productOrderNo") Integer productOrderNo, Model model) {

        List<ProductOrderDetail> productOrderDetails = productOrderDetailSVC.findByProductOrder(productOrderNo);
        model.addAttribute("productOrderDetailListData", productOrderDetails);
        return "backstage/productorderdetail/listAllProductOrderDetailByHost";

    }

}


