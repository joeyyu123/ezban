package com.ezban.cart.controller;

import com.ezban.birthdaycoupon.model.BirthdayCouponResponse;
import com.ezban.birthdaycoupon.model.BirthdayCouponService;
import com.ezban.cart.model.*;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BirthdayCouponService birthdayCouponService;


    @GetMapping("/items")
    public String cartPage() {
        return "frontstage/product/cart";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "frontstage/login";
    }

    @PostMapping("/checkUser")
    public ResponseEntity<?> checkUser(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");
        if (memberNo != null) {
            // 使用者已登入，return memberNo
            return ResponseEntity.ok().body("{\"isLogged\": true, \"memberNo\": " + memberNo + "}");
        } else {
            return ResponseEntity.ok().body("{\"isLogged\": false}");
        }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> processCheckout(@RequestBody CheckoutRequest checkoutRequest) {
        List<CheckoutRequest.ProductInfo> products = checkoutRequest.getProducts();
        if (products == null || products.isEmpty()) {
            return ResponseEntity.badRequest().body("沒有選擇商品");
        }

        return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/cart/checkoutPage"));
    }

    @GetMapping("/checkoutPage")
    public String checkOutPage() {
        return "frontstage/product/checkout";
    }

    @GetMapping("/getMemberInfo")
    public ResponseEntity<?> getMemberInfo(HttpSession session) {
//        session.setAttribute("memberNo", 2);
        Integer memberNo = (Integer) session.getAttribute("memberNo");
        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"使用者未登入\"}");
        }
        Member member = memberService.getMemberById(memberNo);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<GetCartItemsResponse> getCartItems(@RequestParam("memberNo") Integer memberNo) {
        GetCartItemsResponse items = cartService.getCartItemsByMemberNo(memberNo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(items);
    }

    @GetMapping("/getValidCoupon")
    public ResponseEntity<?> getValidCoupon(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");
        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"使用者未登入\"}");
        }

        BirthdayCouponResponse coupon = birthdayCouponService.getValidCoupon(memberNo);
        return ResponseEntity.ok().body(Objects.requireNonNullElse(coupon, "{\"message\": \"無適用優惠券\"}"));
    }

    @PutMapping("/updateQty")
    public ResponseEntity<String> updateQty(HttpSession session, @RequestBody UpdateQtyRequest updateQtyRequest) {
//        session.setAttribute("memberNo", 1);
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        Integer updatedQuantity = cartService.updateCartQty(memberNo, updateQtyRequest.getProductNo(), updateQtyRequest.getQuantity());
        if (updatedQuantity == null) {
            return ResponseEntity.internalServerError().body("更新數量失敗");
        }
        return ResponseEntity.ok(updatedQuantity.toString());
    }

    @DeleteMapping("/deleteCartItem/{productNo}")
    public ResponseEntity<?> deleteCartItem(HttpSession session, @PathVariable Integer productNo) {
//        session.setAttribute("memberNo", 1);
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        cartService.deleteCartItem(memberNo, productNo);
        Integer cartQuantity = cartService.getCartQuantity(memberNo);
        return ResponseEntity.ok().body("{\"success\": true, \"message\": \"商品已從購物車移除\", \"cartQuantity\": " + cartQuantity + "}");
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(HttpSession session, @RequestBody AddToCartRequest addToCartRequest) {
//        session.setAttribute("memberNo", 1);
        Integer memberNo = (Integer) session.getAttribute("memberNo");

        Integer result = cartService.addCartItem(memberNo, addToCartRequest.getProductNo(), addToCartRequest.getQuantity());

        if (result != null && result > 0) {
            // 獲取購物車的商品數量
            Integer cartQuantity = cartService.getCartQuantity(memberNo);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"成功加入商品到購物車\", \"cartQuantity\": " + cartQuantity + "}");
        } else {
            return ResponseEntity.ok().body("{\"success\": false, \"message\": \"無法加入購物車\"}");
        }
    }

    @GetMapping("/getCartQuantity")
    public ResponseEntity<?> getCartQuantity(HttpSession session) {
        Integer memberNo = (Integer) session.getAttribute("memberNo");
        if (memberNo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"User not logged in.\"}");
        }
        Integer cartQuantity = cartService.getCartQuantity(memberNo);
        return ResponseEntity.ok().body("{\"cartQuantity\": " + cartQuantity + "}");
    }
}
