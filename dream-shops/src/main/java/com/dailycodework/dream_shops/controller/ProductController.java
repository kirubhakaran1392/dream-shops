package com.dailycodework.dream_shops.controller;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

	private final IProductService productService;
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getAllProducts(){
		try {
			List<Product> products = productService.getAllProducts();
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
		try {
			Product product = productService.getProductById(productId);
			ProductDto productDto = productService.covertToDto(product);
			return ResponseEntity.ok(new ApiResponse("Success!", productDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}catch (ProductNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
		try {
			Product savedProduct = productService.addProduct(product);
			ProductDto productDto = productService.covertToDto(savedProduct);
			return ResponseEntity.ok(new ApiResponse("Success!", productDto));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/{productId}")
	public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId){
		try {
			Product updatedProduct = productService.updateProduct(product, productId);
			ProductDto productDto = productService.covertToDto(updatedProduct);
			return ResponseEntity.ok(new ApiResponse("Success!", productDto));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
		try {
			productService.deleteProduct(productId);
			return ResponseEntity.ok(new ApiResponse("Success!", productId));
		} catch (ProductNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/ByBrandAndName/{brandName}/{productName}")
	public ResponseEntity<ApiResponse> getProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
		try {
			List<Product> products = productService.getProductsByBrandAndName(brandName, productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", ""));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/ByCategoryAndBrand/{categoryName}/{brandName}")
	public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@PathVariable String categoryName, @PathVariable String brandName){
		try {
			List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", ""));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/ByName/{productName}")
	public ResponseEntity<ApiResponse> getProductByName(@PathVariable String productName){
		try {
			List<Product> products = productService.getProductsByName(productName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/ByCategory/{categoryName}")
	public ResponseEntity<ApiResponse> getProductByCategory(@PathVariable String categoryName){
		try {
			List<Product> products = productService.getProductsByCategory(categoryName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/ByBrand")
	public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName){
		System.out.println("ByBrand>>>>>>>>>>>>>");
		try {
			List<Product> products = productService.getProductsByBrand(brandName);
			if(products.isEmpty()) {
				return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product Found", null));
			}
			List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
			return ResponseEntity.ok(new ApiResponse("Success!", convertedProducts));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/CountByBrandAndName/{brandName}/{productName}")
	public ResponseEntity<ApiResponse> countProductByBrandAndName(@PathVariable String brandName, @PathVariable String productName){
		try {
			var productCount = productService.countProductsByBrandAndName(brandName, productName);
			return ResponseEntity.ok(new ApiResponse("Success!", productCount));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
