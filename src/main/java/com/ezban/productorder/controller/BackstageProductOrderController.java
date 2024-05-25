package com.ezban.productorder.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;

import com.ezban.productorder.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BackstageProductOrderController {

    @Autowired
    ProductOrderService productOrderSvc;

    @Autowired
    ProductOrderEmailService productOrderEmailService;

    @ModelAttribute("productOrderListData")
    public List<ProductOrder> referenceListData(Model model) throws Exception {
        List<ProductOrder> list = productOrderSvc.getAll();
        return list;
    }

    // 員工管理訂單的查詢首頁路徑
    @GetMapping("/admin/productorder/selectPage")
    public String SelectPage() {
        return "backstage/productorder/selectPage";
    }


    // 廠商後台管理訂單的首頁路徑
    @GetMapping("/backstage/productorder/HostOrder")
    public String HostOrder() {
        return "backstage/productorder/productOrderListByHost";
    }


    // 顯示所有訂單路徑
    @GetMapping("/admin/productorder/listAllProductOrder")
    public String listAllProductOrder(Model model) {
        return "backstage/productorder/listAllProductOrder";
    }


    // 選擇單筆訂單路徑
    @GetMapping("/admin/productorder/listOneProductOrder")
    public String listOneProductOrder(Model model) {
        return "backstage/productorder/listOneProductOrder";
    }


    // 顯示單筆訂單
    @PostMapping("/admin/productorder/getOneForDisplay")
    public String getOneForDisplay(@RequestParam("productOrderNo") String productOrderNo, ModelMap model) {

        Integer no = validProductNo(productOrderNo, model);

        if (no == null) return "backstage/productorder/selectPage";
        ProductOrder productOrder = productOrderSvc.getOneProductOrder(no);
        List<ProductOrder> list = productOrderSvc.getAll();
        model.addAttribute("productOrderListData", list);

        if (productOrder == null) {
            model.addAttribute("errorMessage", "* 查無資料，請填入正確的訂單編號 !");
            return "backstage/productorder/selectPage";
        }

        model.addAttribute("productOrder", productOrder);
        model.addAttribute("functionType", "query");
        return "backstage/productorder/listOneProductOrder";

    }
    // 錯誤訊息
    private static Integer validProductNo(String productOrderNo, ModelMap model) {

        String errorMsg = "";
        if (!StringUtils.hasText(productOrderNo)) {
            errorMsg = "* 請填入正確的訂單編號，訂單編號不可為空白 !";
        }
        Integer no = 0;
        try {
            no = Integer.valueOf(productOrderNo);
            if (no > 9999) {
                errorMsg = String.format("* 查無資料，訂單編號不能超過%d !", no);
            }
            if (no < 1001) {
                errorMsg = String.format("* 查無資料，訂單編號不能小於%d !", no);
            }
        } catch (NumberFormatException e) {
            errorMsg = "* 查無資料，請填入正確的訂單編號 !";
        }
        if (StringUtils.hasText(errorMsg)) {
            model.addAttribute("errorMessage", errorMsg);
            return null;
        }
        return no;

    }


    // 員工更新單筆訂單
    @PostMapping("/admin/productorder/getOneForUpdate")
    public String getOneForUpdate(@RequestParam("productOrderNo") String productOrderNo, ModelMap model) {

        ProductOrder productOrder = productOrderSvc.getOneProductOrder(Integer.valueOf(productOrderNo));
        model.addAttribute("updateProductOrderDTO", productOrder);
        return "backstage/productorder/updateProductOrderInput";

    }

    @PostMapping("/admin/productorder/update")
    public String update(@Valid UpdateProductOrderDTO updateProductOrderDTO, BindingResult result, ModelMap model, @RequestParam(required = false) Byte productPaymentStatus) throws IOException, MessagingException {

        if (result.hasErrors()) {
            model.addAttribute("updateProductOrderDTO", updateProductOrderDTO);
            return "backstage/productorder/updateProductOrderInput";
        }
        try {
            productOrderSvc.updateProductOrder(updateProductOrderDTO);
        } catch (RuntimeException e) {
            model.addAttribute("updateProductOrderDTO", updateProductOrderDTO);
            model.addAttribute("errorMessage", e.getMessage());
            return "backstage/productorder/updateProductOrderInput";
        }

        ProductOrder updatedProductOrderDTO = productOrderSvc.getOneProductOrder(updateProductOrderDTO.getProductOrderNo());
        model.addAttribute("productOrder", updatedProductOrderDTO);
        model.addAttribute("functionType", "update");

        // 若付款狀態更改為已退款(1)，以寄信方式通知會員
        if (productPaymentStatus == 1) {
            ProductOrder productOrder = productOrderSvc.findById(updateProductOrderDTO.getProductOrderNo());
            productOrderEmailService.sendOrderEmail(productOrder);
        }
        return "backstage/productorder/listOneProductOrder";

    }


    /**
     * 根據登入的廠商編號查詢廠商各自的訂單
     *
     * @param model     模型
     * @param principal 當前登入的廠商信息
     * @return 訂單列表頁面
     */
    @GetMapping("/backstage/productorder/findByHost")
    public String findByHost(Model model, Principal principal) {
        // 獲取當前登入的廠商編號
        Integer hostNo = Integer.parseInt(principal.getName());
        // 根據廠商編號查找訂單
        List<ProductOrder> productOrders = productOrderSvc.findByHost(hostNo);
        // 將訂單列表添加到模型中
        model.addAttribute("productOrderListData", productOrders);
        return "backstage/productorder/productOrderListByHost";
    }


    // 廠商更新單筆訂單
    @PostMapping("/backstage/productorder/getOneForUpdateByHost")
    public String getOneForUpdateByHost(@RequestParam("productOrderNo") String productOrderNo, ModelMap model) {

        ProductOrder productOrder = productOrderSvc.getOneProductOrder(Integer.valueOf(productOrderNo));
        model.addAttribute("updateProductOrderByHostDTO", productOrder);
        return "backstage/productorder/updateProductOrderInputByHost";

    }
    @PostMapping("/backstage/productorder/updateByHost")
    public String updateByHost(@Valid UpdateProductOrderByHostDTO updateProductOrderByHostDTO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("updateProductOrderByHostDTO", updateProductOrderByHostDTO);
            return "backstage/productorder/updateProductOrderInputByHost";
        }
        try {
            productOrderSvc.updateProductOrderByHost(updateProductOrderByHostDTO);
        } catch (RuntimeException e) {
            model.addAttribute("updateProductOrderByHostDTO", updateProductOrderByHostDTO);
            model.addAttribute("errorMessage", e.getMessage());
            return "backstage/productorder/updateProductOrderInputByHost";
        }

        ProductOrder updatedProductOrderByHostDTO = productOrderSvc.getOneProductOrder(updateProductOrderByHostDTO.getProductOrderNo());
        model.addAttribute("productOrder", updatedProductOrderByHostDTO);
        model.addAttribute("functionType", "updateByHost");
        return "backstage/productorder/hostListOneProductOrder";

    }

}