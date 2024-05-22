package com.ezban.productorderdetail.model;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ezban.productimg.model.ProductImgRepository;

@Service
public class ProductOrderDetailService {

	@Autowired
	ProductOrderDetailRepository repository;

	@Autowired
	ProductImgRepository productImgRepository;

	// 依據訂單編號顯示所有的明細
	public List<ProductOrderDetail> findByProductOrder(Integer productOrderNo) {

		List<ProductOrderDetail> details = repository.findByProductOrder(productOrderNo);
		return details;

	}

}