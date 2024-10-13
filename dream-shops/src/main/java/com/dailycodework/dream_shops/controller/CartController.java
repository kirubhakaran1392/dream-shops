package com.dailycodework.dream_shops.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dream_shops.dto.CartDto;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.CartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {

	private final CartService cartService;
	
	@GetMapping("/{cardId}")
	public ResponseEntity<ApiResponse> getCart(@PathVariable Long cardId){
		
		try {
			Cart cart = cartService.getCart(cardId);
			CartDto cartDto = cartService.convertTpCartDto(cart);
			return ResponseEntity.ok(new ApiResponse("Success", cartDto));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@DeleteMapping("/{cardId}")
	public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cardId){
		try {
			cartService.clearCart(cardId);
			return ResponseEntity.ok(new ApiResponse("Clear Cart Success", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
	@GetMapping("total-price/{cardId}")
	public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cardId){
		try {
			BigDecimal totalPrice = cartService.getTotalPrice(cardId);
			return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
		}
	}
	
}
