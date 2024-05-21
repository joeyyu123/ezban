// let userStatus = {
//     isLogged: false,
//     memberNo: null
// };
//
// function checkUser() {
//     return fetch('/cart/checkUser', {
//         method: 'POST',
//         credentials: 'include'  // 確保session資訊在fetch中傳遞
//     })
//         .then(response => response.json())
//         .then(data => {
//             if (data.isLogged) {
//                 userStatus.isLogged = true;
//                 userStatus.memberNo = data.memberNo;
//             } else {
//                 userStatus.isLogged = false;
//                 userStatus.memberNo = null;
//             }
//             return userStatus;
//         })
//         .catch(error => {
//             console.error('檢查使用者狀態失敗:', error);
//             userStatus.isLogged = false;
//             userStatus.memberNo = null;
//             return userStatus;
//         });
// }

/* 在頁面載入後判斷登入狀態 */
// document.addEventListener('DOMContentLoaded', function () {
//     checkUser().then(userStatus => {
//         console.log('使用者登入狀態:', userStatus.isLogged);
//         if (userStatus.isLogged) {
//             console.log('會員編號:', userStatus.memberNo);
//         }
//     });
// });

/* ========================== 加入購物車  ========================== */

/* 檢查使用者是否已登入 */
// function addToCart(productNo, quantity) {
//     if (userStatus.isLogged) {
//         // user 已登入，將資料存到 server
//         addToCartServer(userStatus.memberNo, productNo, quantity);
//     } else {
//         // user 未登入，將資料存到 localStorage
//         addToCartLocal(productNo, quantity);
//     }
// }

/* 資料存到 server */
// function addToCartServer(memberNo, productNo, quantity) {
//     productNo = parseInt(productNo, 10);
//     quantity = parseInt(quantity, 10);
//
//     let data = {productNo: productNo, quantity: quantity};
//     let url = `/cart/addToCart`;
//     fetch(url,
//         {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify(data)
//         })
//         .then(response => response.json())
//         .then(data => {
//             if (data.success) {
//                 // 更新cart badge數量
//                 updateCartBadge(data.cartQuantity)
//                 console.log('購物車數量:', data.cartQuantity);
//
//                 Swal.fire({
//                     position: 'top',
//                     icon: 'success',
//                     title: data.message,
//                     showConfirmButton: false,
//                     timer: 1000,
//                     showClass: {
//                         popup: `
//                                 animate__animated
//                                 animate__fadeInDown
//                                 animate__faster
//                                 `
//                     },
//                     hideClass: {
//                         popup: `
//                               animate__animated
//                               animate__fadeOutUp
//                               animate__faster
//                             `
//                     },
//                     customClass: {
//                         popup: '.swal2_success',
//                         title: '.swal2-title-custom'
//                     }
//                 });
//             } else {
//                 Swal.fire({
//                     position: 'top',
//                     icon: 'error',
//                     text: data.message,
//                     showConfirmButton: false,
//                     timer: 1000,
//                     showClass: {
//                         popup: `
//                                 animate__animated
//                                 animate__fadeInDown
//                                 animate__faster
//                                 `
//                     },
//                     hideClass: {
//                         popup: `
//                               animate__animated
//                               animate__fadeOutUp
//                               animate__faster
//                             `
//                     },
//                     customClass: {
//                         popup: '.swal2_error',
//                         title: '.swal2-title-custom'
//                     }
//                 });
//             }
//         })
//         .catch(error => {
//             console.error("加入購物車失敗: ", error);
//             Swal.fire({
//                 position: 'top',
//                 icon: 'error',
//                 text: '無法加入購物車',
//                 showConfirmButton: false,
//                 timer: 1000,
//                 showClass: {
//                     popup: `
//                                 animate__animated
//                                 animate__fadeInDown
//                                 animate__faster
//                                 `
//                 },
//                 hideClass: {
//                     popup: `
//                               animate__animated
//                               animate__fadeOutUp
//                               animate__faster
//                             `
//                 },
//                 customClass: {
//                     popup: '.swal2_error',
//                     title: '.swal2-title-custom'
//                 }
//             });
//         });
// }

/* 資料存到 localStorage */
// function addToCartLocal(productNo, quantity) {
//     console.log('商品編號:', productNo, '數量:', quantity)
//     productNo = parseInt(productNo, 10);
//     quantity = parseInt(quantity, 10);
//
//     let cart = JSON.parse(localStorage.getItem('cart')) || {};
//     if (cart[productNo]) {
//         cart[productNo] += quantity;
//     } else {
//         cart[productNo] = quantity;
//     }
//     localStorage.setItem('cart', JSON.stringify(cart));
//     console.log('商品已存入localStorage:', cart);
//
//     // 顯示 SweetAlert 通知
//     Swal.fire({
//         position: 'top-end',  // 修改為 top-end 以避免遮擋其他元素
//         icon: 'success',
//         // title: message,
//         showConfirmButton: false,
//         timer: 1500,  // 設定自動關閉時間為 1500 毫秒
//         showClass: {
//             popup: 'animate__animated animate__fadeInDown animate__faster'
//         },
//         hideClass: {
//             popup: 'animate__animated animate__fadeOutUp animate__faster'
//         }
//     });
// }

// localStorage.removeItem('cart');

/* 更新購物車徽章數量 */
// function updateCartBadge(newQuantity) {
//     let badge = document.querySelector('.cart-icon .badge');
//     if (badge) {
//         badge.innerText = newQuantity > 99 ? '99+' : newQuantity;
//     }
// }


/* ========================== 購物車頁面  ========================== */
// function getCartItem() {
//         if (userStatus.isLogged) {
//             // 使用者已登入，從 server 取得購物車資料
//             console.log('會員編號為:', userStatus.memberNo);
//             getItemsFromServer(userStatus.memberNo)
//         } else {
//             // 使用者未登入，從 localStorage 取得購物車資料
//             getCartFromLocal();
//         }
// }

/* 從 localStorage 取資料顯示在頁面 */
// function getCartFromLocal() {
//     let cart = JSON.parse(localStorage.getItem('cart')) || {};
//     let allItems = [];
//     for (let productNo in cart) {
//         let url = `/product/getProductDetail?productNo=${productNo}`;
//         fetch(url)
//             .then(response => response.json())
//             .then(data => {
//                 let item = {
//                     product_no: data.productNo,
//                     product_name: data.productName,
//                     product_url: `/product/shopdetail?productNo=${data.productNo}`,
//                     product_img: `/product/getFirstImage/${data.productNo}`,
//                     price: data.productPrice,
//                     quantity: cart[productNo],
//                     subtotal: data.productPrice * cart[productNo]
//                 };
//                 allItems.push(item);
//                 showCartItems(allItems);
//             })
//             .catch(error => console.error('Error:', error));
//     }
// }

// 更新 localStorage 購物車數量
// function updateQtyLocal(productNo, quantity) {
//     productNo = parseInt(productNo, 10);
//     quantity = parseInt(quantity, 10);
//
//     let cart = JSON.parse(localStorage.getItem('cart')) || {};
//     cart[productNo] = quantity;
//     localStorage.setItem('cart', JSON.stringify(cart));
// }

// 刪除 localStorage 購物車商品
// function deleteItemLocal(productNo) {
//     productNo = parseInt(productNo, 10);
//
//     let cart = JSON.parse(localStorage.getItem('cart')) || {};
//     delete cart[productNo];
//     localStorage.setItem('cart', JSON.stringify(cart));
// }

