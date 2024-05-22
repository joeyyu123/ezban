package com.ezban.productorder.model;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.birthdaycoupon.model.BirthdayCouponRepository;
import com.ezban.host.model.Host;
import com.ezban.member.model.Member;
import com.ezban.member.model.MemberRepository;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import com.ezban.productorderdetail.model.AddProductOrderDetailDTO;
import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.productorderdetail.model.ProductOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductOrderService {

    @Autowired
    ProductOrderRepository repository;

    @Autowired
    BirthdayCouponRepository birthdayCouponRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductOrderDetailRepository productOrderDetailRepository;

    @Autowired
    MemberRepository memberRepository;

    // 新增訂單
    public void addProductOrder(@Valid AddProductOrderDTO addProductOrderDTO) {

        // 獲取購買的商品明細列表
        List<AddProductOrderDetailDTO> productOrderDetailList = addProductOrderDTO.getProductOrderDetail();
        // 創建一個Map，用於將商品明細按照廠商分類
        Map<Host, List<AddProductOrderDetailDTO>> productDetailMap = new HashMap<>();
        // 查詢所有涉及的產品並按照廠商分類
        for (AddProductOrderDetailDTO orderDetailDTO : productOrderDetailList) {

            Optional<Product> productOptional = productRepository.findByProductNo(Integer.valueOf(orderDetailDTO.getProductNo()));

            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                Host host = product.getHost();
                productDetailMap.computeIfAbsent(host, k -> new ArrayList<>()).add(orderDetailDTO);
            } else {
                throw new NoSuchElementException("未找到產品，產品編號: " + orderDetailDTO.getProductNo());
            }

        }

        // 商品明細按照廠商分類完後，分別創建各自的訂單
        for (Map.Entry<Host, List<AddProductOrderDetailDTO>> entry : productDetailMap.entrySet()) {

            List<AddProductOrderDetailDTO> hostProductOrderDetails = entry.getValue();
            ProductOrder productOrder = new ProductOrder();

            // 轉換 DTO 中的會員編號
            String memberNoString = addProductOrderDTO.getMemberNo();
            Integer memberNo;
            try {
                memberNo = Integer.valueOf(memberNoString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("無效的會員編號: " + memberNoString, e);
            }

            Optional<Member> memberOptional = memberRepository.findById(memberNo);
            if (memberOptional.isPresent()) {
                Member member = memberOptional.get();
                productOrder.setMember(member);
            } else {
                throw new NoSuchElementException("未找到會員，會員編號: " + memberNo);
            }

            try {
                // 轉換並設置 productPrice
                Integer productPrice = Integer.valueOf(addProductOrderDTO.getProductPrice());
                productOrder.setProductPrice(productPrice);

                // 轉換並設置 memberPoints
                if (addProductOrderDTO.getMemberPoints() != null) {
                    Integer memberPoints = Integer.valueOf(addProductOrderDTO.getMemberPoints());
                    productOrder.setMemberPoints(memberPoints);
                }

                // 查詢並設置生日優惠券
                if (addProductOrderDTO.getBirthdayCouponNo() != null) {

                    String birthdayCouponNoString = addProductOrderDTO.getBirthdayCouponNo();
                    Integer birthdayCouponNo;
                    try {
                        birthdayCouponNo = Integer.valueOf(birthdayCouponNoString);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("無效的生日優惠券編號: " + birthdayCouponNoString, e);
                    }
                    Optional<BirthdayCoupon> birthdayCouponOptional = birthdayCouponRepository.findById(birthdayCouponNo);
                    if (birthdayCouponOptional.isPresent()) {
                        BirthdayCoupon birthdayCoupon = birthdayCouponOptional.get();
                        productOrder.setBirthdayCoupon(birthdayCoupon);
                    } else {
                        throw new NoSuchElementException("未找到生日優惠券，編號: " + birthdayCouponNo);
                    }

                }

                // 轉換並設置 productCouponDiscount
                if (addProductOrderDTO.getProductCouponDiscount() != null) {
                    Integer productCouponDiscount = Integer.valueOf(addProductOrderDTO.getProductCouponDiscount());
                    productOrder.setProductCouponDiscount(productCouponDiscount);
                }

                // 轉換並設置 productCheckoutAmount
                Integer productCheckoutAmount = Integer.valueOf(addProductOrderDTO.getProductCheckoutAmount());
                productOrder.setProductCheckoutAmount(productCheckoutAmount);

                // 其他
                productOrder.setRecipient(addProductOrderDTO.getRecipient());
                productOrder.setRecipientPhone(addProductOrderDTO.getRecipientPhone());
                productOrder.setRecipientAddress(addProductOrderDTO.getRecipientAddress());
                productOrder.setProductPaymentStatus((byte) 0);
                productOrder.setProductProcessStatus((byte) 0);
                productOrder.setProductOrderAllocationAmount(0);
                productOrder.setProductOrderAllocationStatus((byte) 0);

                // 處理訂單明細
                List<ProductOrderDetail> orderDetails = new ArrayList<>();
                for (AddProductOrderDetailDTO orderDetailDTO : hostProductOrderDetails) {

                    ProductOrderDetail orderDetail = new ProductOrderDetail();
                    // 將訂單設置為廠商各自明細的訂單
                    orderDetail.setProductOrder(productOrder);
                    // 查詢產品
                    Product product = productRepository.findByProductNo(Integer.valueOf(orderDetailDTO.getProductNo())).orElseThrow(
                            () -> new NoSuchElementException("未找到產品，產品編號: " + orderDetailDTO.getProductNo())
                    );
                    // 設置產品
                    orderDetail.setProduct(product);
                    // 設置數量和價格及評論狀態預設為0(未評論)
                    orderDetail.setProductQty(orderDetailDTO.getProductQty());
                    orderDetail.setProductPrice(orderDetailDTO.getProductPrice());
                    orderDetail.setCommentsStatus((byte) 0);
                    // 將訂單明細添加到列表中
                    orderDetails.add(orderDetail);

                }

                // 將商品訂單明細設置到商品訂單中
                productOrder.setProductOrderDetail(orderDetails);
                // 保存訂單
                repository.save(productOrder);

            } catch (NumberFormatException e) {
                // 處理轉換異常，例如記錄日誌或拋出自定義異常
                throw new IllegalArgumentException("無效的數值格式", e);
            }

        }

    }


    /*
    訂單狀態別:
    ProductPaymentStatus:
    0:已付款
    1:已退款
    =======================================
    ProductProcessStatus:
    0:備貨中
    1:已取消
    2:已出貨
    3:已退貨
    4:已結案
    =======================================
    ProductOrderAllocationStatus:
    0:未撥款
    1:已撥款
    =======================================
    */


    // 員工更新訂單內容
    public void updateProductOrder(UpdateProductOrderDTO updateProductOrderDTO) {

        Optional<ProductOrder> optionalProductOrder = repository.findById(updateProductOrderDTO.getProductOrderNo());
        if (optionalProductOrder.isPresent()) {

            ProductOrder productOrder = optionalProductOrder.get();
            // 比對更新前後的訂單資訊，確認是否有修改
            boolean hasChanges =
                            (productOrder.getProductPaymentStatus() == null && updateProductOrderDTO.getProductPaymentStatus() != null) ||
                            (productOrder.getProductPaymentStatus() != null && !productOrder.getProductPaymentStatus().equals(updateProductOrderDTO.getProductPaymentStatus())) ||
                            (productOrder.getProductProcessStatus() == null && updateProductOrderDTO.getProductProcessStatus() != null) ||
                            (productOrder.getProductProcessStatus() != null && !productOrder.getProductProcessStatus().equals(updateProductOrderDTO.getProductProcessStatus())) ||
                            (productOrder.getProductOrderAllocationStatus() == null && updateProductOrderDTO.getProductOrderAllocationStatus() != null) ||
                            (productOrder.getProductOrderAllocationStatus() != null && !productOrder.getProductOrderAllocationStatus().equals(updateProductOrderDTO.getProductOrderAllocationStatus()));
            // 更新
            if (hasChanges) {

                // 如果ProductProcessStatus 狀態改為 4(已結案)，則設置訂單結案時間為當前系統時間
                productOrder.setProductPaymentStatus(updateProductOrderDTO.getProductPaymentStatus());
                productOrder.setProductOrderAllocationStatus(updateProductOrderDTO.getProductOrderAllocationStatus());
                productOrder.setProductProcessStatus(updateProductOrderDTO.getProductProcessStatus());
                if (productOrder.getProductProcessStatus() == 4) {
                    productOrder.setOrderClosedate(Timestamp.valueOf(LocalDateTime.now()));
                }

                repository.save(productOrder);

            } else {
                throw new RuntimeException("* 若無更改內容，請點擊取消更新按鈕 ！");
            }
        } else {
            throw new RuntimeException("* 查無訂單可進行修改 ！");
        }

    }


    // 顯示單筆訂單資料
    public ProductOrder getOneProductOrder(Integer productOrderNo) {
        Optional<ProductOrder> optional = repository.findById(productOrderNo);
        return optional.orElse(null);
    }


    // 顯示所有訂單資料
    public List<ProductOrder> getAll() {
        return repository.findAll();
    }


    public ProductOrder findById(Integer id) {
        return repository.findById(id).orElseThrow();
    }


    // 會員查詢自己的訂單
    public List<ProductOrder> findByMember(Integer memberNo) {
        return repository.findByMember(memberNo);
    }


    // 廠商查詢自己的訂單
    public List<ProductOrder> findByHost(Integer hostNo) {
        return repository.findByHost(hostNo);
    }


    // 會員取消訂單
    public void cancelProductOrderDTO(CancelProductOrderDTO cancelProductOrderDTO) {

        Optional<ProductOrder> optionalProductOrder = repository.findById(cancelProductOrderDTO.getProductOrderNo());
        if (optionalProductOrder.isPresent()) {

            ProductOrder productOrder = optionalProductOrder.get();
            // 更新訂單內容
            if (isCancelAllowed(productOrder)) {

                productOrder.setProductOrderNo(cancelProductOrderDTO.getProductOrderNo());
                productOrder.setProductProcessStatus((byte) 1);
                //若會員取消訂單，則回補庫存數量
                for (ProductOrderDetail productOrderDetail : productOrder.getProductOrderDetail()) {

                    int canceledQuantity = productOrderDetail.getProductQty();
                    Product product = productOrderDetail.getProduct();
                    int currentStock = product.getRemainingQty();
                    //現有庫存+商品取消數量=新的庫存量
                    int updatedStock = currentStock + canceledQuantity;
                    product.setRemainingQty(updatedStock);

                }
                // 保存更新後的訂單可一併回補商品數量
                repository.save(productOrder);

            } else {
                throw new RuntimeException("* 訂單已完成備貨處理無法取消訂單，如有需要請洽客服人員 ！");
            }

        }

    }


    // 會員取消訂單的條件判斷
    private boolean isCancelAllowed(ProductOrder productOrder) {

        // 假設訂單狀態為 0 表示備貨中，會員可以取消訂單
        return productOrder.getProductProcessStatus() == 0;
    }


    // 廠商更新訂單內容
    public void updateProductOrderByHost(UpdateProductOrderByHostDTO updateProductOrderByHostDTO) {

        Optional<ProductOrder> optionalProductOrder = repository.findById(updateProductOrderByHostDTO.getProductOrderNo());
        if (optionalProductOrder.isPresent()) {

            ProductOrder productOrder = optionalProductOrder.get();
            // 比對更新前後的訂單資訊，確認是否有修改
            boolean hasChanges =
                            (productOrder.getRecipient() == null && updateProductOrderByHostDTO.getRecipient() != null) ||
                            (productOrder.getRecipient() != null && !productOrder.getRecipient().equals(updateProductOrderByHostDTO.getRecipient())) ||
                            (productOrder.getRecipientPhone() == null && updateProductOrderByHostDTO.getRecipientPhone() != null) ||
                            (productOrder.getRecipientPhone() != null && !productOrder.getRecipientPhone().equals(updateProductOrderByHostDTO.getRecipientPhone())) ||
                            (productOrder.getRecipientAddress() == null && updateProductOrderByHostDTO.getRecipientAddress() != null) ||
                            (productOrder.getRecipientAddress() != null && !productOrder.getRecipientAddress().equals(updateProductOrderByHostDTO.getRecipientAddress())) ||
                            (productOrder.getProductProcessStatus() == null && updateProductOrderByHostDTO.getProductProcessStatus() != null) ||
                            (productOrder.getProductProcessStatus() != null && !productOrder.getProductProcessStatus().equals(updateProductOrderByHostDTO.getProductProcessStatus()));
            // 更新
            if (hasChanges) {

                productOrder.setRecipient(updateProductOrderByHostDTO.getRecipient());
                productOrder.setRecipientPhone(updateProductOrderByHostDTO.getRecipientPhone());
                productOrder.setRecipientAddress(updateProductOrderByHostDTO.getRecipientAddress());
                productOrder.setProductProcessStatus(updateProductOrderByHostDTO.getProductProcessStatus());

                repository.save(productOrder);

            } else {
                // 若沒有修改內容，跳出查無修正內容紀錄的訊息
                throw new RuntimeException("* 若無更改內容，請點擊取消更新 ！");
            }

        }

    }
}