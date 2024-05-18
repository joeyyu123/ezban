async function getCartQuantityAPI() {
    let url = `/cart/getCartQuantity`;
    try {
        // const fetchResponse = await fetch(url);
        return await fetch(url);
    } catch (e) {
        return e;
    }
}

//
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

function addToCartLocal(productNo, quantity) {
    console.log('商品編號:', productNo, '數量:', quantity)
    productNo = parseInt(productNo, 10);
    quantity = parseInt(quantity, 10);

    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    if (cart[productNo]) {
        cart[productNo] += quantity;
    } else {
        cart[productNo] = quantity;
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    console.log('商品已存入localStorage:', cart);
}

function getCartFromLocal() {
    let cart = JSON.parse(localStorage.getItem('cart')) || {};
    console.log(cart, 'cart9999');
    return cart;
}

// 這個function是用來將購物車數量顯示在UI上的
function setCartQuantityOnUI(cartQuantity) {
    console.log(cartQuantity, 'cartQuantity')
    if (cartQuantity != null) {
        numProducts = Object.keys(cartQuantity).length;
        document.querySelector('.cart-icon .badge').innerText = numProducts > 99 ? '99+' : numProducts;
    }
}

// 這個function是用來將商品加入購物車的，如果使用者有登入，就會將資料存到server，否則就存到localStorage
async function AutoIncreaseCartQuantity(productNo, quantity) {
    response = await IncreaseCartQuantityAPI(productNo, quantity);
    if (response.status === 200 && !response.url.endsWith('loginPage')) {
        Swal.fire({
            icon: 'success',
            title: '成功加入購物車(redis)',
            showConfirmButton: false,
            timer: 1500
        });
        return
    }
    // 預設使用者按下"加入購物車"按鈕時就是某個product數量+1
    addToCartLocal(productNo, quantity);
    Swal.fire({
        icon: 'success',
        title: '成功加入購物車(local storage)',
        showConfirmButton: false,
        timer: 1500
    });
    addToCartLocal(productNo, quantity);
}

async function AutoIncreaseCartQuantityAndSetUI(productNo, quantity) {
    await AutoIncreaseCartQuantity(productNo, quantity);
    AutoGetCartQuantityAndSetUI();
}

async function AutoGetCartQuantity() {
    response = await getCartQuantityAPI();
    // 代表使用者有登入
    if (response.status === 200 && !response.url.endsWith("loginPage")) {
        cart = getCartFromLocal()
        if (cart && Object.keys(cart).length !== 0) {
            localStorage.clear();
            for (let productNo in cart) {
                await IncreaseCartQuantityAPI(productNo, cart[productNo]);
            }
            await AutoGetCartQuantity();
            return
        }
        cart = await response.json();
        console.log(cart, 'cart5555');
        return cart
    }

    // 代表使用者沒有登入，所以資料存放在localStorage
    cart = getCartFromLocal();
    console.log(cart, 'cart1111');
    return Promise.resolve(cart);
}

async function AutoGetCartQuantityAndSetUI() {
    cart = await AutoGetCartQuantity();
    setCartQuantityOnUI(cart);
}

AutoGetCartQuantityAndSetUI();
