package com.ezban.productorder.controller;

import java.util.List;
import javax.validation.Valid;

import com.ezban.productorder.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productorder")
public class FrontstageProductOrderController {

    @Autowired
    ProductOrderService productOrderSvc;

    // 新增訂單(後端測試正常,待確認是否與前端可以正常處理)
    @GetMapping("/addProductOrder")
    public String addProductOrder(ModelMap model) {
        ProductOrder productOrder = new ProductOrder();
        model.addAttribute("productOrder", productOrder);
        return "frontstage/productorder/addProductOrder";
    }

    @PostMapping("/insert")
    public String insert(@Valid AddProductOrderDTO addProductOrderDTO, BindingResult result, ModelMap model) throws Exception {
        productOrderSvc.addProductOrder(addProductOrderDTO);
        List<ProductOrder> list = productOrderSvc.getAll();
        model.addAttribute("productOrderListData", list);
        return "redirect:/productorder/listAllProductOrder";
    }


    // 會員查詢自己的訂單
    @GetMapping("/findByMember/{memberNo}")
    public String findByMember(@PathVariable("memberNo") Integer memberNo, Model model) {
        List<ProductOrder> productOrders = productOrderSvc.findByMember(memberNo);
        model.addAttribute("productOrderListData", productOrders);
        return "frontstage/productorder/productOrderListByMember";
    }


    // 會員取消訂單
    @PostMapping("/cancelProductOrder")
    public ResponseEntity<?> cancelProductOrder(@RequestBody CancelProductOrderDTO cancelProductOrderDTO) {

        try {
            // 執行更新操作
            productOrderSvc.cancelProductOrderDTO(cancelProductOrderDTO);

            // 更新成功，返回 HTTP 200 OK 狀態碼
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            // 處理可能的錯誤，例如更新時發生異常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product report: " + e.getMessage());
        }
    }

}