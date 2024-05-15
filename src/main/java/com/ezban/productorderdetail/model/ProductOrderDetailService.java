package com.ezban.productorderdetail.model;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ezban.productimg.model.ProductImgRepository;
import javax.validation.Valid;

@Service
public class ProductOrderDetailService {

	@Autowired
	ProductOrderDetailRepository repository;
	@Autowired
	ProductImgRepository productImgRepository;

	// 依據訂單編號顯示所有的明細
	public List<ProductOrderDetail> findByProductOrder(Integer productOrderNo) {
		//  查 Detail
		List<ProductOrderDetail> details = repository.findByProductOrder(productOrderNo);
		//  回傳
		return details;
	}


	// 新增訂單明細(待確認能否正常新增)
	public void addProductOrderDetail(@Valid ProductOrderDetail productOrderDetail) {
		productOrderDetail.setProductOrderDetailNo(productOrderDetail.getProductOrderDetailNo());
		productOrderDetail.setProduct(productOrderDetail.getProduct());
		productOrderDetail.setProductOrder(productOrderDetail.getProductOrder());
		productOrderDetail.setProductQty(productOrderDetail.getProductQty());
		productOrderDetail.setProductPrice(productOrderDetail.getProductPrice());
		productOrderDetail.setCommentsStatus((byte) 0);
		repository.save(productOrderDetail);
	}

}