
// localStorage.removeItem('checkoutProducts');
document.addEventListener('DOMContentLoaded', function() {
    checkUser().then(() => {
        getCartItem();  // 確保user狀態後再取購物車內容
    });
});
/* 從url取會員編號 */
// document.addEventListener('DOMContentLoaded', function () {
//     console.log("Document fully loaded");
//     let url = new URL(window.location.href);
//     let params = new URLSearchParams(url.search);
//     let memberNo = params.get('memberNo');
//
//     if (memberNo) {
//         getCartItems(memberNo);
//     } else {
//         console.error("此商品不存在");
//     }
// });


/* 取購物車內容 */
function getItemsFromServer(memberNo) {
    let url = `/cart/getCartItems?memberNo=${memberNo}`;
    fetch(url)
        .then(response => response.json())
        .then(data => {
            let allItems = data.items.map(item => ({
                product_no: item.product.productNo,
                product_name: item.product.productName,
                product_url: `/product/shopdetail?productNo=${item.product.productNo}`,
                product_img: `/product/getFirstImage/${item.product.productNo}`,
                price: item.product.productPrice,
                quantity: item.quantity,
                subtotal: item.product.productPrice * item.quantity
            }));
            showCartItems(allItems);
        })
        .catch(error => console.error('Error:', error));
}


/* 更新購物車內容 */
function updateQtyServer(productNo, quantity) {
    let url = `/cart/updateQty`;
    let data = {productNo, quantity};

    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error('Error:', error));
}

/* 刪除購物車內容 */
function deleteCartItem(productNo) {
    let url = `/cart/deleteCartItem/${productNo}`;
    let data = {productNo};

    fetch(url, {
        method: 'DELETE'
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                updateCartBadge(data.cartQuantity);
            }
        })
        .catch(error => console.error('Error:', error));
}
function updateCartBadge(newQuantity) {
    let badge = document.querySelector('.cart-icon .badge');
    if (badge) {
        badge.innerText = newQuantity > 99 ? '99+' : newQuantity;
    }
}

/* 顯示購物車內容 */
function showCartItems(items) {
    let container = document.querySelector('.table-content');
    container.innerHTML = '';
    items.forEach(item => {
        let row = `
                <tr>
                   <td class="checkout">
                        <label class="custom-checkbox-label" for="product${item.product_no}">
                            <input type="checkbox" name="select-to-checkout" id="product${item.product_no}" data-product-no="${item.product_no}">
                            <span class="checkmark"></span>
                        </label>
                    </td>
                    <td class="product-thumbnail" style="padding: 15px 8px;">
                        <div class="product-info">
                            <a href="${item.product_url}">
                                <img src="${item.product_img}" alt="product" class="img-responsive" style="width: 80px;">
                            </a>
                            <a href="${item.product_url}">${item.product_name}</a>
                        </div>
                    </td>
                    <td class="product-price">
                        <span class="price">$${item.price}</span>
                    </td>
                    <td class="product-quantity">
                        <div class="quantity">
                            <input type="text" value="${item.quantity}" class="form-control input-sm" name="cart-quantity" data-product-no="${item.product_no}" onchange="inputQtyChange(this)">
                    </td>
                    <td class="product-subtotal" style="padding: 20px;">
                        <span class="amount">$${item.subtotal}</span>
                    </td>
                    <td class="prodcut-remove">
                        <i class="remove bi bi-trash3" data-product-no = "${item.product_no}"></i>
                    </td>
                </tr>
            `;
        container.innerHTML += row;
    });

    // 總計
    let total = items.reduce((acc, item) => acc + item.subtotal, 0);
    let totalRow = `
        <tr>
            <td colspan="4" class="text-right total" style="padding-top: 16px;">總計</td>
            <td class="total" style="padding-top: 16px;">
                <span class="amount">$${total}</span>
            </td>
        </tr>
        `;
    container.innerHTML += totalRow;

    // 初始化 Touchspin
    initializeTouchSpin(userStatus.isLogged);

}

/* 初始化TouchSpin */
function initializeTouchSpin(isLogged) {
    $("input[name='cart-quantity']").TouchSpin({
        min: 1,
        max: 100,
        step: 1,
        decimals: 0
    }).on('touchspin.on.startspin', function () {
        updateSubtotal(this);  // 更新小計
        updateTotal();         // 更新總計

        let productNo = this.getAttribute('data-product-no');
        let quantity = parseInt($(this).val(), 10);

        // 依據 user 狀態更新資料
        if (isLogged) {
            updateQtyServer(productNo, quantity);
        } else {
            updateQtyLocal(productNo, quantity);
        }
    });
}

/* input 標籤數量改變時更新資料 */
function inputQtyChange(input) {
    let productNo = parseInt(input.getAttribute('data-product-no'), 10);
    let quantity = parseInt(input.value, 10);

    updateSubtotal(input);
    updateTotal();

    if (userStatus.isLogged) {
        updateQtyServer(productNo, quantity);
    } else {
        updateQtyLocal(productNo, quantity);
    }
}

/* 數量改變時更新小計 */
function updateSubtotal(input) {
    let quantity = parseInt(input.value);
    let price = parseInt(input.closest('tr').querySelector('.price').innerText.slice(1));
    let subtotal = quantity * price;
    input.closest('tr').querySelector('.product-subtotal .amount').innerText = `$${subtotal}`;
}

/* 數量改變時更新總計 */
function updateTotal() {
    let total = 0;
    document.querySelectorAll('.product-subtotal .amount').forEach(function (span) {
        let subtotal = parseInt(span.innerText.slice(1));
        total += subtotal;
    })
    document.querySelector('.total .amount').innerText = `$${total}`;
}


/* 點擊刪除按鈕，刪除頁面商品 */
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.table-content').addEventListener('click', function (e) {
        if (e.target.classList.contains('remove')) {
            let productNo = parseInt(e.target.getAttribute('data-product-no'), 10);
            let row = e.target.closest('tr');
            if (confirm('要從購物車移除此項目嗎？')) {
                if (userStatus.isLogged) {
                    deleteCartItem(productNo);
                } else {
                    deleteItemLocal(productNo);
                }
                row.remove();
                updateTotal()
            }
        }
    });
});


// 結帳按鈕
function checkLoginAndCheckout() {
    // 取得user勾選要結帳的商品
    let products = [];
    document.querySelectorAll('input[name="select-to-checkout"]:checked').forEach(checkbox => {
        let productNo = parseInt(checkbox.getAttribute('data-product-no'), 10);
        let quantityInput = checkbox.closest('tr').querySelector(`input[name="cart-quantity"]`);
        let quantity = parseInt(quantityInput.value, 10);
        products.push({productNo, quantity});
    });
    if (products.length === 0) {
        alert('請選擇商品');
        return;
    }
    // 將勾選的商品存到localStorage
    localStorage.setItem('checkoutProducts', JSON.stringify(products));

    // 檢查user登入狀態
    if (!userStatus.isLogged) {
        window.location.href = '/cart/login';
        return;
    }

    // 若有勾選商品且登入會員，進入結帳流程
    fetch('/cart/checkout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({products: products})
    })
        .then(response => response.json())
        .then(data => {
            if (data.redirectUrl) {
                // 根據後端回傳的重導URL重導頁面
                window.location.href = data.redirectUrl;
            } else {
                throw new Error('沒有提供重導URL');
            }
        })
        .catch(error => {
            console.error('錯誤訊息:', error);
            alert('結帳過程中發生錯誤，請重試。');
        });
}

// ==========================================================================================

/* checkbox 設定 */
document.addEventListener('DOMContentLoaded', function () {
    // 監聽全選複選框的變化
    const selectAllCheckbox = document.getElementById('select-all');
    selectAllCheckbox.addEventListener('change', function () {
        // 獲取所有商品的複選框
        const itemCheckboxes = document.querySelectorAll('input[name="select-to-checkout"]');
        // 根據全選複選框的狀態設定每個商品複選框的狀態
        itemCheckboxes.forEach(checkbox => {
            checkbox.checked = selectAllCheckbox.checked;
        });
    });


    // 獲取所有商品的複選框
    const itemCheckboxes = document.querySelectorAll('input[name="select-to-checkout"]');
    // 為每個商品複選框添加事件監聽器
    itemCheckboxes.forEach(checkbox => {
        checkbox.addEventListener('change', function () {
            // 如果商品複選框的狀態為未選取，則將全選複選框的狀態設定為未選取
            if (!checkbox.checked) {
                selectAllCheckbox.checked = false;
            }
        });
    });
});