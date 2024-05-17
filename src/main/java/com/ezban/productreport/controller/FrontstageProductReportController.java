package com.ezban.productreport.controller;

import com.ezban.productreport.model.AddProductReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ezban.productreport.model.ProductReportService;

@Controller
@RequestMapping("/productreport")
public class FrontstageProductReportController {

    @Autowired
    ProductReportService productReportSvc;

    // 新增檢舉商品
    @PostMapping("/insert")
    public ResponseEntity<?> submitProductReport(@RequestBody AddProductReportDTO addProductReportDTO) {

        try {

            // 先測試資料,之後換成session裡面的memberNo
            addProductReportDTO.setMemberNo(10);

            // 執行更新操作
            productReportSvc.addProductReport(addProductReportDTO);

            // 更新成功，返回 HTTP 200 OK 狀態碼
            return ResponseEntity.ok().build();
        } catch (Exception e) {

            // 處理可能的錯誤，例如更新時發生異常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("檢舉失敗: " + e.getMessage());
        }
    }
}