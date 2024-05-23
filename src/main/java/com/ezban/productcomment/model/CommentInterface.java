package com.ezban.productcomment.model;

import java.util.List;

public interface CommentInterface {
	
	ProductComment save(ProductComment comment);
    List<ProductCommentDTO> findAllComments(); 

}
