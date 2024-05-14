package com.ezban.productimg.controller;

import com.ezban.product.model.ProductServiceImpl;
import com.ezban.productimg.model.ProductImg;
import com.ezban.productimg.model.ProductImgRepository;
import com.ezban.productimg.model.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductImgController {

    @Autowired
    private ProductImgService productImgService;

    @Autowired
    private ProductServiceImpl productServiceImpl;
    @Autowired
    private ProductImgRepository productImgRepository;

    @GetMapping("/uploadImg")
    public String uploadForm() {
        return "uploadImg";
    }

    @PostMapping("/upload")
    public String uploadProductImage(@RequestParam("images") MultipartFile[] images,
//                                     @RequestParam("productNo") Integer productNo,
                                     RedirectAttributes redirectAttributes) {
        try {
            // Product product = productServiceImpl.getOneProduct(productNo);
            Integer productNo = 19;
            List<Integer> productImgNos = new ArrayList<>();
            for (MultipartFile image: images) {
                Integer productImgNo = productImgService.addImage(image, productNo);
                productImgNos.add(productImgNo);

            }
            redirectAttributes.addFlashAttribute("message", "Image uploaded successfully!");
            redirectAttributes.addFlashAttribute("productImgNos", productImgNos);
            return "redirect:/showImage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to upload image: " + e.getMessage());
            return "redirect:/uploadImg";
        }
    }

    @GetMapping("/showImage")
    public String showImage(@ModelAttribute("productImgNos") List<Integer> productImgNos, Model model) {

        List<ProductImg> productImgs = productImgNos.stream()
                .map(productImgNo -> productImgRepository.findById(productImgNo).orElse(null))
                .collect(Collectors.toList());
        model.addAttribute("images", productImgs);
        return "showImage";
    }


    @GetMapping("/getImage/{productImgNo}")
    public ResponseEntity<?> getImage(@PathVariable Integer productImgNo) {
        try {
            ProductImg productImg = productImgRepository.findById(productImgNo)
                    .orElseThrow(() -> new RuntimeException("Image not found"));
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("image/jpeg"))
                    .body(new ByteArrayResource(productImg.getProductImg()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
