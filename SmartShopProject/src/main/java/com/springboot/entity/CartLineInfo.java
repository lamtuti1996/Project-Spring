package com.springboot.entity;

import com.springboot.entity.ProductInfo;

public class CartLineInfo {
	private ProductInfo product;
	private int quantity;

	public CartLineInfo() {
		this.quantity = 0;
	}

	public ProductInfo getProducs() {
		return product;
	}

	public void setProductInfo(ProductInfo product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return this.product.getPrice() * this.quantity;
	}
}
