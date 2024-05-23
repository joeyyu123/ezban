async function getCartQuantityAPI() {
    let url = `/cart/getCartQuantity`;
    try {
        // const fetchResponse = await fetch(url);
        return await fetch(url);
    } catch (e) {
        return e;
    }
}

// 以遞增delta的方式增加商品數量
async function IncreaseCartQuantityAPI(productNo, quantity) {
    let url = `/cart/addToCart`;
    let data = {productNo: productNo, quantity: quantity};
    try {
        return await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    } catch (e) {
        return e;
    }
}

// 以直接更新總數量的方式增加商品數量
async function updateCartQuantityAPI(productNo, quantity) {
    let url = `/cart/updateQty`;
    let data = {productNo: productNo, quantity: quantity};
    try {
        return await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
    } catch (e) {
        return e;
    }

}

async function deleteCartAPI(productNo) {
    let url = `/cart/deleteCartItem/${productNo}`;
    try {
        return await fetch(url, {
            method: 'DELETE'
        });
    } catch (e) {
        return e;
    }
}
// 以遞增delta的方式增加商品數量
function addToCartLocal(productNo, quantity) {
    // console.log('商品編號:', productNo, '數量:', quantity)
    productNo = parseInt(productNo, 10);
    quantity = parseInt(quantity, 10);

    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    if (cart[productNo]) {
        cart[productNo] += quantity;
    } else {
        cart[productNo] = quantity;
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    // console.log('商品已存入localStorage:', cart);
}

// 以直接更新總數量的方式增加商品數量
function updateCartLocal(productNo, quantity) {
    // console.log('商品編號:', productNo, '數量:', quantity)
    productNo = parseInt(productNo, 10);
    quantity = parseInt(quantity, 10);

    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    cart[productNo] = quantity;
    localStorage.setItem('cart', JSON.stringify(cart));
    // console.log('商品已存入localStorage:', cart);

}

function getCartFromLocal() {
    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    return cart;
}

function deleteCartFromLocal(productNo) {
    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    delete cart[productNo];
    localStorage.setItem('cart', JSON.stringify(cart));
    return cart;
}

// 購物車數量顯示在UI
function setCartQuantityOnUI(cartQuantity) {
    // console.log(cartQuantity, 'cartQuantity')
    if (cartQuantity != null) {
        numProducts = Object.keys(cartQuantity).length;
        document.querySelector('.cart-icon .badge').innerText = numProducts > 99 ? '99+' : numProducts;
    }
}

// 加入購物車，若使用者有登入，資料存到server，否則就存到localStorage
async function autoIncreaseCartQuantity(productNo, quantity) {
    response = await IncreaseCartQuantityAPI(productNo, quantity);
    if (response.status === 200 && !response.url.endsWith('loginPage')) {
        Swal.fire({
            icon: 'success',
            title: '成功加入購物車',
            showConfirmButton: false,
            timer: 1500
        });
        return
    }
    // 預設使用者按下"加入購物車"按鈕時product數量+1
    Swal.fire({
        icon: 'success',
        title: '成功加入購物車',
        showConfirmButton: false,
        timer: 1500
    });
    addToCartLocal(productNo, quantity);
}

async function autoIncreaseCartQuantityAndSetUI(productNo, quantity) {
    await autoIncreaseCartQuantity(productNo, quantity);
    await autoGetCartQuantityAndSetUI();
}

// 更新購物車數量，若使用者有登入，資料存到server，否則就存到localStorage
async function autoUpdateCartQuantity(productNo, quantity) {
    response = await updateCartQuantityAPI(productNo, quantity);
    if (response.status === 200 && !response.url.endsWith('loginPage')) {
        return
    }
    updateCartLocal(productNo, quantity);
}

async function autoUpdateCartQuantityAndSetUI(productNo, quantity) {
    await autoUpdateCartQuantity(productNo, quantity);
    await autoGetCartQuantityAndSetUI();

}

async function autoDeleteCart(productNo) {
    response = await deleteCartAPI(productNo);
    if (response.status === 200 && !response.url.endsWith('loginPage')) {
        return
    }
    deleteCartFromLocal(productNo);
}

async function autoDeleteCartAndSetUI(productNo) {
    await autoDeleteCart(productNo);
    await autoGetCartQuantityAndSetUI();

}


async function autoGetCartQuantity() {
    response = await getCartQuantityAPI();
    // 代表使用者有登入
    if (response.status === 200 && !response.url.endsWith("loginPage")) {
        cart = getCartFromLocal()
        if (cart && Object.keys(cart).length !== 0) {
            localStorage.clear();
            for (let productNo in cart) {
                await IncreaseCartQuantityAPI(productNo, cart[productNo]);
            }
            return await autoGetCartQuantity();
        }
        cart = await response.json();
        // console.log(cart, 'cart5555');
        return cart
    }

    // 代表使用者沒有登入，資料存放在localStorage
    cart = getCartFromLocal();
    // console.log(cart, 'cart1111');
    return Promise.resolve(cart);
}

async function autoGetCartQuantityAndSetUI() {
    cart = await autoGetCartQuantity();
    setCartQuantityOnUI(cart);
}

autoGetCartQuantityAndSetUI();
