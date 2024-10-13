package com.dailycodework.dream_shops.service.product;

import java.util.List;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;

public interface IProductService {

	Product addProduct(AddProductRequest product);
	Product getProductById(Long id);
	void deleteProduct(Long id);
	Product updateProduct(ProductUpdateRequest product, Long productId);
	
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByBrand(String brand);
	List<Product> getProductsByCategoryAndBrand(String category, String brand);
	List<Product> getProductsByName(String name);
	List<Product> getProductsByBrandAndName(String category, String name);
	Long countProductsByBrandAndName(String category, String name);
	ProductDto covertToDto(Product product);
	List<ProductDto> getConvertedProducts(List<Product> products);
}
