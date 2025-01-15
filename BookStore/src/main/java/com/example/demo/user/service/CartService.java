package com.example.demo.user.service;

import java.util.List;

import com.example.demo.user.entity.CartInfo;

//1月15日 遠所作業

public interface CartService {
    List<CartInfo> getCartItems(Integer userId);
    double calculateTotal(Integer userId);
    void updateItemQuantity(Integer userId, Integer itemId, Integer quantity);
    void removeItem(Integer userId, Integer itemId);
}


