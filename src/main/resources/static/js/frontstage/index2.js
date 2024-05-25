let indexProducts = [];
/* 取商品資訊 */
function getIndexProducts() {
	let url = "/product/getAllProducts";
	fetch(url)
		.then(response => response.json())
		.then(data => {
			indexProducts = data
				.slice(0, 8)  // 取前8個商品
				.map(product => ({
					product_no: product.productNo,
					product_name: product.productName,
					product_url: `/product/shopdetail?productNo=${product.productNo}`,
					product_img: `/product/getFirstImage/${product.productNo}`,
					price: product.productPrice
				}));
			showIndexProducts(indexProducts);
		})
		.catch(error => console.error("取得商品失敗:", error));
}

function showIndexProducts(products) {
	let container = document.getElementById('index_products');
	container.innerHTML = '';
	products.forEach(product => {
		// console.log(product.product_no)
		let card = `
            <div class="col-md-3">
                <div class="product-item">
                    <div class="product-thumb">
                        <img class="img-responsive" src=${product.product_img} alt="product-img"/>
                    </div>
                    <div class="product-content">
                        <h4><a href=${product.product_url}>${product.product_name}</a></h4>
                        <span class="price sale_price">$${product.price}</span>
                    </div>
                </div>
            </div>
        `;
		container.innerHTML += card;
	});
}

document.addEventListener('DOMContentLoaded', () => {
	getIndexProducts();
});