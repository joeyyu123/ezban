package com.ezban.saveproduct.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezban.member.model.MemberService;
import com.ezban.saveproduct.model.AddSaveProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ezban.saveproduct.model.SaveProduct;
import com.ezban.saveproduct.model.SaveProductService;

@Controller
public class SaveProductController {

    @Autowired
    SaveProductService saveProductSvc;

    @Autowired
    MemberService memberService;


    /**
     * 根據會員查找收藏紀錄
     * @param model 模型
     * @param principal 當前登入的用戶信息
     * @return 收藏列表頁面
     */
    @GetMapping("/saveproduct/findByMember")
    public String findByMember(Model model, Principal principal) {

        // 獲取當前登入的會員編號
        Integer memberNo = Integer.parseInt(principal.getName());
        // 根據會員編號查找收藏的商品列表
        List<SaveProduct> saveProducts = saveProductSvc.findByMember(memberNo);
        model.addAttribute("saveProductListData", saveProducts);
        return "frontstage/saveproduct/saveProductListByMember";

    }


    // 新增收藏商品
    @PostMapping("/saveproduct/insert")
    public ResponseEntity<?> submitSaveProduct(@RequestBody AddSaveProductDTO addSaveProductDTO, Principal principal) {

        try {
            // 從 Principal 對象中獲取當前登錄用戶的會員編號
            Integer memberNo = Integer.parseInt(principal.getName());
            addSaveProductDTO.setMemberNo(memberNo);

            // 執行更新操作
            SaveProduct saveProduct = saveProductSvc.toggleSaveStatus(addSaveProductDTO);

            // 更新成功，返回 HTTP 200 OK 狀態碼
            Map<String, Object> response = new HashMap<>();
            response.put("saveStatus", saveProduct.getSaveStatus());
            if (saveProduct.getSaveStatus() == 0) {
                response.put("message", "您已成功將商品取消收藏！");
            } else {
                response.put("message", "您已成功將商品加入收藏！");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {

            // 處理可能的錯誤，例如點擊時發生異常
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "抱歉，您並未成功將商品加入收藏！");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

}