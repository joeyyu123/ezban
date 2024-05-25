package com.ezban.cart.model;

import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private HashOperations<String, String, Object> hashOperations;

    private final String KEY_PREFIX = "cart:member:";
    private final String PRODUCTKEY_PREFIX = "product:";

    public GetCartItemsResponse getCartItemsByMemberNo(Integer memberNo) {
        String key = KEY_PREFIX + memberNo;
        Map<Object, Object> rawMap = redisTemplate.opsForHash().entries(key);
        List<CartItems> cartItems = new ArrayList<>();

        rawMap.forEach((k, v) -> {
            try {
                Integer productNo = Integer.parseInt(((String) k).split(":")[1]);
                Integer quantity = (Integer) v;
                Product product = productRepository.findById(productNo).orElse(null);
                if (product == null) {
                    return;
                }
                Integer inventoryQuantity = product.getRemainingQty();
                // 如果實際庫存量小於購物車數量，更新購物車數量
                if (inventoryQuantity < quantity) {
                    updateCartQty(memberNo, productNo, inventoryQuantity);
                    quantity = inventoryQuantity;
                }
                cartItems.add(new CartItems(product, quantity));
            } catch (NumberFormatException ex) {
                System.err.println("Error parsing product number: " + k);
            }
        });
        return new GetCartItemsResponse(memberNo, cartItems);
    }

    public Integer updateCartQty(Integer memberNo, Integer productNo, Integer quantity) {
        String key = KEY_PREFIX + memberNo;
        String productKey = PRODUCTKEY_PREFIX + productNo;
        hashOperations.put(key, productKey, quantity);
        return (Integer) hashOperations.get(key, productKey);
    }

    public void deleteCartItem(Integer memberNo, Integer productNo) {
        String key = KEY_PREFIX + memberNo;
        String productKey = PRODUCTKEY_PREFIX + productNo;
        hashOperations.delete(key, productKey);
    }

    public Integer addCartItem(Integer memberNo, Integer productNo, Integer quantity) {
        String key = KEY_PREFIX + memberNo;
        String productKey = PRODUCTKEY_PREFIX + productNo;

        // 檢查購物車中是否已經有這個商品
        if (hashOperations.hasKey(key, productKey)) {
            // 商品已存在，更新數量
            Integer currentQty = (Integer) hashOperations.get(key, productKey);
            Integer newQty = currentQty + quantity;
            hashOperations.put(key, productKey, newQty);
            return newQty;
        } else {
            // 商品不存在，新增商品
            hashOperations.put(key, productKey, quantity);
            return (Integer) hashOperations.get(key, productKey);
        }
    }

    public Integer getCartQuantity(Integer memberNo) {
        String cartKey = KEY_PREFIX + memberNo;
        Map<String, Object> cartItems = hashOperations.entries(cartKey);
        return cartItems.size();
    }

    public Map<String, Object> getCartItems(Integer memberNo) {
        String cartKey = KEY_PREFIX + memberNo;
        Map<String, Object> cartItems = hashOperations.entries(cartKey);
        Map<String, Object> processedCartItems = new HashMap<>();
        cartItems.forEach((key, value) -> {
            Integer productNo = Integer.parseInt(key.substring(PRODUCTKEY_PREFIX.length()));
            Product product = productRepository.findById(productNo).orElse(null);
            if (product == null) {
                return;
            }
            Integer quantity = (Integer) value;
            Integer inventoryQuantity = product.getRemainingQty();
            // 如果實際庫存量小於購物車數量，更新購物車數量
            if (inventoryQuantity < quantity) {
                updateCartQty(memberNo, productNo, inventoryQuantity);
                quantity = inventoryQuantity;
            }
            processedCartItems.put( String.valueOf(productNo), quantity);
        });
        return processedCartItems;
    }
}