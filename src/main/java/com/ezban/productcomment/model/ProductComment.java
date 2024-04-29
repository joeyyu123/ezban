package com.ezban.productcomment.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.ezban.member.model.Member;
import com.ezban.product.model.Product;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class ProductComment {

	@Entity
	@Table(name = "product_comment")
	public class Product_comment {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "product_comment_no", nullable = false)
		private Integer productCommentNo;

		@NotNull
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "product_no", nullable = false)
		private Product product;

		@NotNull
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "member_no", nullable = false)
		private Member member;

		@Column(name = "product_rate")
		private Integer productRate;

		@Column(name = "product_comment_content")
		private String productCommentContent;

		@NotNull
		@Column(name = "product_comment_date", nullable = false)
		private Timestamp productCommentDate;

		@Column(name = "product_comment_status")
		private Byte productCommentStatus;

		public Product_comment() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Product_comment(Integer productCommentNo, @NotNull Product product, @NotNull Member member,
				Integer productRate, String productCommentContent, @NotNull Timestamp productCommentDate,
				Byte productCommentStatus) {
			super();
			this.productCommentNo = productCommentNo;
			this.product = product;
			this.member = member;
			this.productRate = productRate;
			this.productCommentContent = productCommentContent;
			this.productCommentDate = productCommentDate;
			this.productCommentStatus = productCommentStatus;
		}

		public Integer getProductCommentNo() {
			return productCommentNo;
		}

		public void setProductCommentNo(Integer productCommentNo) {
			this.productCommentNo = productCommentNo;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public Member getMember() {
			return member;
		}

		public void setMember(Member member) {
			this.member = member;
		}

		public Integer getProductRate() {
			return productRate;
		}

		public void setProductRate(Integer productRate) {
			this.productRate = productRate;
		}

		public String getProductCommentContent() {
			return productCommentContent;
		}

		public void setProductCommentContent(String productCommentContent) {
			this.productCommentContent = productCommentContent;
		}

		public Timestamp getProductCommentDate() {
			return productCommentDate;
		}

		public void setProductCommentDate(Timestamp productCommentDate) {
			this.productCommentDate = productCommentDate;
		}

		public Byte getProductCommentStatus() {
			return productCommentStatus;
		}

		public void setProductCommentStatus(Byte productCommentStatus) {
			this.productCommentStatus = productCommentStatus;
		}

		@Override
		public String toString() {
			return "Product_comment [productCommentNo=" + productCommentNo + ", product=" + product + ", member="
					+ member + ", productRate=" + productRate + ", productCommentContent=" + productCommentContent
					+ ", productCommentDate=" + productCommentDate + ", productCommentStatus=" + productCommentStatus
					+ "]";
		}
		
		
	}

}
