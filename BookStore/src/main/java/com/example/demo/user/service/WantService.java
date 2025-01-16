package com.example.demo.user.service;

import java.util.List;

import com.example.demo.user.entity.CartInfo;

//1月15日 遠所作業

public class WantService {
    List<CartInfo> getWantItems(Integer userId);
    CartInfo getWantItem(Integer userId, Integer itemId);
    void removeFromWantList(Integer userId, Integer itemId);
    // 他の必要なメソッド
}

