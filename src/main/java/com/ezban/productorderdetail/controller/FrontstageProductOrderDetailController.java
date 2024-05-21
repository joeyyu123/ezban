package com.ezban.productorderdetail.controller;

import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.productorderdetail.model.ProductOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

}