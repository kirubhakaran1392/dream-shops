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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/category")
public class CategoryController {

	private final ICategoryService categoryService;
	
	@GetMapping("/")
	public ResponseEntity<ApiResponse> getAllCategories(){
		try {
			List<Category> categories = categoryService.getAllCategory();
			return ResponseEntity.ok(new ApiResponse("Found!", categories));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", INTERNAL_SERVER_ERROR));
		}
	}
	
	@PostMapping("/")
	public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
		try {
			Category category = categoryService.addCategory(name);
			return ResponseEntity.ok(new ApiResponse("Saved", category));
		} catch (AlreadyExistsException e) {
			return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null));
		}
	}
	
	@GetMapping("/id/{categoryId}")
	public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
		try {
			Category category = categoryService.getCategoryById(categoryId);
			return ResponseEntity.ok(new ApiResponse("Found!", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@GetMapping("/name/{categoryName}")
	public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String categoryName){
		try {
			Category category = categoryService.getCategoryByName(categoryName);
			return ResponseEntity.ok(new ApiResponse("Found!", category));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable Long id){
		try {
			categoryService.deleteCategoryById(id);
			return ResponseEntity.ok(new ApiResponse("Deleted!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id, @RequestBody Category category){
		try {
			Category updateCategory = categoryService.updateCategory(category, id);
			return ResponseEntity.ok(new ApiResponse("Update Success!", updateCategory));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
}
