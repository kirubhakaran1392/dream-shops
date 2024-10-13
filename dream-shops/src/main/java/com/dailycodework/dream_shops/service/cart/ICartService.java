package com.dailycodework.dream_shops.service.cart;

import java.math.BigDecimal;

import com.dailycodework.dream_shops.dto.CartDto;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.model.User;

public interface ICartService {

	Cart getCart(Long id);
	void clearCart(Long id);
	BigDecimal getTotalPrice(Long id);
	Cart initializeNewCart(User user);
	Cart getCartByUserId(Long userId);
	CartDto convertTpCartDto(Cart cart);
}
