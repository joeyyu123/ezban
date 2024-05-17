package com.ezban.saveproduct.controller;

import java.util.List;
import java.util.Optional;

import com.ezban.saveproduct.model.AddSaveProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ezban.saveproduct.model.SaveProduct;
import com.ezban.saveproduct.model.SaveProductService;

@Controller
@RequestMapping("/saveproduct")
public class SaveProductController {

    @Autowired
    SaveProductService saveProductSvc;

    // 會員查詢自己的收藏
    @GetMapping("/findByMember/{memberNo}")
    public String findByMember(@PathVariable("memberNo") Integer memberNo, Model model) {
        List<SaveProduct> saveProducts = saveProductSvc.findByMember(memberNo);
        model.addAttribute("saveProductListData", saveProducts);
        return "frontstage/saveproduct/saveProductListByMember";
    }


    // 新增收藏商品
    @PostMapping("/insert")
    public ResponseEntity<?> submitSaveProduct(@RequestBody AddSaveProductDTO addSaveProductDTO) {

        try {

            // 先測試資料,之後換成session裡面的memberNo
            addSaveProductDTO.setMemberNo(1);

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("抱歉，您並未成功將商品加入收藏 !" );
        }
    }

}