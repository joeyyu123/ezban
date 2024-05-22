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
        userDetails.memberMail = anonymizeEmail(userDetails.memberMail);
        userDetails.rate = comments[i].productRate;
        userDetails.comment = comments[i].commentContent;
        totalRate += comments[i].productRate;
        commentsDetails.push(userDetails);

        console.log(userDetails, "userDetails111", commentsDetails, "commentsDetails222")
    }
    // 取到小數點後一位
    let averageRate = commentCount > 0 ? parseFloat((totalRate / commentCount).toFixed(1)) : 0;
    return {
        details: commentsDetails,
        count: commentCount,
        averageRate: averageRate
    };
}

// 相關商品，productNo代表目前商品(excluded)
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
