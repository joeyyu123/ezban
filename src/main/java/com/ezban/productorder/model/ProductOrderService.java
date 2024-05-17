package com.ezban.productorder.model;

import com.ezban.birthdaycoupon.model.BirthdayCoupon;
import com.ezban.birthdaycoupon.model.BirthdayCouponRepository;
import com.ezban.product.model.Product;
import com.ezban.product.model.ProductRepository;
import com.ezban.productorderdetail.model.ProductOrderDetail;
import com.ezban.productorderdetail.model.ProductOrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    // 新增訂單
    public void addProductOrder(@Valid AddProductOrderDTO addProductOrderDTO) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setProductOrderNo(addProductOrderDTO.getProductOrderNo());
        productOrder.setMember(addProductOrderDTO.getMember());
        productOrder.setProductPrice(addProductOrderDTO.getProductPrice());
        productOrder.setMemberPoints(addProductOrderDTO.getMemberPoints());
        BirthdayCoupon birthdayCoupon = birthdayCouponRepository.findById(addProductOrderDTO.getBirthdayCouponNo()).get();
        productOrder.setBirthdayCoupon(birthdayCoupon);
        productOrder.setProductCouponDiscount(addProductOrderDTO.getProductCouponDiscount());
        productOrder.setProductCheckoutAmount(addProductOrderDTO.getProductCheckoutAmount());
        productOrder.setRecipient(addProductOrderDTO.getRecipient());
        productOrder.setRecipientPhone(addProductOrderDTO.getRecipientPhone());
        productOrder.setRecipientAddress(addProductOrderDTO.getRecipientAddress());
        productOrder.setProductPaymentStatus((byte) 0);
        productOrder.setProductProcessStatus((byte) 0);
        productOrder.setProductOrderAllocationAmount(addProductOrderDTO.getProductOrderAllocationAmount());
        productOrder.setProductOrderAllocationStatus((byte) 0);
        repository.save(productOrder);
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
                if (productOrder.getProductProcessStatus() == 4) {
                    productOrder.setOrderClosedate(Timestamp.valueOf(LocalDateTime.now()));
                }
                productOrder.setProductPaymentStatus(updateProductOrderDTO.getProductPaymentStatus());
                productOrder.setProductOrderAllocationStatus(updateProductOrderDTO.getProductOrderAllocationStatus());
                productOrder.setProductProcessStatus(updateProductOrderDTO.getProductProcessStatus());

                repository.save(productOrder);

            } else {
                throw new RuntimeException("* 若無更改內容，請點擊取消修改按鈕 ！");
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
                for (ProductOrderDetail productOrderDetail : productOrder.getProductOrderDetails()) {
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
                throw new RuntimeException("* 若無更改內容，請點擊取消修改 ！");
            }
        }
    }

}