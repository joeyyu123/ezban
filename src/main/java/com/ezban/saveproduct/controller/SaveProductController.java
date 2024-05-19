package com.ezban.saveproduct.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
//@RequestMapping("/saveproduct")
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
        // 將收藏的商品列表添加到模型中
        model.addAttribute("saveProductListData", saveProducts);
        // 返回視圖
        return "frontstage/saveproduct/saveProductListByMember";
    }


    // 新增收藏商品
    @PreAuthorize("hasRole('member')")
    @PostMapping("/saveproduct/insert")
    public ResponseEntity<?> submitSaveProduct(@RequestBody AddSaveProductDTO addSaveProductDTO) {

        try {
            // 執行更新操作
            SaveProduct saveProduct = saveProductSvc.toggleSaveStatus(addSaveProductDTO);

            // 更新成功，返回 HTTP 200 OK 狀態碼
            if (saveProduct.getSaveStatus() == 0) {
                return ResponseEntity.ok("您已成功將商品取消收藏！");
            } else {
                return ResponseEntity.ok("您已成功將商品加入收藏！");
            }
        } catch (Exception e) {
            // 處理可能的錯誤，例如點擊時發生異常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("抱歉，您並未成功將商品加入收藏！" );
        }
    }

}