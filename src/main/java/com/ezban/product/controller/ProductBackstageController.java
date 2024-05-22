package com.ezban.product.controller;

import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductDto;
import com.ezban.product.model.ProductMapper;
import com.ezban.product.model.ProductServiceImpl;
import com.ezban.productimg.model.ProductImg;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/backstage")
public class ProductBackstageController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private HostService hostService;

    /**
     * 該廠商所有商品頁面
     */
    @GetMapping("/products")
    public String products(Principal principal, Model model) {
        Host host = hostService.findHostByHostNo(principal.getName()).orElseThrow();

        List<Product> productsList = productServiceImpl.getProductsByHost(host);
        Map<Integer, List<ProductImg>> productImages = new HashMap<>();
        for (Product p : productsList) {
            List<ProductImg> imgs = productImgService.getImgByProduct(p.getProductNo());
            productImages.put(p.getProductNo(), imgs);
        }
        model.addAttribute("productsList", productsList);
        model.addAttribute("productImages", productImages);
        return "backstage/product/allProducts";
    }

    /**
     * 新增商品頁面
     */
    @GetMapping("/product/addProduct")
    public String getAddProductForm(Principal principal, Model model) {
        Integer hostNo = Integer.parseInt(principal.getName());
        ProductDto productDto = new ProductDto();
        productDto.setHostNo(hostNo);
        model.addAttribute("productDto", productDto);
        return "backstage/product/addProduct";
    }

    /**
     * 新增商品
     *
     * @param principal  使用者
     * @param productDto 商品資料
     * @param files      商品圖片
     */
    @PostMapping("/product/addProduct")
    public String insert(Principal principal,
                         ProductDto productDto,
                         Model model,
                         @RequestParam("images") MultipartFile[] files) throws IOException {

        if (!productServiceImpl.isAuthenticated(principal, productDto)) {
            return "redirect:/backstage/product/addProduct";
        }

        try {
            productServiceImpl.addProductandImages(productDto, files);
            return "redirect:/backstage/product/addProduct?success=true";

        } catch (IOException | IllegalArgumentException e) {
            model.addAttribute("productDto", productDto);
            model.addAttribute("errorMessage", e.getMessage());
            return "backstage/product/addProduct";
        }
    }

    @GetMapping("/product/getImage/{productImgNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable("productImgNo") Integer productImgNo) {
        ProductImg img = productImgService.getImgByImgNo(productImgNo);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(img.getProductImg());
    }


    /**
     * 修改商品頁面
     */
    @GetMapping("/product/{productNo}/edit")
    public String getUpdateProductForm(Principal principal,
                                       @PathVariable("productNo") Integer productNo,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        Product product = productServiceImpl.getProductByProductNo(productNo);
        if (product == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "商品不存在");
            return "redirect:/backstage/products";
        }
        ProductDto productDto = ProductMapper.toDto(product);
        List<ProductImg> productImages = productImgService.getImgByProduct(product.getProductNo());

        // 檢查使用者是否有權修改此商品
        if (!productServiceImpl.isAuthenticated(principal, productDto)) {
            redirectAttributes.addFlashAttribute("errorMessage", "您沒有權限修改此商品");
            return "redirect:/backstage/products";
        }

        model.addAttribute("productDto", productDto);
        model.addAttribute("productNo", productNo);
        model.addAttribute("productImages", productImages);

        return "backstage/product/updateProduct";
    }

    /**
     * 修改商品
     *
     * @param productDto   商品資料
     * @param deleteImages 要刪除的圖片編號
     * @param files        新增的圖片
     *
     */
    @PostMapping("/product/update")
    public String update(@RequestParam(required = false) List<Integer> deleteImages,
                         @RequestParam("productImages") MultipartFile[] files,
                         ProductDto productDto,
                         RedirectAttributes redirectAttributes) throws IOException {
        Integer productNo = productDto.getProductNo();
        try {
            productServiceImpl.updateProductAndImages(productDto, deleteImages, files);
            return "redirect:/backstage/product/" + productNo + "/edit?success=true";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/backstage/product/" + productNo + "/edit";
        }
    }

}