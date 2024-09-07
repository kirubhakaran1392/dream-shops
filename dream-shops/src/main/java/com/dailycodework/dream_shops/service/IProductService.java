package com.dailycodework.dream_shops.service;

import java.util.List;

import com.dailycodework.dream_shops.model.Product;

public interface IProductService {

	Product addProduct(Product product);
	Product getProductById(Long id);
	void deleteProduct(Long id);
	void updateProduct(Product product, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String category, String name);
	List<Product> countProductsByBrandAndName(String category, String name);
}
