package com.example.demo.user.service;

import java.util.List;

import com.example.demo.user.entity.ItemId;

//１月１５日　遠所作業　仮のService

public interface BookService {
    ItemId getItemById(Integer id);
    List<ItemId> searchItems(String query);
    // 他の必要なメソッド
}


