package com.dailycodework.dream_shops.service;

import java.util.List;

import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.service.repository.ProductRepository;

public class ProductService implements IProductService{

	private ProductRepository productRepository;
	@Override
	public Product addProduct(Product product) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.findById(id)
			.ifPresentOrElse(productRepository::delete, 
					()->{throw new ProductNotFoundException("Product Not Found!");});
		
	}

	@Override
	public void updateProduct(Product product, Long productId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByBrand(String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getProductsByBrandAndName(String category, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> countProductsByBrandAndName(String category, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
