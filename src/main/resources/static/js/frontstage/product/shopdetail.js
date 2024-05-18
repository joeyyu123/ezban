/* 從url取商品編號 */
function test(productNo)
{
    document.addEventListener('DOMContentLoaded', function () {

            let input_el = document.getElementById('product-quantity');
            input_el.setAttribute('data-product-no', productNo);

            // getProductDetail(productNo);
            // getProductImages(productNo);

    });
}
/* 取商品資訊 */
function getProductDetail(productNo) {
    let url = `/product/getProductDetail?productNo=${productNo}`;
    fetch(url)
        .then(response => response.json())
        .then(product => {
            console.log(product, "product")
            // 頁面有兩個 class="product_name" 元素 -> querySelectorAll
            let productNames = document.querySelectorAll('.product_name');
            productNames.forEach((productName) => {
                productName.innerText = product.productName;
            })
            document.querySelector('.product_price').innerText = `$${product.productPrice}`;
            document.querySelector('.product_category').innerText = product.productCategory.productCategoryName;
            document.querySelector('.product_desc').innerText = product.productDesc;
        })
        .catch(error => console.error("取得商品資訊失敗", error));
}

/* 取商品圖片 */
async function getProductImages(productNo) {
    let url = `/product/getProductImages?productNo=${productNo}`;
    const location = window.location.hostname;
    try {
        const fetchResponse = await fetch(url);
        const data = await fetchResponse.json();
        console.log(data, "data")
        return data;
    } catch (e) {
        return e;
    }
    // fetch(url)
    //     .then(response => response.json())
    //     .then(imageUrls => {
    //         let carouselIndicators = document.querySelector('.carousel-indicators');
    //         let carouselInner = document.querySelector('.carousel-inner');
    //
    //         imageUrls.forEach((url, index) => {
    //             // carousel-indicator
    //             let indicator = document.createElement('li');
    //             indicator.setAttribute('data-target', '#carousel-custom');
    //             indicator.setAttribute('data-slide-to', index);
    //             if (index === 0) indicator.classList.add('active');
    //             let thumb_img = document.createElement('img');
    //             thumb_img.src = url;
    //             indicator.appendChild(thumb_img);
    //             carouselIndicators.appendChild(indicator);
    //
    //             // carousel-inner
    //             let item = document.createElement('div');
    //             item.className = 'item' + (index === 0 ? ' active' : '');
    //             let img = document.createElement('img');
    //             img.src = url;
    //             img.setAttribute('data-zoom-image', url);
    //             item.appendChild(img);
    //             carouselInner.appendChild(item);
    //         });
    //     })
    //     .catch(error => console.error("讀取圖片失敗", error));
}

/* 當input值改變時更新data-quantity屬性 */
// let input = document.getElementById('product-quantity');
// input.addEventListener('change', function() {
//     input.setAttribute('data-quantity', input.value);
// });

/* 加入購物車 */
document.getElementById('add_to_cart').addEventListener('click', function (e) {
    e.preventDefault();
    let quantity = document.getElementById('product-quantity').value;
    addToCart(productNo, quantity);
});