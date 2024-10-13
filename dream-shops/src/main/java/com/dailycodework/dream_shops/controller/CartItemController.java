package com.dailycodework.dream_shops.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.ICartItemService;
import com.dailycodework.dream_shops.service.cart.ICartService;
import com.dailycodework.dream_shops.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {

	private final ICartItemService cartItemService;
	private final ICartService cartService;
	private final IUserService userService;
	
	@PostMapping("/addItemToCart")
	public ResponseEntity<ApiResponse> addItemToCart(
													 @RequestParam Long productId,
													 @RequestParam Integer quantity){
		
		try {
			User user = userService.getUserById(1L);
			Cart cart = cartService.initializeNewCart(user);
			cartItemService.addItemToCart(cart.getId(), productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Add Item to Cart Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
	
	@DeleteMapping("/removeItemFromCart/{cartId}/{itemId}")
	public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
		try {
			cartItemService.removeItemFromCart(cartId, itemId);
			return ResponseEntity.ok(new ApiResponse("Remove Item from Cart Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
	}
	
	@PutMapping("/updateQuantity/{cartId}/{productId}")
	public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
													 @PathVariable Long productId,
													 @RequestParam Integer quantity){
		
		try {
			cartItemService.updateItemQuantity(cartId, productId, quantity);
			return ResponseEntity.ok(new ApiResponse("Update Quantity Success!", null));
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}
		
	}
}
