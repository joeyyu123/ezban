package com.ezban.product.controller;

import com.ezban.product.model.Product;
import com.ezban.product.model.ProductDto;
import com.ezban.product.model.ProductMapper;
import com.ezban.product.model.ProductServiceImpl;
import com.ezban.productimg.model.ProductImg;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/backstage/product")
public class ProductBackstageController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductImgService productImgService;


    @GetMapping("test2")
    public String test2() {
        return "backstage/product/allProducts";
    }


//    @GetMapping("showProducts")
//    public String showProducts(Model model) {
//        Product product1 = productServiceImpl.getProductByProductNo(1);
//        List<Product> productsList = productServiceImpl.getProductsByHost(product1.getHost());
//        Map<Integer, List<ProductImg>> productImages = new HashMap<>();
//        for (Product p : productsList) {
//            List<ProductImg> imgs = productImgService.getImgByProduct(p.getProductNo());
//            productImages.put(p.getProductNo(), imgs);
//        }
//        model.addAttribute("productsList", productsList);
//        model.addAttribute("productImages", productImages);
//        model.addAttribute("successMessage", "商品新增成功!");
//        return "backstage/product/allProducts";
//    }

    @GetMapping("showProducts")
    public String showProducts(@RequestParam("productNo") Integer productNo, Model model
            , RedirectAttributes redirectAttributes
            , Principal principal) {

//        Integer.parseInt(principal.getName()).hostService;
        Product product = productServiceImpl.getProductByProductNo(productNo);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "找不到指定的商品");
            return "redirect:/backstage/product/allProducts";
        }
        prepareProductModel(model, product);
        return "backstage/product/allProducts";
    }

    /* ========================== 新增商品 ========================== */
    @GetMapping("addProduct")
    public String getAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "backstage/product/addProduct";
    }

    @PostMapping("insert")
    public String insert(ProductDto productDto, BindingResult result, Model model,
                         RedirectAttributes redirectAttributes,
                         @RequestParam("images") MultipartFile[] files) throws IOException {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError ->
                    System.out.println("Error in field: '" + fieldError.getField() + "' - " + fieldError.getDefaultMessage()));
            model.addAttribute("productDto", productDto);
            return "backstage/product/addProduct";
        }
        try {
            Product product = productServiceImpl.addProductandImages(productDto, files);
            redirectAttributes.addAttribute("productNo", product.getProductNo());
            redirectAttributes.addFlashAttribute("successMessage", "商品新增成功!");
            return "redirect:/backstage/product/showProducts";

        } catch (IOException | IllegalArgumentException e) {
            model.addAttribute("productDto", productDto);
            model.addAttribute("errorMessage", e.getMessage());
            return "backstage/product/addProduct";
        }
    }

    @GetMapping("/getImage/{productImgNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable("productImgNo") Integer productImgNo) {
        ProductImg img = productImgService.getImgByImgNo(productImgNo);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(img.getProductImg());
    }

    /* ========================== 修改商品 ========================== */

    @GetMapping("updateProduct/{productNo}")
    public String getUpdateProductForm(@PathVariable("productNo") Integer productNo, Model model) {
        Product product = productServiceImpl.getProductByProductNo(productNo);
        ProductDto productDto = ProductMapper.toDto(product);
        model.addAttribute("productDto", productDto);
        model.addAttribute("productNo", productNo);

        // 取商品圖片
        List<ProductImg> productImages = productImgService.getImgByProduct(product.getProductNo());
        model.addAttribute("productImages", productImages);

        return "backstage/product/updateProduct";
    }

    @PostMapping("/update")
    public String updateProduct(@RequestParam("productNo") Integer productNo, ProductDto productDto,
                                @RequestParam(required = false) List<Integer> deleteImages,
                                @RequestParam("productImages") MultipartFile[] files,
                                RedirectAttributes redirectAttributes,
                                BindingResult result) throws IOException {

        List<ProductImg> remainingImages = productImgService.getImgByProduct(productNo);
        // 不需要驗證 !deleteImages.isEmpty()，checkbox沒勾選是null
        if (deleteImages != null) {
            // 移除remainingImages中標記刪除的圖片
            remainingImages.removeIf(image -> deleteImages.contains(image.getProductImgNo()));
            // 刪除資料庫的圖片
            productImgService.deleteImgByImgNos(deleteImages);
            System.out.println("Remaining images count: " + remainingImages.size());
            System.out.println("Is remainingImages empty: " + remainingImages.isEmpty());
        }
        // 檢查是否有上傳圖片
        boolean hasValidImg = false;
        if (files != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    hasValidImg = true;
                    break;
                }
            }
        }
        if (remainingImages.isEmpty() && !hasValidImg) {
            redirectAttributes.addFlashAttribute("errorMessage", "*請上傳至少一張圖片");
            return "redirect:/backstage/product/updateProduct/" + productNo;
        }

        // 處理新上傳的圖片
        if (hasValidImg) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    productImgService.addImage(file, productNo);
                }
            }
        }

        productServiceImpl.updateProduct(productNo, productDto);
        redirectAttributes.addAttribute("productNo", productNo);
        redirectAttributes.addFlashAttribute("successMessage", "商品修改成功!");
        return "redirect:/backstage/product/showProducts";
    }

    private void prepareProductModel(Model model, Product product) {
        List<Product> productsList = productServiceImpl.getProductsByHost(product.getHost());
        Map<Integer, List<ProductImg>> productImages = new HashMap<>();
        for (Product p : productsList) {
            List<ProductImg> imgs = productImgService.getImgByProduct(p.getProductNo());
            productImages.put(p.getProductNo(), imgs);
        }

        model.addAttribute("productsList", productsList);
        model.addAttribute("productImages", productImages);
    }



}