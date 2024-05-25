package com.ezban.productreport.controller;

import com.ezban.member.model.MemberService;
import com.ezban.productreport.model.AddProductReportDTO;
import com.ezban.productreport.model.ProductReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ezban.productreport.model.ProductReportService;
import java.security.Principal;

@Controller
public class FrontstageProductReportController {

    @Autowired
    ProductReportService productReportSvc;

    @Autowired
    MemberService memberService;

    /**
     * 會員需登錄才可進行檢舉
     *
     * @param principal 當前登入的用戶信息
     * @return 訂單列表頁面
     */
    @PostMapping("/productreport/insert")
    public ResponseEntity<?> submitProductReport(@RequestBody AddProductReportDTO addProductReportDTO, Principal principal) {

        try {
            // 從 Principal 對象中獲取當前登錄用戶的用戶名
            Integer memberNo  = Integer.parseInt(principal.getName());
            addProductReportDTO.setMemberNo(memberNo);
            // 執行新增操作
            ProductReport productReport = productReportSvc.addProductReport(addProductReportDTO);
            // 更新成功，返回 HTTP 200 OK 狀態碼
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 處理可能的錯誤，例如更新時發生異常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("檢舉失敗: " + e.getMessage());
        }

    }

}