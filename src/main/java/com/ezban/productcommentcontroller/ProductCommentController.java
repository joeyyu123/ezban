package com.ezban.productcommentcontroller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;
import com.ezban.productcomment.model.ProductComment;
import com.ezban.productcomment.model.ProductCommentDTO;
import com.ezban.productcomment.model.ProductCommentService;
import com.ezban.productorder.model.ProductOrder;
import com.ezban.productorder.model.ProductOrderService;
import com.ezban.productorderdetail.model.ProductOrderDetail;

@RestController
@RequestMapping("/api/product/comment")
public class ProductCommentController {

    private final ProductCommentService commentService;
    private final ProductOrderService orderService;

    @Autowired
    public ProductCommentController(ProductCommentService commentService, ProductOrderService orderService) {
        this.commentService = commentService;
        this.orderService = orderService;
    }

    @PostMapping(consumes = "application/json;charset=UTF-8")
    public ResponseEntity<?> addComment(@RequestBody ProductCommentDTO commentDTO, Principal principal) {
        System.out.println("Received POST request to add comment");
        try {
            Integer memberNo = Integer.parseInt(principal.getName());
            Member member = new Member(memberNo);
            Product product = new Product(commentDTO.getProductNo());

            ProductComment comment = new ProductComment(commentDTO, member, product);
            ProductComment savedComment = commentService.save(comment);

            return ResponseEntity.status(HttpStatus.CREATED).body(new ProductCommentDTO(savedComment));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid member number format: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving comment: " + e.getMessage());
        }
    }

    @GetMapping("/{productNo}")
    public ResponseEntity<?> getCommentsByProductNo(@PathVariable Integer productNo) {
        try {
            List<ProductCommentDTO> comments = commentService.findCommentsByProductNo(productNo);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching comments: " + e.getMessage());
        }
    }

    @GetMapping("/stats/{productNo}")
    public ResponseEntity<?> getCommentStats(@PathVariable Integer productNo) {
        try {
            ProductCommentDTO.CommentStatsDTO stats = commentService.getCommentStats(productNo);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching comment stats: " + e.getMessage());
        }
    }

    @GetMapping("/order/{orderNo}")
    public ResponseEntity<?> getProductAndMemberInfoByOrderNo(@PathVariable Integer orderNo, Principal principal) {
        try {
            ProductOrder order = orderService.findById(orderNo);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }

            ProductOrderDetail orderDetail = order.getProductOrderDetail().get(0);
            Integer productNo = orderDetail.getProduct().getProductNo();

            Integer memberNo = Integer.parseInt(principal.getName());

            return ResponseEntity.ok().body(new ProductCommentDTO.ProductAndMemberDTO(productNo, memberNo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving product and member info: " + e.getMessage());
        }
    }
}
