package com.ezban.productcomment.model;

import java.util.List;

public interface ProductCommentInterface {
	ProductComment save(ProductComment comment);

	List<ProductComment> findAll();
}