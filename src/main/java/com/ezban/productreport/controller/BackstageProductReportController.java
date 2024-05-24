package com.ezban.productreport.controller;

import com.ezban.productreport.model.ProductReport;
import com.ezban.productreport.model.ProductReportService;
import com.ezban.productreport.model.UpdateProductReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/admin/productreport")
public class BackstageProductReportController {

    @Autowired
    ProductReportService productReportSvc;

    @ModelAttribute("productReportListData")
    public List<ProductReport> referenceListData(Model model) throws Exception {

        List<ProductReport> list = productReportSvc.getAll();
        return list;

    }


    // 顯示所有商品被檢舉的紀錄
    @GetMapping("/listAllProductReport")
    public String listAllProductReport(Model model) {
        return "backstage/productreport/listAllProductReport";
    }


    // 更新商品被檢舉的狀態
    @PostMapping("/update")
    public ResponseEntity<?> updateProductReportStatus(@RequestBody UpdateProductReportDTO updateProductReportDTO,
                                                       Principal principal) {

        try {

            //拿员工的資料(暫定先寫死)
//            Integer adminNo = 2;
            Integer adminNo = Integer.parseInt(principal.getName());
            // 執行更新操作並將管理員資訊回傳
            productReportSvc.updateProductReport(updateProductReportDTO, adminNo);
            // 更新成功，返回 HTTP 200 OK 狀態碼
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 處理可能的錯誤，例如更新時發生異常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product report: " + e.getMessage());
        }

    }

}