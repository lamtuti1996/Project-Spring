package com.springboot.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity(name="CategoriesProduct")
public class CategoriesProduct {
     
	@Id
	@Column(name="CategoryID")
	private int categoryID;
	
	@Column(name="CategoryName")
	private String categoryName;
	
	@OneToMany(mappedBy = "categoryProduct", fetch = FetchType.EAGER)
	private List<Products> productList;

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Products> getProductList() {
		return productList;
	}

	public void setProductList(List<Products> productList) {
		this.productList = productList;
	}
	
	
	
}
