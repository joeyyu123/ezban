package com.ezban.product.controller;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductServiceImpl;
import com.ezban.productcategory.model.ProductCategory;
import com.ezban.productcategory.model.ProductCategoryService;
import com.ezban.productcomment.model.ProductCommentDTO;
import com.ezban.productcomment.model.ProductCommentService;
import com.ezban.productimg.model.ProductImg;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/product")
public class ProductFrontstageController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCommentService productCommentService;

    @GetMapping("/shopall")
    public String shopall() {
        return "frontstage/product/shopall";
    }

    @GetMapping("/shopdetail")
    public String shopdetail(@RequestParam(name = "productNo", required = true) String productNo, Model model) {
        model.addAttribute("productNo", productNo);
        return "frontstage/product/shopdetail";
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productServiceImpl.getAll();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    @GetMapping("/getProductDetail")
    public ResponseEntity<Product> getProductDetail(@RequestParam("productNo") Integer productNo) {
        Product product = productServiceImpl.getProductByProductNo(productNo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(product);
    }

    @GetMapping("/getFirstImage/{productNo}")
    public ResponseEntity<byte[]> getFirstImage(@PathVariable("productNo") Integer productNo) {
        ProductImg img = productImgService.getFirstProductImg(productNo);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(img.getProductImg());
    }

    @GetMapping("/getProductImages")
    public ResponseEntity<List<String>> getProductImages(@RequestParam("productNo") Integer productNo) {
        List<ProductImg> productImgs = productImgService.getImgByProduct(productNo);
        List<String> imgUrls = productImgs.stream()
                .map(ProductImg::getImageUrl)
                .collect(Collectors.toList());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(imgUrls);
    }

    @GetMapping("/getImage/{productImgNo}")
    public ResponseEntity<byte[]> getImage(@PathVariable("productImgNo") Integer productImgNo) {
        ProductImg img = productImgService.getImgByImgNo(productImgNo);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(img.getProductImg());
    }

    @GetMapping("/getAllProductCategories")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories(){
        List<ProductCategory> productCategories = productCategoryService.getAllProductCategories();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productCategories);
    }

    @GetMapping("/getProductsByCategory")
    public ResponseEntity<List<Product>> getProductsByCategory(@RequestParam("productCategoryNo") Integer productCategoryNo){
        List<Product> products = productServiceImpl.getProductsByProductCategoryNo(productCategoryNo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    @GetMapping("/related")
    public ResponseEntity<List<Product>> getProductsByHostAndCategory(@RequestParam("productNo") Integer productNo,
                                                                      @RequestParam("hostNo") Integer hostNo,
                                                                      @RequestParam("productCategoryNo") Integer productCategoryNo) {
        List<Product> products = productServiceImpl.getProductsByHostandCategory(hostNo, productCategoryNo, productNo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(products);
    }

    @GetMapping("/{productNo}/comments")
    public ResponseEntity<List<ProductCommentDTO>> getCommemtsByProductNo(@PathVariable Integer productNo) {
        try {
            List<ProductCommentDTO> comments = productCommentService.findCommentsByProductNo(productNo);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("member/{memberNo}")
    public ResponseEntity<Member> getMember(@PathVariable Integer memberNo) {
        Member member = productServiceImpl.getMemberByMemberNo(memberNo);
        return ResponseEntity.ok(member);
    }
}
