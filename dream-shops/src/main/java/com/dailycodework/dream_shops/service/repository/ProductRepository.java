package com.dailycodework.dream_shops.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.dream_shops.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
