package com.ezban.productorder.controller;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;
import javax.validation.Valid;

import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import com.ezban.productorder.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/productorder")
public class FrontstageProductOrderController {

    @Autowired
    ProductOrderService productOrderSvc;

    @Autowired
    MemberService memberService;

    // 新增訂單
    @GetMapping("/productorder/addProductOrder")
    public String addProductOrder(ModelMap model) {
        ProductOrder productOrder = new ProductOrder();
        model.addAttribute("productOrder", productOrder);
        return "frontstage/productorder/addProductOrder";
    }

    @PostMapping("/productorder/insert")
    public String insert(@Valid @RequestBody AddProductOrderDTO addProductOrderDTO, BindingResult result, ModelMap model) throws Exception {
        productOrderSvc.addProductOrder(addProductOrderDTO);
        List<ProductOrder> list = productOrderSvc.getAll();
        model.addAttribute("productOrderListData", list);
        return "redirect:/productorder/listAllProductOrder";
    }


    /**
     * 根據登入的會員編號查找自己的訂單
     *
     * @param model     模型
     * @param principal 當前登入的會員信息
     * @return 訂單列表頁面
     */
    @GetMapping("/productorder/findByMember")
    public String findByMember(Model model, Principal principal) {
        // 獲取當前登入的會員編號
        Integer memberNo = Integer.parseInt(principal.getName());
        // 根據會員編號查找訂單
        List<ProductOrder> productOrders = productOrderSvc.findByMember(memberNo);
        // 將訂單列表添加到模型中
        model.addAttribute("productOrderListData", productOrders);
        // 返回視圖
        return "frontstage/productorder/productOrderListByMember";
    }


    // 會員取消訂單(經由查詢後的跳轉頁,故不進行第二次身分驗證)
    @PreAuthorize("hasRole('member')")
    @PostMapping("/productorder/cancelProductOrder")
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