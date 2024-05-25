package com.ezban.cart.controller;

import com.ezban.birthdaycoupon.model.BirthdayCouponResponse;
import com.ezban.birthdaycoupon.model.BirthdayCouponService;
import com.ezban.cart.model.*;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
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

//    @PostMapping("/checkUser")
//    public ResponseEntity<?> checkUser(HttpSession session) {
//        Integer memberNo = (Integer) session.getAttribute("memberNo");
//        if (memberNo != null) {
//            // 使用者已登入，return memberNo
//            return ResponseEntity.ok().body("{\"isLogged\": true, \"memberNo\": " + memberNo + "}");
//        } else {
//            return ResponseEntity.ok().body("{\"isLogged\": false}");
//        }
//    }

//    @PostMapping("/checkout")
//    public ResponseEntity<?> processCheckout(
//            Principal principal,
//            @RequestBody CheckoutRequest checkoutRequest,
//            Model model
//    ) {
//        Map<Integer, Integer> products = checkoutRequest.getProducts();
//
////        if (products == null || products.isEmpty()) {
////            return ResponseEntity.badRequest().body("沒有選擇商品");
////        }
//
//        model.addAttribute("products", products);
//
//        return ResponseEntity.ok(Collections.singletonMap("redirectUrl", "/cart/checkoutPage"));
//    }

    @PostMapping("/checkoutPage")
    public String checkOutPage(Principal principal,
                               Model model,
                               @RequestBody CheckoutRequest checkoutRequest
    ) {
//        Integer memberNo = Integer.parseInt(principal.getName());

        model.addAttribute("products", checkoutRequest.getProducts());
        return "frontstage/product/checkout";
    }

    @GetMapping("/getMemberInfo")
    public ResponseEntity<?> getMemberInfo(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());
        Member member = memberService.getMemberById(memberNo);
        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<GetCartItemsResponse> getCartItems(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());
        GetCartItemsResponse items = cartService.getCartItemsByMemberNo(memberNo);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(items);
    }

    @GetMapping("/getValidCoupon")
    public ResponseEntity<?> getValidCoupon(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());
        BirthdayCouponResponse coupon = birthdayCouponService.getValidCoupon(memberNo);
        return ResponseEntity.ok().body(Objects.requireNonNullElse(coupon, "{\"message\": \"無適用優惠券\"}"));
    }

    // 以直接更新總數量的方式增加商品數量
    @PutMapping("/updateQty")
    public ResponseEntity<String> updateQty(Principal principal,
                                            @RequestBody UpdateQtyRequest updateQtyRequest) {
        Integer memberNo = Integer.parseInt(principal.getName());
        Integer updatedQuantity = cartService.updateCartQty(memberNo, updateQtyRequest.getProductNo(), updateQtyRequest.getQuantity());
        if (updatedQuantity == null) {
            return ResponseEntity.internalServerError().body("更新購物車數量失敗。");
        }
        return ResponseEntity.ok(updatedQuantity.toString());
    }

    @DeleteMapping("/deleteCartItem/{productNo}")
    public ResponseEntity<?> deleteCartItem(Principal principal,
                                            @PathVariable Integer productNo) {
        Integer memberNo = Integer.parseInt(principal.getName());
        cartService.deleteCartItem(memberNo, productNo);
        Integer cartQuantity = cartService.getCartQuantity(memberNo);
        return ResponseEntity.ok().body("{\"success\": true, \"message\": \"商品已從購物車移除\", \"cartQuantity\": " + cartQuantity + "}");
    }

    // 以遞增delta的方式增加商品數量
    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(Principal principal,
                                       @RequestBody AddToCartRequest addToCartRequest) {
        Integer memberNo = Integer.parseInt(principal.getName());

        Integer result = cartService.addCartItem(memberNo, addToCartRequest.getProductNo(), addToCartRequest.getQuantity());
        if (result != null && result > 0) {
            // 獲取購物車的商品數量
            Integer cartQuantity = cartService.getCartQuantity(memberNo);
            return ResponseEntity.ok().body("{\"success\": true, \"message\": \"成功加入商品到購物車\", \"cartQuantity\": " + cartQuantity + "}");
        } else {
            return ResponseEntity.ok().body("{\"success\": false, \"message\": \"無法加入購物車\"}");
        }
    }

    /**
     * 取得會員購物車數量以顯示在 badge
     */
    @GetMapping("/getCartQuantity")
    public ResponseEntity<?> getCartQuantity(Principal principal) {
        Integer memberNo = Integer.parseInt(principal.getName());

        Map<String, Object> cartItems = cartService.getCartItems(memberNo);
        return ResponseEntity.ok().body(cartItems);
    }
}
