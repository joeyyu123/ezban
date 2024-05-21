/* 取商品資訊 */
async function getProductDetailAPI(productNo) {
    let url = `/product/getProductDetail?productNo=${productNo}`;
    try {
        response =  await fetch(url);
        if (response.status !== 200) {
            console.error("取得商品資訊失敗", response);
            return;
        }
        return await response.json();
    } catch (e) {
        return e;
    }
}

/* 取商品圖片 */
async function getProductImages(productNo) {
    let url = `/product/getProductImages?productNo=${productNo}`;
    const location = window.location.hostname;
    try {
        const fetchResponse = await fetch(url);
        const data = await fetchResponse.json();
        // console.log(data, "data")
        return data;
    } catch (e) {
        return e;
    }
}

async function getProductCommentsByProductNoAPI(productNo) {
    let url = `/product/${productNo}/comments`;
    try {
        return await fetch(url);
    } catch (e) {
        return e;
    }
}

async function getUserDetailsByMemberNoAPI(memberNo) {
    let url = `/product/member/${memberNo}`;
    try {
        return await fetch(url);
    } catch (e) {
        return e;
    }
}

async function autoGetCommentsDetailByProductNoAPI(productNo) {
    let commentsDetails = [];
    let totalRate = 0;
    response = await getProductCommentsByProductNoAPI(productNo);
    if (response.status !== 200) {
        console.error("取得商品評論失敗", response);
        return;
    }
    let comments = await response.json();
    let commentCount = comments.length || 0;

    for (let i = 0; i < comments.length; i++) {
        let response = await getUserDetailsByMemberNoAPI(comments[i].memberNo);
        if (response.status !== 200) {
            console.error("取得使用者資訊失敗", response);
            return;
        }
        let userDetails = await response.json();
        userDetails.rate = comments[i].productRate;
        userDetails.comment = comments[i].commentContent;
        totalRate += comments[i].productRate;
        commentsDetails.push(userDetails);
    }
    // 取到小數點後一位
    let averageRate = commentCount > 0 ? parseFloat((totalRate / commentCount).toFixed(1)) : 0;
    return {
        details: commentsDetails,
        count: commentCount,
        averageRate: averageRate
    };
}

// productNo is excluded
async function getProductsByHostAndCategoryAPI(hostNo, productCategoryNo, productNo) {
    let url = `/product/related?hostNo=${hostNo}&productCategoryNo=${productCategoryNo}&productNo=${productNo}`;
    try {
        response =  await fetch(url);
        if (response.status !== 200) {
            console.error("取得相關商品失敗", response);
            return;
        }
        return await response.json();
    } catch (e) {
        return e;
    }
}

// 取得當前商品詳細資訊
// async function getCurrentProductByProductNoAPI(productNo){
//     let url = `/product/getProductDetail?productNo=${productNo}`;
//     try {
//         return await fetch(url);
//     } catch (e) {
//         return e;
//     }
// }
// function displayCurrentProductDetails(productNo) {
//     console.log(productNo, 'productNo');
//     const productDetailUrl = `/product/getProductDetail?productNo=${productNo}`;
//     fetchProductDetails(productDetailUrl)
//         .then(productDetails => {
//             // 獲取 hostNo 和 productCategoryNo
//             const hostNo = productDetails.host.hostNo;
//             const productCategoryNo = productDetails.productCategory.productCategoryNo;
//
//             // 使用獲取的 hostNo 和 productCategoryNo 調用 getRelatedProducts
//             getRelatedProducts(hostNo, productCategoryNo);
//         });
// }
// function getRelatedProducts(hostNo, productCategoryNo) {
//     let url = `/product/related?hostNo=${hostNo}&productCategoryNo=${productCategoryNo}`;
//     fetch(url)
//         .then(response => response.json())
//         .then(products => {
//             updateProductsDisplay(products);
//         })
//         .catch(error => console.error('Error fetching related products:', error));
// }
// function updateProductsDisplay(products) {
//     let container = document.querySelector('#related_products');
//     container.innerHTML = '';  // 清空現有內容
//     products.forEach(product => {
//         let card = `
//             <div class="col-md-3 related_product_card">
//                 <div class="product-item">
//                     <div class="product-thumb">
//                         <img class="img-responsive" src="/product/getFirstImage/${productNo}" alt="${product.productName}"/>
//                     </div>
//                     <div class="product-content">
//                         <h4><a href="/product/shopdetail?productNo=${product.productNo}">${product.productName}</a></h4>
//                         <p class="price">$${product.productPrice}</p>
//                     </div>
//                 </div>
//             </div>`;
//         container.innerHTML += card;
//     });
// }
//
// displayCurrentProductDetails(productNo);
